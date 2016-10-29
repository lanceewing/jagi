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
        Enumeration en;
    
        outs.writeShort(attributes.size());
        en = attributes.elements();
    
        while (en.hasMoreElements())
        {
            ((Attribute)en.nextElement()).compile(outs);
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
        Enumeration en = attributes.elements();
        int         size = 2;
    
        while (en.hasMoreElements())
        {
            size += ((Attribute)en.nextElement()).getSize() + 6;
        }
        
        return size;
    }
}
