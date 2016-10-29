
package com.sierra.jit;

import com.sierra.jit.code.*;
import java.io.*;
import java.util.*;

public class Code extends Attribute
{
    protected AttributeCollection attributes = new AttributeCollection();
    protected ConstantPool        constants;
    protected Scope               contents;
    protected String              returnType;

    protected int                 counter;
    protected int                 maxStack  = -1;
    protected int                 maxLocals = -1;

    public Code(ConstantPool constants, int nameIndex, int descriptionIndex, boolean isStatic)
    {
        super(nameIndex);
        this.constants = constants;
        this.contents  = new ScopeArgument(constants, this, (String)constants.getContent(descriptionIndex), isStatic);
    }

    public void clearContext()
    {
        context = null;
    }

    public void setMaxLocals(int maxLocals)
    {
        this.maxLocals = maxLocals;
    }
    
    public void setMaxStack(int maxStack)
    {
        this.maxStack = maxStack;
    }

    public void setReturnType(String type)
    {
        returnType = type;
    }
    
    public String getReturnType()
    {
        return returnType;
    }

    protected CompileContext context;

    public void compile()
    {
        if (context == null)
        {
            try
            {
                context = contents.compile();
            }
            catch (IOException ioex)
            {
            }
        }
    }

    public void compile(DataOutputStream outs) throws IOException
    {
        Enumeration enum;
        Vector      exceptions;
        
        super.compile(outs);
        compile();
        
        outs.writeShort(maxStack  == -1? context.getMaxLocal(): maxStack);
        outs.writeShort(maxLocals == -1? context.getMaxLocal(): maxLocals);
        
        outs.writeInt(context.getSize());
        outs.write(context.getData());
        
        exceptions = context.getExceptions();
        enum       = exceptions.elements();
        outs.writeShort(exceptions.size());
        
        while (enum.hasMoreElements())
        {
            ((ExceptionTableEntry)enum.nextElement()).compile(outs);
        }
        
        attributes.compile(outs);
    }
    
    public int getSize()
    {
        int size = attributes.getSize() + 2 + 2 + 4 + 2;
    
        compile();
        
        size += (context.getExceptions().size() * 8);
        size += context.getSize();
        return size;
    }
    
    public Scope getScope()
    {
        return contents;
    }
    
    public String generateLabel()
    {
        return "<generated" + counter++ + ">";
    }
}
