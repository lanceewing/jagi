/*
 * InstructionDecrement.java
 */

package com.sierra.agi.logic.interpret.instruction;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.logic.interpret.jit.*;
import com.sierra.jit.code.*;
import java.io.*;

/**
 * Decrement Instruction.
 *
 * The value of the variable v[p1] is decremented by one,
 * i.e. <CODE>v[p1] = v[p1] - 1</CODE>. If the value is <CODE>0</CODE>,
 * it is left unchanged.
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class InstructionDecrement extends InstructionUni implements Compilable
{
    /**
     * Creates a new Decrement Instruction.
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     * @throws IOException I/O Exception are throw when <CODE>stream.read()</CODE> fails.
     */
    public InstructionDecrement(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
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
        short vn = logicContext.getVar(p1);
        
        if (vn > 0)
        {
            logicContext.setVar(p1, (short)(vn - 1));
        }
        
        return 2;
    }

    /**
     * Compile the Instruction into Java Bytecode.
     *
     * @param compileContext Logic Compile Context.
     */
    public void compile(LogicCompileContext compileContext)
    {
        Scope  scope = compileContext.scope;
        String end   = scope.generateLabel();

        compileContext.compileGetVariableValue(p1);
        scope.addDuplicate();
        scope.addStoreVariable("temp");
        
        scope.addIfEqualZero(end);
        
        scope.addLoadVariable("logicContext");
        scope.addPushConstant(p1);
        scope.addLoadVariable("temp");
        scope.addPushConstant(1);
        scope.addIntegerSubstract();
        scope.addInvokeSpecial("com.sierra.agi.logic.LogicContext", "setVar", "(SS)V");
        
        scope.addLabel(end);
    }

//#ifdef DEBUG
    /**
     * Retreive the AGI Instruction name and parameters.
     * <B>For debugging purpose only. Will be removed in final releases.</B>
     *
     * @return Returns the textual name of the instruction.
     */
    public String[] getNames()
    {
        String[] names = new String[2];
        
        names[0] = "dec";
        names[1] = "v" + p1;
        return names;
    }
//#endif DEBUG
}