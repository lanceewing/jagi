
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
        Enumeration enum;

        super.compile(outs);
        outs.writeShort(exceptions.size());
        enum = exceptions.elements();

        while (enum.hasMoreElements())
        {
            in = (Integer)enum.nextElement();
            outs.writeShort(in.shortValue());
        }
    }

    public int getSize()
    {
        return 2 + (exceptions.size() * 2);
    }
}
