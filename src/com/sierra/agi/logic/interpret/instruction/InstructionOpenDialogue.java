/*
 * InstructionOpenDialogue.java
 */

package com.sierra.agi.logic.interpret.instruction;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.logic.interpret.jit.*;
import com.sierra.jit.code.*;
import java.io.*;

/**
 * Open Dialog Instruction.
 *
 * <P><CODE><B>open.dialog</B> Instruction 0xa3</CODE><BR>
 * Enables <CODE>get.string</CODE> and <CODE>get.num</CODE> commands if
 * <CODE>prevent.input</CODE> has been issued.
 * </P>
 *
 * @see     com.sierra.agi.logic.interpret.instruction.InstructionGetString
 * @see     com.sierra.agi.logic.interpret.instruction.InstructionGetNum
 * @see     com.sierra.agi.logic.interpret.instruction.InstructionPreventInput
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class InstructionOpenDialogue extends Instruction
{
    /** 
     * Creates new Open Dialog Instruction.
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     * @throws IOException I/O Exception are throw when <CODE>stream.read()</CODE> fails.
     */
    public InstructionOpenDialogue(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
    {
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
        return 1;
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
        return new String[] {"open.dialog"};
    }
//#endif DEBUG
}