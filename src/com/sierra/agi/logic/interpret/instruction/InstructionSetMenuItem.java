/*
 * InstructionSetMenuItem.java
 */

package com.sierra.agi.logic.interpret.instruction;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.logic.interpret.jit.*;
import com.sierra.jit.code.*;
import java.io.*;

/**
 * Set Menu Item Instruction.
 *
 * <P><CODE><B>set.menu.item</B> Instruction 0x9d</CODE><BR>
 * <CODE>m[p1]</CODE> is used as a menu element, where <CODE>p2</CODE> is this
 * element's code (a number between 0 and 255).
 * </P>
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class InstructionSetMenuItem extends InstructionBi
{
    /** 
     * Creates new Set Menu Item Instruction.
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     * @throws IOException I/O Exception are throw when <CODE>stream.read()</CODE> fails.
     */
    public InstructionSetMenuItem(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
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
        logicContext.getMenuBar().addMenuItem(logicContext.processMessage(logic.getMessageProcessed(p1)), p2);
        return 3;
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
        return new String[] {"set.menu.item", "m" + p1, "c" + p2};
    }
//#endif DEBUG
}