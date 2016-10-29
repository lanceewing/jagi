/**
 *  IOUtils.java
 *  Adventure Game Interpreter I/O Package
 *
 *  Created by Dr. Z
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.io;

import java.io.*;

public abstract class IOUtils extends Object
{
    public static int fill(InputStream in, byte b[], int off, int len) throws IOException
    {
        int c, r = 0;
        
        while (len != 0)
        {
            c = in.read(b, off, len);

            if (c <= 0)
            {
                throw new EOFException();
            }

            r   += c;
            off += c;
            len -= c;
        }

        return r;
    }

    public static int skip(InputStream in, int len) throws IOException
    {
        int c, r = 0;
        
        while (len != 0)
        {
            c = (int)in.skip(len);

            if (c <= 0)
            {
                throw new EOFException();
            }
            
            r   += c;
            len -= c;
        }

        return r;
    }
};