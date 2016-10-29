
package com.sierra.jit;

import java.io.*;
import java.util.*;

public class ExceptionsAttribute extends Attribute
{
    protected Vector exceptions = new Vector();

    public ExceptionsAttribute(int nameIndex)
    {
        super(nameIndex);
    }

    public void addException(int exceptionIndex)
    {
        Integer i = new Integer(exceptionIndex);

        if (!exceptions.contains(i))
        {
            exceptions.add(i);
        }
    }

    public void compile(DataOutputStream outs) throws IOException
    {
        Integer     in = null;
        Enumeration en;

        super.compile(outs);
        outs.writeShort(exceptions.size());
        en = exceptions.elements();

        while (en.hasMoreElements())
        {
            in = (Integer)en.nextElement();
            outs.writeShort(in.shortValue());
        }
    }

    public int getSize()
    {
        return 2 + (exceptions.size() * 2);
    }
}
