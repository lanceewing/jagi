package com.sierra.agi.logic.interpret.instruction;

import java.io.IOException;
import java.io.InputStream;

import com.sierra.agi.logic.Logic;
import com.sierra.agi.logic.LogicContext;
import com.sierra.agi.logic.LogicException;
import com.sierra.agi.logic.interpret.LogicReader;

/**
 * Set Key Instruction.
 *
 * <P><CODE><B>set.key</B> Instruction 0x79</CODE><BR>
 * Set interpreter's special key. c is the key code (decimal number from 0 to
 * 255) and s (if the key is a regular, or CTRL+key pair). the ASCII code (for
 * example, TAB is 0x0009). If the key is a function key or ALT+key pair, the
 * corresponding IBM-PC keyboard scan code is in the high byte of s. For
 * example, the scan code of F1 is 0x3B00, ALT+Z is 0x2C00.
 * </P>
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class InstructionSetKey2 extends InstructionBi {

    /** 
     * Creates new Set Key Instruction (AGI v1 two argument version)
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     */
    public InstructionSetKey2(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException {
        super(stream, bytecode);
    }

    /**
     * Execute the Instruction.
     *
     * @param logic         Logic used to execute the instruction.
     * @param logicContext  Logic Context used to execute the instruction.
     * @return Returns the number of byte of the uninterpreted instruction.
     */
    @Override
    public int execute(Logic logic, LogicContext logicContext) throws LogicException, Exception {
        return 3;
    }

//#ifdef DEBUG
    /**
     * Retreive the AGI Instruction name and parameters.
     * <B>For debugging purpose only. Will be removed in final releases.</B>
     *
     * @return Returns the textual names of the instruction.
     */
    @Override
    public String[] getNames() {
        String[] names = new String[3];
        
        names[0] = "set.key";
        names[1] = Integer.toString(p1);
        names[2] = Integer.toString(p2);

        return names;
    }
//#endif DEBUG
}
