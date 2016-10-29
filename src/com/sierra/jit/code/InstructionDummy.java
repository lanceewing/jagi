/**
 *  InstructionDummy.java
 *  Just-in-Time Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.jit.code;

import java.io.*;

public class InstructionDummy extends Instruction
{
    protected byte[] data;
    protected int    pop;
    protected int    push;
    
    protected static byte[] noData;
    
    public InstructionDummy(int pop, int push)
    {
        if (noData == null)
        {
            noData = new byte[0];
        }
    
        this.data = noData;
        this.pop  = pop;
        this.push = push;
    }

    public InstructionDummy(byte data, int pop, int push)
    {
        this.data = new byte[] {data};
        this.pop  = pop;
        this.push = push;
    }

    public InstructionDummy(byte[] data, int pop, int push)
    {
        this.data = data;
        this.pop  = pop;
        this.push = push;
    }

    public void compile(CompileContext context, Scope scope, DataOutputStream outs, int pc) throws IOException
    {
        if (data.length == 0)
        {
            return;
        }
    
        outs.write(data, 0, data.length);
    }
    
    public int getSize(CompileContext context, Scope scope, int pc)
    {
        return data.length;
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
