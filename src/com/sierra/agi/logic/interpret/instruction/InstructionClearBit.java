package com.sierra.agi.logic.interpret.instruction;

import java.io.IOException;
import java.io.InputStream;

import com.sierra.agi.logic.Logic;
import com.sierra.agi.logic.LogicContext;
import com.sierra.agi.logic.LogicException;
import com.sierra.agi.logic.interpret.LogicReader;

public class InstructionClearBit extends InstructionBi {

    /** 
     * Creates new Clear Bit Instruction.
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     */
    public InstructionClearBit(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException {
        super(stream, bytecode);
    }

    @Override
    public int execute(Logic logic, LogicContext logicContext) throws LogicException, Exception {
        // TODO: Implement clearing/resetting of the specified bit in the var.
        return 3;
    }

    @Override
    public String[] getNames() {
        String[] names = new String[3];
        
        names[0] = "clear.bit";
        names[1] = Integer.toString(p1);
        names[2] = "v" + p2;

        return names;
    }
}
