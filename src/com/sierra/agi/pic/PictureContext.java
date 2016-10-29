/*
 *  PictureContext.java
 *  Adventure Game Interpreter Picture Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.pic;

import java.awt.*;
import java.awt.image.*;
import java.util.Arrays;
import com.sierra.agi.awt.*;
import com.sierra.agi.view.*;

/**
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class PictureContext extends Object
{
    /** Picture Dimensions */
    public int width  = 160;
    public int height = 168;
    
    /** Picture data. */
    public int[] picData;
    
    /** Priority data. */
    public byte[] priData;

    /** Picture Picture Color. */
    public int picColor = -1;

    /** Picture Priority Color. */
    public byte priColor = -1;
    
    /** Pen Style */
    public byte penStyle = 0;
    
    protected int[] pixel = new int[1];

    protected int whitePixel;

    /** Creates new Picture Context. */
    public PictureContext()
    {
        picData = new int [width * height];
        priData = new byte[width * height];

        whitePixel = translatePixel((byte)15);

        Arrays.fill(picData, whitePixel);
        Arrays.fill(priData, (byte)4);
    }
    
    /**
     * Clips a variable with a maximum.
     *
     * @param v    Variable to be clipped.
     * @param max  Maximum value that the to be clipped variable can have.
     * @return     The Variable clipped.
     */    
    public static int clip(int v, int max)
    {
        if (v > max)
            v = max;
        
        return v;
    }
    
    public int translatePixel(byte b)
    {
        if (b == -1)
        {
            return -1;
        }
        else
        {
            EgaUtils.getNativeColorModel().getDataElements(EgaUtils.getIndexColorModel().getRGB(b), pixel);
            return pixel[0];
        }
    }
    
    /**
     * Obtain the index in the buffer where (x,y) is located.
     *
     * @param  x X coordinate.
     * @param  y Y coordinate.
     * @return Index in the buffer.
     */    
    public final int getIndex(int x, int y)
    {
        return (y * width) + x;
    }
    
    /**
     * Obtain the color of the pixel asked.
     *
     * @param  x X coordinate.
     * @param  y Y coordinate.
     * @return Color at the specified pixel.
     */    
    public final int getPixel(int x, int y)
    {
        return picData[(y * width) + x];
    }

    /**
     * Obtain the priority of the pixel asked. 
     *
     * @param  x X coordinate.
     * @param  y Y coordinate.
     * @return Priority at the specified pixel.
     */    
    public final byte getPriorityPixel(int x, int y)
    {
        return priData[(y * width) + x];
    }
    
    /**
     * Set the (x,y) pixel to the current color and priority.
     *
     * @see #picColor
     * @see #priColor
     * @param x X coordinate.
     * @param y Y coordinate.
     */
    public final void putPixel(int x, int y)
    {
        int i;
        
        if ((x >= width) || (y >= height))
        {
            return;
        }
        
        i = (y * width) + x;
        
        if (picColor >= 0)
        {
            picData[i] = picColor;
        }
        
        if (priColor >= 0)
        {
            priData[i] = priColor;
        }
    }
    
    /**
     * Draw a line with current color and current priority.
     *
     * @param x1 Start X Coordinate.
     * @param y1 Start Y Coordinate.
     * @param x2 End X Coordinate.
     * @param y2 End Y Coordinate.
     * @see #picColor
     * @see #priColor
     * @see #putPixel(int,int)
     */
    public void drawLine(int x1, int y1, int x2, int y2)
    {
        int x, y;
        
        /* Clip! */
        x1 = clip(x1, width  - 1);
        x2 = clip(x2, width  - 1);
        y1 = clip(y1, height - 1);
        y2 = clip(y2, height - 1);
        
        /* Vertical Line */
        if (x1 == x2)
        {
            if (y1 > y2)
            {
                y  = y1;
                y1 = y2;
                y2 = y;
            }
            
            for( ; y1 <= y2; y1++)
            {
                putPixel(x1, y1);
            }
        }
        /* Horizontal Line */
        else if (y1 == y2)
        {
            if (x1 > x2)
            {
                x  = x1;
                x1 = x2;
                x2 = x;
            }

            for( ; x1 <= x2; x1++)
            {
                putPixel(x1, y1);
            }
        }
        else
        { 
            int deltaX = x2 - x1;
            int deltaY = y2 - y1;
            int stepX  = 1;
            int stepY  = 1;
            int detDelta;
            int errorX;
            int errorY;
            int count;
            
            if (deltaY < 0)
            {
                stepY  = -1;
                deltaY = -deltaY;
            }

            if (deltaX < 0)
            {
                stepX  = -1;
                deltaX = -deltaX;
            }

            if (deltaY > deltaX)
            {
                count    = deltaY;
                detDelta = deltaY;
                errorX   = deltaY / 2;
                errorY   = 0;
            }
            else
            {
                count    = deltaX;
                detDelta = deltaX;
                errorX   = 0;
                errorY   = deltaX / 2;
            }

            x = x1;
            y = y1;
            putPixel(x, y);
            
            do
            {
                errorY = (errorY + deltaY);
		if (errorY >= detDelta)
		{
			errorY -= detDelta;
			y      += stepY;
		}

		errorX = (errorX + deltaX);
		if (errorX >= detDelta)
		{
			errorX -= detDelta;
			x      += stepX;
		}
                
		putPixel(x, y);
		count--;
            } while(count > 0);
            
            putPixel(x, y);
        }
    }

    public boolean isFillCorrect(int x, int y)
    {
	if ((picColor < 0) && (priColor < 0))
        {
            return false;
        }

	if ((priColor < 0) && (picColor >= 0) && (picColor != whitePixel))
	{
            return (getPixel(x, y) == whitePixel);
	}

	if ((priColor >= 0) && (picColor < 0) && (priColor != 4))
	{
            return (getPriorityPixel(x, y) == 4);
	}

	return ((picColor >= 0) && (getPixel(x, y) == whitePixel) && (picColor != whitePixel));
    }

    protected Image loadImage(Toolkit toolkit, byte[] data)
    {
        MemoryImageSource mis;

        if (toolkit == null)
        {
            toolkit = Toolkit.getDefaultToolkit();
        }

        mis = new MemoryImageSource(width, height, EgaUtils.getIndexColorModel(), data, 0, width);
        return toolkit.createImage(mis);
    }
    
    protected Image loadImage(Toolkit toolkit, int[] data)
    {
        MemoryImageSource mis;

        if (toolkit == null)
        {
            toolkit = Toolkit.getDefaultToolkit();
        }

        mis = new MemoryImageSource(width, height, EgaUtils.getNativeColorModel(), data, 0, width);
        return toolkit.createImage(mis);
    }
    
    public void addToPic(Cell cell, int x, int y, byte pri, int mar)
    {
        int d[] = cell.getPixelData();
        int i, j, k, l, w, we, t;
        
        y -= cell.getHeight() - 1;
        l  = d.length;
        w  = cell.getWidth();
        k  = (y * width) + x;
        t  = cell.getTransparentPixel();
        
        for (i = 0; i < l; i += w)
        {
            we = i + w;
            
            for (j = i; j < we; j++)
            {
                if (d[j] != t)
                {
                    if (priData[k] <= pri)
                    {
                        picData[k] = d[j];
                        priData[k] = pri;
                    }
                }
                
                k++;
            }
            
            k += (width - w);
        }
    }

    public Image getPictureImage(Toolkit toolkit)
    {
        return loadImage(toolkit, picData);
    }
    
    public Image getPriorityImage(Toolkit toolkit)
    {
        return loadImage(toolkit, priData);
    }
    
    public int[] getPictureData()
    {
        return picData;
    }
    
    public byte[] getPriorityData()
    {
        return priData;
    }
}