/**
 *  ExceptionTableEntry.java
 *  Just-in-Time Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.jit.code;

import java.io.*;

public class ExceptionTableEntry extends Object
{
    public int start;
    public int end;
    public int handler;
    public int type;
    
    public void compile(DataOutputStream outs) throws IOException
    {
        outs.writeShort(start);
        outs.writeShort(end);
        outs.writeShort(handler);
        outs.writeShort(type);
    }
}
