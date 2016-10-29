/*
 * InstructionBi.java
 */

package com.sierra.agi.logic.interpret.instruction;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import java.io.*;

/**
 * Base Class for all Logic's Instructions that has 2 parameters.
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public abstract class InstructionBi extends Instruction
{
    /** Bytecode */
    protected short bytecode;

    /** Parameter #1 */
    protected short p1;

    /** Parameter #2 */
    protected short p2;
    
    /**
     * Creates a new Instruction. It store the bytecode and read the 2 next byte
     * in the <CODE>stream</CODE> object.
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     * @throws IOException I/O Exception are throw when <CODE>stream.read()</CODE> fails.
     */
    protected InstructionBi(InputStream stream, short bytecode) throws IOException
    {
        this.bytecode = bytecode;
        this.p1       = (short)stream.read();
        this.p2       = (short)stream.read();
    }
    
    /**
     * Determine Instruction Size. In this class, it always return 3. (It is the
     * size of a instruction that has 2 parameters.)
     *
     * @return Returns the instruction size.
     */
    public int getSize()
    {
        return 3;
    }
}