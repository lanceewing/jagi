/**
 *  Instruction.java
 *  Adventure Game Interpreter Logic Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.logic.interpret.instruction;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import java.io.*;

/**
 * Base Class for all Logic's Instructions.
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public abstract class Instruction extends Object
{
    /**
     * Creates a new Instruction Does absolutly nohting in this class, it is
     * included as a formal declaration.
     */
    protected Instruction()
    {
    }
    
    /**
     * Creates a new Instruction. Does absolutly nothing in this class, it is
     * included as a formal declaration.
     *
     * @param context   Game context where this instance of the instruction will be used.
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction.
     * @param bytecode  Bytecode of the current instruction.
     */
    protected Instruction(InputStream stream, LogicReader reader, short bytecode, short engineEmulation)
    {
    }

    /**
     * Execute the Instruction.
     *
     * @param logic         Logic used to execute the instruction.
     * @param logicContext  Logic Context used to execute the instruction.
     * @return Returns the number of byte of the uninterpreted instruction.
     */
    public abstract int execute(Logic logic, LogicContext logicContext) throws LogicException, Exception;

//#ifdef DEBUG
    /**
     * Retreive the AGI Instruction name and parameters.
     * <B>For debugging purpose only. Will be removed in final releases.</B>
     *
     * @return Returns the textual name of the instruction.
     */
    public abstract String[] getNames();
    
    public String toString()
    {
        String[]     names = getNames();
        StringBuffer buff  = new StringBuffer(32);
        
        buff.append(names[0]);
        
        if (names.length > 1)
        {
            int i;
            
            buff.append("(");
            for (i = 1; i < names.length; i++)
            {
                if (i != 1)
                {
                    buff.append(",");
                }
                
                buff.append(names[i]);
            }
            buff.append(")");
        }
        
        return buff.toString();
    }
//#endif DEBUG

    /**
     * Determine Instruction Size. In this class, it always return 1. (It is the
     * size of a instruction that has no parameter.)
     *
     * @return Returns the instruction size.
     */
    public int getSize()
    {
        return 1;
    }
}