/**
 *  ResourceProviderV2.java
 *  Adventure Game Interpreter Resource Package
 *
 *  Created by Dr. Z
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.res.v2;

import  java.io.*;
import  java.util.*;

import  com.sierra.agi.io.*;
import  com.sierra.agi.res.*;
import  com.sierra.agi.res.dir.ResourceDirectory;

/**
 * Provide access to resources via the standard storage methods.
 * It reads unmodified sierra's resource files.
 *
 * All AGI games have either one directory file, or more commonly, four.
 * AGI version 2 games will have the files LOGDIR, PICDIR, VIEWDIR, and SNDDIR.
 * This single file is basically the four version 2 files joined together
 * except that it has an 8 byte header giving the position of each directory
 * within the single file.
 *
 * The directory files give the location of the data types within the VOL
 * files. The type of directory determines the type of data. For example, the
 * LOGDIR gives the locations of the LOGIC files.
 *
 * <I>Note</I>: In this description and elsewhere in documents written by me,
 * the AGI data called LOGIC, PICTURE, VIEW, and SOUND data are referred to by
 * me as files even though they are part of a single VOL file. I think of
 * the VOL file as sort of a virtual storage device in itself that holds many
 * files. Some documents call the files contains in VOL files "resources".
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class ResourceProviderV2 extends Object implements ResourceProvider
{
    /** Resource's CRC. */
    protected long crc;
    
    /** Resource's Entries Tables. */
    protected ResourceDirectory entries[] = new ResourceDirectory[4];
    
    /** Path to resources files. */
    protected File path;
    
    /** Resource Configuration */
    protected ResourceConfiguration configuration = new ResourceConfiguration();

    /**
     * AGDS's Decryption Key. This key is used to decrypt
     * AGDS games.
     */
    public static final String AGDS_KEY = "Alex Simkin";
    
    /**
     * Sierra's Decryption Key. This key used to decrypt
     * original sierra games.
     */
    public static final String SIERRA_KEY = "Avis Durgan";
    
    /**
     * Initialize the ResourceProvider implentation to access
     * resource on the file system.
     *
     * @param folder Resource's folder or File inside the resource's
     *               folder.
     */
    public ResourceProviderV2(File folder) throws IOException, ResourceException
    {
        if (!folder.exists())
        {
            throw new FileNotFoundException();
        }

        if (folder.isDirectory())
        {
            path = folder.getAbsoluteFile();
        }
        else
        {
            path = folder.getParentFile();
        }
        
        readVolumes();
        readDirectories();
        calculateCRC();
        calculateConfiguration();
    }

    protected void validateType(byte resType) throws ResourceTypeInvalidException
    {
        if ((resType > TYPE_WORD) || (resType < TYPE_LOGIC))
        {
            throw new ResourceTypeInvalidException();
        }
    }

    /**
     * Retreive the count of resources of the specified type.
     * Only valid with Locic, Picture, Sound and View resource
     * types.
     *
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_LOGIC
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_PICTURE
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_SOUND
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_VIEW
     * @param  resType Resource type
     * @return Resource count.
     */
    public int count(byte resType) throws ResourceException
    {
        validateType(resType);
        
        if (resType >= TYPE_OBJECT)
        {
            return 1;
        }
        
        return entries[resType].getCount();
    }
    
    /**
     * Enumerate the resource numbers of the specified type.
     * Only valid with Locic, Picture, Sound and View resource
     * types.
     *
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_LOGIC
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_PICTURE
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_SOUND
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_VIEW
     * @param  resType Resource type
     * @return Array containing the resource numbers.
     */
    public short[] enumerate(byte resType) throws ResourceException
    {
        validateType(resType);
        
        return entries[resType].getNumbers();
    }
    
    /**
     * Open the specified resource and return a pointer
     * to the resource. The InputStream is decrypted/decompressed,
     * if neccessary, by this function. (So you don't have to care
     * about them.)
     *
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_LOGIC
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_OBJECT
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_PICTURE
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_SOUND
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_VIEW
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_WORD
     * @param  resType   Resource type
     * @param  resNumber Resource number. Ignored if resource type
     *                   is <CODE>TYPE_OBJECT</CODE> or
     *                   <CODE>TYPE_WORD</CODE>
     * @return InputStream linked to the specified resource.
     */
    public InputStream open(byte resType, short resNumber) throws ResourceException, IOException
    {
        File volf;
        
        switch (resType)
        {
        case ResourceProvider.TYPE_OBJECT:
            volf = getDirectoryFile(resType);
            
            if (isCrypted(volf))
            {
                return new CryptedInputStream(new FileInputStream(volf), getKey(false));
            }
            else
            {
                return new FileInputStream(volf);
            }
            
        case ResourceProvider.TYPE_WORD:
            return new FileInputStream(getDirectoryFile(resType));
        }

        try
        {
            if (entries[resType] != null)
            {
                int vol, offset, length;
                
                vol    = entries[resType].getVolume(resNumber);
                offset = entries[resType].getOffset(resNumber);
            
                if ((vol != -1) && (offset != -1))
                {
                    byte[]           b;
                    RandomAccessFile file;
                    InputStream      in;
                    
                    b    = new byte[5];
                    file = new RandomAccessFile(getVolumeFile(vol), "r");
                    file.seek(offset);
                    file.read(b, 0, 5);
                    
                    if ((b[0] != 0x12) || (b[1] != 0x34))
                    {
                        throw new CorruptedResourceException();
                    }
                    
                    length = ByteCaster.lohiUnsignedShort(b, 3);
                    in     = new SegmentedInputStream(file, offset + 5, length);
                    
                    if (resType == TYPE_LOGIC)
                    {
                        int startPos, numMessages, offsetCrypted;
                    
                        // Calculate the Messages Offset
                        file.read(b, 0, 2);
                        startPos = ByteCaster.lohiUnsignedShort(b, 0) + 2;
                        file.seek(offset + startPos + 5);
                        file.read(b, 0, 3);
                        numMessages   = ByteCaster.lohiUnsignedByte(b, 0);
                        offsetCrypted = startPos + 3 + (numMessages * 2);
                        file.seek(offset + 5);
                        
                        in = new CryptedInputStream(in, getKey(false), offsetCrypted);
                    }
                    
                    return in;
                }
            }
            
            throw new ResourceNotExistingException();
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new ResourceTypeInvalidException();
        }
    }

    /**
     * Calculate the CRC of the resources. In this implentation
     * the CRC is not calculated by this function, it only return
     * the cached CRC value.
     *
     * @return CRC of the resources.
     */
    public long getCRC()
    {
        return crc;
    }
    
    public byte getProviderType()
    {
        return PROVIDER_TYPE_FAST;
    }

    public static String getKey(boolean agds)
    {
        return System.getProperty("com.sierra.agi.res.key", agds? AGDS_KEY: SIERRA_KEY);
    }
    
    public ResourceConfiguration getConfiguration()
    {
        return configuration;
    }
    
    protected File getVolumeFile(int vol) throws IOException
    {
        File file = getGameFile(path, "vol." + Integer.toString(vol));
        
        if (!file.exists())
        {
            throw new FileNotFoundException("File " + file.getPath() + " can't be found.");
        }

        return file;
    }
    
    /**
     * To account for different platforms, where there may or may not be a case
     * sensitive file system, and where the game files may or may not have been
     * copied from another platform, we attempt here to look for the requested
     * game file firstly as-is, then in uppercase form, and then in lowercase.
     * 
     * @param path The File that represents the folder that contains the game files.
     * @param fileName The name of the file to get.
     * 
     * @return A File representing the game file.
     */
    protected File getGameFile(File path, String fileName) 
    {
        File file = new File(path, fileName);
        if (!file.exists()) 
        {
            file = new File(path, fileName.toUpperCase());
            if (!file.exists()) 
            {
                file = new File(path, fileName.toLowerCase());
            }
        }
        return file;
    }
    
    protected File getDirectoryFile(byte resType) throws IOException
    {
        File file;
    
        switch (resType)
        {
        case ResourceProvider.TYPE_OBJECT:
            file = getGameFile(path, "object");
            break;
        case ResourceProvider.TYPE_WORD:
            file = getGameFile(path, "words.tok");
            break;
        case ResourceProvider.TYPE_LOGIC:
            file = getGameFile(path, "logdir");
            break;
        case ResourceProvider.TYPE_PICTURE:
            file = getGameFile(path, "picdir");
            break;
        case ResourceProvider.TYPE_SOUND:
            file = getGameFile(path, "snddir");
            break;
        case ResourceProvider.TYPE_VIEW:
            file = getGameFile(path, "viewdir");
            break;
        default:
            return null;
        }

        if (!file.exists())
        {
            throw new FileNotFoundException();
        }

        return file;
    }

    /**
     * Retreive the size in bytes of the specified resource.
     *
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_LOGIC
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_OBJECT
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_PICTURE
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_SOUND
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_VIEW
     * @see    com.sierra.agi.res.ResourceProvider#TYPE_WORD
     * @param  resType   Resource type
     * @param  resNumber Resource number. Ignored if resource type
     *                   is <CODE>TYPE_OBJECT</CODE> or
     *                   <CODE>TYPE_WORD</CODE>
     * @return Size in bytes of the specified resource.
     */
    public int getSize(byte resType, short resNumber) throws ResourceException, IOException
    {
        switch (resType)
        {
        case ResourceProvider.TYPE_OBJECT:
        case ResourceProvider.TYPE_WORD:
            return (int)getDirectoryFile(resType).length();
        }

        try
        {
            if (entries[resType] != null)
            {
                int vol, offset;
                
                vol    = entries[resType].getVolume(resNumber);
                offset = entries[resType].getOffset(resNumber);
            
                if ((vol != -1) && (offset != -1))
                {
                    byte[]           b;
                    RandomAccessFile file;
                    
                    b    = new byte[5];
                    file = new RandomAccessFile(getVolumeFile(vol), "r");
                    file.seek(offset);
                    file.read(b);
                    file.close();
                    
                    if ((b[0] != 0x12) || (b[1] != 0x34))
                    {
                        throw new CorruptedResourceException();
                    }
                    
                    return ByteCaster.lohiUnsignedShort(b, 3);
                }
            }
            
            throw new ResourceNotExistingException();
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new ResourceTypeInvalidException();
        }
    }
    
    /** Find volumes files */
    protected void readVolumes() throws NoVolumeAvailableException
    {
        boolean founded = false;
        int     i       = 0;
        File    volf;
        
        while (true)
        {
            volf = getGameFile(path, "vol." + Integer.toString(i));
            
            if (volf.exists())
            {
                founded = true;
                break;
            }
            
            if (i > 50)
            {
                break;
            }
            
            i++;
        }

        if (!founded)
        {
            throw new NoVolumeAvailableException();
        }
    }
    
    /** Read all directory files */
    protected void readDirectories() throws NoDirectoryAvailableException, IOException
    {
        byte        i;
        int         j;
        File        dir;
        InputStream stream;
        
        for (i = 0, j = 0; i < 4; i++)
        {
            dir = getDirectoryFile(i);
        
            if (dir != null)
            {
                stream     = new FileInputStream(dir);
                entries[i] = new ResourceDirectory(stream);
                stream.close();
                j++;
            }
        }
        
        if (j == 0)
        {
            throw new NoDirectoryAvailableException();
        }
    }
    
    /** Calculate the Resource's CRC */
    protected void calculateCRC() throws IOException
    {
        File dirf = new File(path, "vol.crc");

        try
        {
            /* Check if the CRC has been pre-calculated */
            DataInputStream meta = new DataInputStream(new FileInputStream(dirf));
            
            crc = meta.readLong();
            meta.close();
        }
        catch (IOException ex)
        {
            /* CRC need to be calculated from scratch */
            crc = calculateCRCFromScratch();
        
            /* Write down the CRC for next times */
            DataOutputStream meta = new DataOutputStream(new FileOutputStream(dirf));
                
            meta.writeLong(crc);
            meta.close();
        }
    }

    protected int calculateCRCFromScratch() throws IOException
    {
        File dir[];
        int  i, j, c;
        
        c       = 0;
        dir     = new File[2];
        dir[0]  = getGameFile(path, "object");
        dir[1]  = getGameFile(path, "words.tok");

        for (i = 0; i < entries.length; i++)
        {
            if (entries[i] != null)
            {
                c += entries[i].getCRC();
            }
        }
            
        for (i = 0; i < dir.length; i++)
        {
            FileInputStream stream = new FileInputStream(dir[i]);

            while (true)
            {
                j = stream.read();

                if (j == -1)
                    break;

                c += j;
            }
                    
            stream.close();
        }
        
        return c;
    }

    protected void calculateConfiguration()
    {
        Properties props = new Properties();
        String     scrc  = "0x" + Long.toString(crc, 16);
        String     ver;
        boolean    amiga, agds;

        try
        {
            props.load(getClass().getResourceAsStream("version.conf"));
        }
        catch (IOException ioex)
        {
        }
        
        ver   = props.getProperty(scrc, "0x2917");
        configuration.amiga = ver.indexOf('a') != -1;
        configuration.agds  = ver.indexOf('g') != -1;
        ver   = ver.substring(2);

        while (!Character.isDigit(ver.charAt(ver.length() - 1)))
        {
            ver = ver.substring(0, ver.length() - 1);
        }

        configuration.engineEmulation = (Integer.valueOf(ver, 16).shortValue());
        
        props = new Properties();

        try
        {
            props.load(getClass().getResourceAsStream("name.conf"));
        }
        catch (IOException ioex)
        {
        }
        
        configuration.name = props.getProperty(scrc, "Unknown Game");
    }
    
    public static boolean isCrypted(File file)
    {
        boolean b = false;
        
        try
        {
            ByteCasterStream bstream = new ByteCasterStream(new FileInputStream(file));
            
            if (bstream.lohiReadUnsignedShort() > file.length())
            {
                b = true;
            }
            
            bstream.close();
            return b;
        }
        catch (Throwable t)
        {
            return false;
        }
    }
}