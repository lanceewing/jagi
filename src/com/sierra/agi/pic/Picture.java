/*
 *  Picture.java
 *  Adventure Game Interpreter Picture Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.pic;

import com.sierra.agi.awt.EgaUtils;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

/**
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class Picture extends Object
{
    protected Vector entries;

    /** Creates new Picture */
    public Picture(Vector entries)
    {
        this.entries = entries;
    }
   
    public PictureContext draw() throws PictureException
    {
        PictureContext pictureContext = new PictureContext();
        
        draw(pictureContext);
        return pictureContext;
    }
    
    public void draw(PictureContext pictureContext) throws PictureException
    {
        Enumeration  enum = entries.elements();
        PictureEntry entry;
        
        while (enum.hasMoreElements())
        {
            entry = (PictureEntry)enum.nextElement();
            entry.draw(pictureContext);
        }
    }
}