/**
 *  Attribute.java
 *  Just-in-Time Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.jit;

import java.io.*;

public abstract class Attribute extends Object
{
    protected int nameIndex;

    public Attribute(int nameIndex)
    {
        this.nameIndex = nameIndex;
    }

    public void compile(DataOutputStream outs) throws IOException
    {
        outs.writeShort(nameIndex);
        outs.writeInt(getSize());
    }
    
    public abstract int getSize();
}
