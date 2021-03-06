/*
 * InstructionClearTextRect.java
 */

package com.sierra.agi.logic.interpret.instruction;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.logic.interpret.jit.*;
import com.sierra.jit.code.*;
import java.io.*;

/**
 * Clear Text Rectangle Instruction.
 *
 * <P><CODE><B>clear.text.rect</B> Instruction 0x9a</CODE><BR>
 * Clears a rectangular area with top left corner coordinates
 * (<CODE>p1</CODE>,<CODE>p2</CODE>) and bottom right coordinates
 * (<CODE>p3</CODE>,<CODE>p4</CODE>) using colour <CODE>p5</CODE>.
 * </P>
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class InstructionClearTextRect extends InstructionPent
{
    /** 
     * Creates new Clear Text Rectangle Instruction.
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     * @throws IOException I/O Exception are throw when <CODE>stream.read()</CODE> fails.
     */
    public InstructionClearTextRect(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
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
        return 6;
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
        return new String[] {"clear.text.rect", Integer.toString(p1), Integer.toString(p2), Integer.toString(p3), Integer.toString(p4), Integer.toString(p5)};
    }
//#endif DEBUG
}