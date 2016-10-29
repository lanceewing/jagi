/**
 *  AttributeCollection.java
 *  Just-in-Time Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.jit;

import java.io.*;
import java.util.*;

public class AttributeCollection extends Object
{
    protected Hashtable attributes;

    public AttributeCollection()
    {
        attributes = new Hashtable();
    }

    public void compile(DataOutputStream outs) throws IOException
    {
        Enumeration enum;
    
        outs.writeShort(attributes.size());
        enum = attributes.elements();
    
        while (enum.hasMoreElements())
        {
            ((Attribute)enum.nextElement()).compile(outs);
        }
    }

    public Attribute get(String name)
    {
        return (Attribute)attributes.get(name);
    }

    public void put(String name, Attribute attribute)
    {
        attributes.put(name, attribute);
    }

    public int getSize()
    {
        Enumeration enum = attributes.elements();
        int         size = 2;
    
        while (enum.hasMoreElements())
        {
            size += ((Attribute)enum.nextElement()).getSize() + 6;
        }
        
        return size;
    }
}
