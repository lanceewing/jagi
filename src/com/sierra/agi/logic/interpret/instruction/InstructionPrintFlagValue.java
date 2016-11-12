package com.sierra.agi.logic.interpret.instruction;

import java.io.IOException;
import java.io.InputStream;

import com.sierra.agi.logic.Logic;
import com.sierra.agi.logic.LogicContext;
import com.sierra.agi.logic.LogicException;
import com.sierra.agi.logic.interpret.LogicReader;
import com.sierra.agi.view.MessageBox;

public class InstructionPrintFlagValue extends InstructionUni {

    /** 
     * Creates new Print Flag Value instruction.
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     * @throws IOException I/O Exception are throw when <CODE>stream.read()</CODE> fails.
     */
    public InstructionPrintFlagValue(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException {
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
        short p = logicContext.getVar(p1);
        (new MessageBox("Var/Flag# " + p1 + " = " + p)).show(logicContext, logicContext.getViewScreen(), true);
        return 2;
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
        String[] names = new String[2];
        
        names[0] = "print.flag.value";
        names[1] = "v" + p1;
        
        return names;
    }
//#endif DEBUG
}
