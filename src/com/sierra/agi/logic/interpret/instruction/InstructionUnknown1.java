package com.sierra.agi.logic.interpret.instruction;

import java.io.IOException;
import java.io.InputStream;

import com.sierra.agi.logic.Logic;
import com.sierra.agi.logic.LogicContext;
import com.sierra.agi.logic.LogicException;
import com.sierra.agi.logic.interpret.LogicReader;

public class InstructionUnknown1 extends InstructionUni {
    /** 
     * Creates new Unknown instruction with one parameter.
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     * @throws IOException I/O Exception are throw when <CODE>stream.read()</CODE> fails.
     */
    public InstructionUnknown1(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
    {
        super(stream, bytecode);
    }

    @Override
    public int execute(Logic logic, LogicContext logicContext) throws LogicException, Exception {
        return 2;
    }

    @Override
    public String[] getNames() {
        return new String[] {"unknown_" + Integer.toHexString(bytecode), Integer.toString(p1)};
    }
}
