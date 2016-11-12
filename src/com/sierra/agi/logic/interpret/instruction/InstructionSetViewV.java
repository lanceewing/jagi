/*
 * InstructionSetView.java
 */

package com.sierra.agi.logic.interpret.instruction;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.logic.interpret.jit.*;
import com.sierra.jit.code.*;
import java.io.*;

/**
 * Set View instruction.
 *
 * <P><CODE><B>set.view.n</B> Instruction 0x29</CODE><BR>
 * Object <CODE>p1</CODE> is associated with a View resource number
 * <CODE>p2</CODE>, which may be an image of the object.</P>
 *
 * <P><CODE><B>set.view.v</B> Instruction 0x2a</CODE><BR>
 * Object <CODE>p1</CODE> is associated with a View resource number
 * <CODE>v[p2]</CODE>, which may be an image of the object.</P>
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class InstructionSetViewV extends InstructionBi
{
    /** 
     * Creates new Set View Instruction (V).
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     */
    public InstructionSetViewV(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
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
        short p = logicContext.getVar(p2);
        logicContext.getViewTable().setView(p1, p);
        return 3;
    }

//#ifdef DEBUG
    /**
     * Retreive the textual name of the instruction.
     *
     * @return Returns the textual name of the instruction.
     */
    public String[] getNames()
    {
        String[] names = new String[3];
        
        names[0] = "set.view.v";
        names[1] = "o" + p1;
        names[2] = "v" + p2;

        return names;
    }
//#endif DEBUG
}