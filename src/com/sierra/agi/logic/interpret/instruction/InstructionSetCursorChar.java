/*
 * InstructionSetCursorChar.java
 */

package com.sierra.agi.logic.interpret.instruction;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.logic.interpret.jit.*;
import com.sierra.jit.code.*;
import java.io.*;

/**
 * Set Cursor Char Instruction.
 *
 * <P><CODE><B>set.cursor.char</B> Instruction 0x6c</CODE><BR>
 * First byte of the <CODE>m[p1]</CODE> is used as a text mode cursor.
 * </P>
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class InstructionSetCursorChar extends InstructionUni
{
    /** 
     * Creates new Set Cursor Char Instruction.
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     * @throws IOException I/O Exception are throw when <CODE>stream.read()</CODE> fails.
     */
    public InstructionSetCursorChar(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
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
        logicContext.getViewScreen().setCursorChar(logicContext.processMessage(logic.getMessageProcessed(p1)).charAt(0));
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
        return new String[] {"set.cursor.char", "m" + p1};
    }
//#endif DEBUG
}