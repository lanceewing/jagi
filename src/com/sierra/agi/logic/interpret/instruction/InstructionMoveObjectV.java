/*
 * InstructionMoveObject.java
 */

package com.sierra.agi.logic.interpret.instruction;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.logic.interpret.jit.*;
import com.sierra.jit.code.*;
import java.io.*;

/**
 * Move Object Instruction.
 *
 * <P><CODE><B>move.object.n</B> Instruction 0x51</CODE><BR>
 * Object <CODE>o[p1]</CODE> is told to move to the point <CODE>(p2,p3)</CODE> by
 * <CODE>p4</CODE> pixels every step. When the destination is reached,
 * <CODE>f[p5]</CODE> is set to <CODE>true</CODE>. If <CODE>p1 == 0</CODE> (Ego),
 * <CODE>program.control</CODE> is executed automatically.
 * </P>
 *
 * <P><CODE><B>move.object.v</B> Instruction 0x52</CODE><BR>
 * Object <CODE>o[p1]</CODE> is told to move to the point <CODE>(v[p2],v[p3])</CODE> by
 * <CODE>v[p4]</CODE> pixels every step. When the destination is reached,
 * <CODE>f[p5]</CODE> is set to <CODE>true</CODE>. If <CODE>p1 == 0</CODE> (Ego),
 * <CODE>program.control</CODE> is executed automatically.
 * </P>
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class InstructionMoveObjectV extends InstructionPent
{
    /** 
     * Creates new Move Object Instruction (V).
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     * @throws IOException I/O Exception are throw when <CODE>stream.read()</CODE> fails.
     */
    public InstructionMoveObjectV(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
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
        com.sierra.agi.view.ViewTable viewTable = logicContext.getViewTable();
        viewTable.moveObject(
                    p1,
                    logicContext.getVar(p2),
                    logicContext.getVar(p3),
                    logicContext.getVar(p4),
                    p5);
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
        String[] names = new String[6];
        
        names[0] = "move.object.v";
        names[1] = "o" + p1;
        names[5] = "f" + p5;
        names[2] = "v" + p2;
        names[3] = "v" + p3;
        names[4] = "v" + p4;
        
        return names;
    }
//#endif DEBUG
}