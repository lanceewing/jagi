/*
 * InventoryObjects.java
 */

package com.sierra.agi.inv;

import java.io.*;
import java.util.*;
import com.sierra.agi.res.ResourceConfiguration;
import com.sierra.agi.io.ByteCasterStream;

/**
 * Stores Objects of the game.
 * <P>
 * <B>Object File Format</B><BR>
 * The object file stores two bits of information about the inventory items used
 * in an AGI game. The starting room location and the name of the inventory item.
 * It also has a byte that determines the maximum number of animated objects.
 * </P><P>
 * <B>File Encryption</B><BR>
 * The first obstacle to overcome is the fact that most object files are
 * encrypted. I say most because some of the earlier AGI games were not, in
 * which case you can skip to the next section. Those that are encrypted are done
 * so with the string "Avis Durgan" (or, in case of AGDS games, "Alex Simkin").
 * The process of unencrypting the file is to simply taken every eleven bytes
 * from the file and XOR each element of those eleven bytes with the corresponding
 * element in the string "Avis Durgan". This sort of encryption is very easy to
 * crack if you know what you are doing and is simply meant to act as a shield
 * so as not to encourage cheating. In some games, however, the object names are
 * clearly visible in the saved game files even when the object file is encrypted,
 * so it's not a very effective shield.
 * <P>
 * <B>File Format</B><BR>
 * <TABLE BORDER=1>
 * <THEAD><TR><TD>Byte</TD><TD>Meaning</TD></TR></THEAD>
 * <TBODY>
 * <TR><TD>0-1</TD><TD>Offset of the start of inventory item names</TD></TR>
 * <TR><TD>2</TD><TD>Maximum number of animated objects</TD></TR>
 * </TBODY></TABLE>
 * <P>
 * Following the first three bytes as a section containing a three byte entry
 * for each inventory item all of which conform to the following format: 
 * <P>
 * <TABLE BORDER=1>
 * <THEAD><TR><TD>Byte</TD><TD>Meaning</TD></TR></THEAD>
 * <TBODY>
 * <TR><TD>0-1</TD><TD>Offset of inventory item name i</TD></TR>
 * <TR><TD>2</TD><TD>Starting room number for inventory item i or 255 carried</TD></TR>
 * </TBODY></TABLE>
 * <P>
 * Where i is the entry number starting at 0. All offsets are taken from the
 * start of entry for inventory item 0 (not the start of the file).
 * <P>
 * Then comes the textual names themselves. This is simply a list of NULL
 * terminated strings. The offsets mentioned in the above section point to the
 * first character in the string and the last character is the one before the
 * 0x00.
 *
 * @author  Dr. Z, Lance Ewing (Documentation)
 * @version 0.00.00.01
 */
public class InventoryObjects extends Object implements InventoryProvider
{
    /** Object list. */    
    protected InventoryObject[] objects = null;
    protected boolean amiga;
    protected int engineEmulation;
    
    public InventoryObjects(ResourceConfiguration config)
    {
        amiga = config.amiga;
        engineEmulation = config.engineEmulation;
    }
    
    /**
     * Loads a AGI Object File from a stream.
     *
     * @param  stream   Stream where the Objects are contained. Must be a AGI
     *                  compliant format.
     * @throws IOException Caller must handle IOException from his stream.
     * @return Returns the number of object contained in the stream.
     */    
    public InventoryObjects loadInventory(InputStream stream) throws IOException
    {
        ByteCasterStream bstream = new ByteCasterStream(stream);
        byte             padSize;
        int              offset, i, nobject;
        int[]            offsets;
        InventoryObject  obj;
        Hashtable        hash;

        // Pre-AGIv2 games don't appear to have the first three bytes. So we read ahead
        // to get the deduce the number of objects, and then reset.
        if (engineEmulation < 0x2000) {
            bstream.mark(2);
        }
        
        /* Calculate Inventory Object Count */
        padSize  = amiga? (byte)4: (byte)3;
        nobject  = bstream.lohiReadUnsignedShort();
        nobject /= padSize;
        
        objects  = new InventoryObject[nobject];
        offsets  = new int[nobject];
        
        // Pre-AGIv2 games don't appear to have the first three bytes.
        if (engineEmulation < 0x2000) { 
            bstream.reset();
        } else {
            bstream.skip(padSize - 2);
        }
        
        offset = 0;
        
        for (i = 0; i < nobject; i++)
        {
            offsets[i] = bstream.lohiReadUnsignedShort();
            objects[i] = new InventoryObject(bstream.readUnsignedByte());
            
            if (padSize > 3)
            {
                bstream.skip(padSize - 3);
            }
            
            offset += padSize;
        }
        
        hash = loadStringTable(stream, offset);
        
        for (i = 0; i < nobject; i++)
        {
            objects[i].name = (String)hash.get(new Integer(offsets[i]));
        }
        
        bstream.close();
        return this;
    }
    
    /**
     * Loads the String Table from a AGI Object file. Internal Uses only.
     *
     * @param  stream       AGI Object file's Stream.
     * @param  offset       Starting offset.
     * @throws IOException  Caller must handle IOException from his stream.
     * @return Returns a Hashtable containing the strings with their offset has
     *         the Hash key.
     */    
    protected static Hashtable loadStringTable(InputStream stream, int offset) throws IOException
    {
        Hashtable h = new Hashtable(64);
        String    o = new String();
        int       s = offset;
        int       c;
        
        while (true)
        {
            c = stream.read();
            offset++;
            
            if (c < 0)
            {
                break;
            }
            
            if (c == 0)
            {
                h.put(new Integer(s), o);
                o = new String();
                s = offset;
            }
            else
            {
                o += (char)c;
            }
        }

        return h;
    }
    
    /**
     * Returns the number of objects contained in this object.
     *
     * @return Returns the number of objects.
     */    
    public short getCount()
    {
        return (short)objects.length;
    }
    
   /**
    * Returns an Object contained in this object based on his index.
    *
    * @param  index Index number of the wanted object.
    * @return Returns the wanted object.
    */    
    public InventoryObject getObject(short index)
    {
        return objects[index];
    }
    
    public void resetLocationTable(short[] locations)
    {
        int i;
    
        for (i = 0; i < objects.length; i++)
        {
            locations[i] = objects[i].getLocation();
        }
    }
}