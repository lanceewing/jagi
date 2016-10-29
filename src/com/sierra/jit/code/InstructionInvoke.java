/*
 *  InstructionInvokeSpecial.java
 *  Just-in-Time Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.jit.code;

import java.io.*;

public class InstructionInvoke extends Instruction
{
    protected int methodNumber;
    protected int methodType;
    
    protected int pop;
    protected int push;
    
    public static final int TYPE_VIRTUAL = 0;
    public static final int TYPE_SPECIAL = 1;
    public static final int TYPE_STATIC  = 2;
    
    public InstructionInvoke(int methodNumber, int methodType, String signature)
    {
        this.methodNumber = methodNumber;
        this.methodType   = methodType;
        
        DescriptorTokenizer tokenizer = new DescriptorTokenizer(signature);
        
        while (tokenizer.hasMoreElements())
        {
            pop += CompileContext.getTypeSize((String)tokenizer.nextElement());
        }
        
        switch (methodType)
        {
        case TYPE_STATIC:
            break;
            
        default:
            pop++;
            break;
        }
        
        push += CompileContext.getTypeSize(tokenizer.getReturnValue());
    }

    public void compile(CompileContext context, Scope scope, DataOutputStream outs, int pc) throws IOException
    {
        outs.write(0xb6 + methodType);
        outs.writeShort(methodNumber);
    }
    
    public int getSize(CompileContext context, Scope scope, int pc)
    {
        return 3;
    }

    public int getPopCount()
    {
        return pop;
    }

    public int getPushCount()
    {
        return push;
    }
}
