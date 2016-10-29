/*
 * InstructionIgnoreBlocks.java
 */

package com.sierra.agi.logic.interpret.instruction;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.logic.interpret.jit.*;
import com.sierra.jit.code.*;
import java.io.*;

/**
 * Ignore Blocks Instruction.
 *
 * <P><CODE><B>Ignore Blocks</B> Instruction 0x58</CODE><BR>
 * Object <CODE>o[p1]</CODE> moves ignoring conditional barriers (pixels with
 * priority 1) and a block set with the block command.
 * </P>
 *
 * @see com.sierra.agi.logic.interpret.instruction.InstructionBlock
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class InstructionIgnoreBlocks extends InstructionUni
{
    /** 
     * Creates new Ignore Blocks Instruction.
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     * @throws IOException I/O Exception are throw when <CODE>stream.read()</CODE> fails.
     */
    public InstructionIgnoreBlocks(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
    {
        super(stream, bytecode);
    }
    
    /**
     * Execute the Instruction.
     *
     * @param logic         Logic used to execute the instruction.
     * @param logicContext  Logic Context used to execute the instruction.
     * @return Returns the number of byte of the uninterpreted instruction.
     */
    public int execute(Logic logic, LogicContext logicContext)
    {
        logicContext.getViewTable().ignoreBlocks(p1);
        return 2;
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
        String[] names = new String[2];
        
        names[0] = "ignore.blocks";
        names[1] = "o" + p1;
        
        return names;
    }
//#endif DEBUG
}