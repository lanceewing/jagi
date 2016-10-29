/*
 * InstructionDisplay.java
 */

package com.sierra.agi.logic.interpret.instruction;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.logic.interpret.jit.*;
import com.sierra.jit.code.*;
import java.io.*;

/**
 * Display Instruction.
 *
 * <P><CODE><B>display.n</B> Instruction 0x67</CODE><BR>
 * Prints a message number <CODE>p1</CODE> in the row <CODE>p2</CODE>, starting
 * with the column <CODE>p3</CODE>. No window is created, so it is up to the
 * programmer to erase the output when it is no longer needed.
 * </P>
 *
 * <P><CODE><B>display.v</B> Instruction 0x68</CODE><BR>
 * Prints a message number <CODE>v[p1]</CODE> in the row <CODE>v[p2]</CODE>, starting
 * with the column <CODE>v[p3]</CODE>. No window is created, so it is up to the
 * programmer to erase the output when it is no longer needed.
 * </P>
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class InstructionDisplay extends InstructionTri
{
    /** 
     * Creates new Display Instruction.
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     * @throws IOException I/O Exception are throw when <CODE>stream.read()</CODE> fails.
     */
    public InstructionDisplay(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
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
        short tp1 = p1;
        short tp2 = p2;
        short tp3 = p3;

        logicContext.getViewScreen().displayLine(tp2, tp1, logicContext.processMessage(logic.getMessageProcessed(tp3)));
        return 4;
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
        String[] names = new String[4];
        
        names[0] = "display";
        names[1] = Integer.toString(p1);
        names[2] = Integer.toString(p2);
        names[3] = "m" + p3;

        return names;
    }
//#endif DEBUG
}