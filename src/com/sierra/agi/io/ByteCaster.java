/**
 *  ByteCaster.java
 *  Adventure Game Interpreter I/O Package
 *
 *  Created by Dr. Z
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.io;

import java.io.*;

/**
 * Interprets byte arrays.
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
abstract public class ByteCaster extends Object
{
    public static short hiloUnsignedByte(byte b[], int off)
    {
        return (short)(b[off] & 0xFF);
    }
    
    public static int hiloUnsignedShort(byte b[], int off)
    {
        return ((b[off]   & 0xFF) << 8) |
                (b[off+1] & 0xFF);
    }

    public static long hiloUnsignedInt(byte b[], int off)
    {
        return ((b[off]   & 0xFF) << 24) |
               ((b[off+1] & 0xFF) << 16) |
               ((b[off+2] & 0xFF) << 8)  |
                (b[off+3] & 0xFF);
    }

    public static short lohiUnsignedByte(byte b[], int off)
    {
        return (short)(b[off] & 0xFF);
    }
    
    public static int lohiUnsignedShort(byte b[], int off)
    {
        return ((b[off+1] & 0xFF) << 8) |
                (b[off]   & 0xFF);
    }

    public static long lohiUnsignedInt(byte b[], int off)
    {
        return ((b[off+3] & 0xFF) << 24) |
               ((b[off+2] & 0xFF) << 16) |
               ((b[off+1] & 0xFF) << 8)  |
                (b[off]   & 0xFF);
    }
}