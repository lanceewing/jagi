/*
 *  ViewProvider.java
 *  Adventure Game Interpreter View Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.view;

import java.io.*;
import com.sierra.agi.io.*;

public class StandardViewProvider extends Object implements ViewProvider
{
    public StandardViewProvider()
    {
    }

    public View loadView(InputStream inputStream, int size) throws IOException, ViewException
    {
        byte   b[];
        int    i, j;
        short  loopCount;
        Loop[] loops       = null;
        String description = null;
        
        b = new byte[size];
        IOUtils.fill(inputStream, b, 0, size);
        inputStream.close();
        
        loopCount = (short)b[2];
        if ((b[3] != 0) || (b[4] != 0))
        {
            /* Reads Description */
            int desc = ByteCaster.lohiUnsignedShort(b, 3);
            
            i = desc;
            try
            {
                while (true)
                {
                    if (b[i] == 0)
                    {
                        break;
                    }
                
                    i++;
                }
            }
            catch (IndexOutOfBoundsException e)
            {
            }
            
            try
            {
                description = new String(b, desc, i - desc, "US-ASCII");
            }
            catch (UnsupportedEncodingException e)
            {
            }
        }
        
        j     = 5;
        loops = new Loop[loopCount];
        for (i = 0; i < loopCount; i++)
        {
            loops[i] = new Loop(b, ByteCaster.lohiUnsignedShort(b, j), i);
            j += 2;
        }
        
        return new View(loops, description);
    }
}
