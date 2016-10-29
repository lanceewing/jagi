/*
 * InstructionMultiply.java
 */

package com.sierra.agi.logic.interpret.instruction;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.logic.interpret.jit.*;
import com.sierra.jit.code.*;
import java.io.*;

/**
 * Multiply Instruction.
 *
 * <P><CODE><B>mul.n</B> Instruction 0xa5</CODE><BR>
 * The value of variable <CODE>v[p1]</CODE> is multiplied by <CODE>p2</CODE>,
 * i.e. <CODE>v[p1] *= p2</CODE>.
 * </P>
 *
 * <P><CODE><B>mul.v</B> Instruction 0xa6</CODE><BR>
 * The value of variable <CODE>v[p1]</CODE> is multiplied by <CODE>v[p2]</CODE>,
 * i.e. <CODE>v[p1] *= v[p2]</CODE>.
 * </P>
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class InstructionMultiply extends InstructionBi
{
    /** 
     * Creates new Multiply Instruction.
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     * @throws IOException I/O Exception are throw when <CODE>stream.read()</CODE> fails.
     */
    public InstructionMultiply(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
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
        short v = p2;

        v *= logicContext.getVar(p1);

        logicContext.setVar(p1, (short)(v & 0xff));
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
        String[] names = new String[3];
        
        names[0] = "mul";
        names[1] = "v" + p1;
        names[2] = Integer.toString(p2);
            
        return names;
    }
//#endif DEBUG
}