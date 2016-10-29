/**
 *  InstructionReturn.java
 *  Adventure Game Interpreter Logic Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.logic.interpret.instruction;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.logic.interpret.jit.*;
import java.io.*;

/**
 * Return Instruction.
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public final class InstructionReturn extends Instruction implements Compilable
{
    /**
     * Creates a new Return Instruction.
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     */
    public InstructionReturn(InputStream stream, LogicReader reader, short bytecode, short engineEmulation)
    {
    }

    public InstructionReturn()
    {
    }

    /**
     * Execute the Instruction.
     *
     * @param logic         Logic used to execute the instruction.
     * @param logicContext  Logic Context used to execute the instruction.
     * @return Returns <CODE>0</CODE>.
     */
    public int execute(Logic logic, LogicContext logicContext)
    {
        throw new LogicReturn();
    }
    
    /**
     * Compile the Instruction into Java Bytecode.
     *
     * @param compileContext Logic Compile Context.
     */
    public void compile(LogicCompileContext compileContext)
    {
        compileContext.scope.addReturns();
    }
    
//#ifdef DEBUG
    /**
     * Retreive the AGI Instruction name and parameters.
     * <B>For debugging purpose only. Will be removed in final releases.</B>
     *
     * @return Returns the textual names of the instruction.
     */
    public String[] getNames()
    {
        return new String[] {"return"};
    }
//#endif DEBUG
}