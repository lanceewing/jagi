
package com.sierra.jit;

import java.io.*;

public class ConstantNameAndType extends Constant
{
    protected int nameIndex;
    protected int typeIndex;

    public ConstantNameAndType(int nameIndex, int typeIndex)
    {
        this.nameIndex = nameIndex;
        this.typeIndex = typeIndex;
    }

    public boolean equals(Object o)
    {
        if (o instanceof ConstantNameAndType)
        {
            ConstantNameAndType c = (ConstantNameAndType)o;

            return (c.nameIndex == nameIndex) && (c.typeIndex == typeIndex);
        }

        return false;
    }

    public int hashCode()
    {
        return typeIndex;
    }

    public void compile(DataOutputStream outs) throws IOException
    {
        outs.write(12);
        outs.writeShort(nameIndex);
        outs.writeShort(typeIndex);
    }
}
