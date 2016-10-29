/*
 * InstructionLoadPic.java
 */

package com.sierra.agi.logic.interpret.instruction;

import com.sierra.agi.*;
import com.sierra.agi.res.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.logic.interpret.jit.*;
import com.sierra.jit.code.*;
import java.io.*;

/**
 * Load Picture Instruction.
 *
 * <P><CODE><B>load.pic.v</B> Instruction 0x18</CODE><BR>
 * Picture <CODE>v[p1]</CODE> is loaded into memory.</P>
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class InstructionLoadPic extends InstructionUni implements Compilable
{
    /** 
     * Creates new Load Picture Instruction.
     *
     * @param context   Game context where this instance of the instruction will be used. (ignored)
     * @param stream    Logic Stream. Instruction must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this instruction. (ignored)
     * @param bytecode  Bytecode of the current instruction.
     * @throws IOException I/O Exception are throw when <CODE>stream.read()</CODE> fails.
     */
    public InstructionLoadPic(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
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
    public int execute(Logic logic, LogicContext logicContext) throws Exception
    {
        logicContext.getCache().loadPicture(logicContext.getVar(p1));
        return 2;
    }

    /**
     * Compile the Instruction into Java Bytecode.
     *
     * @param compileContext Logic Compile Context.
     */
    public void compile(LogicCompileContext compileContext)
    {
        Scope scope = compileContext.scope;

        scope.addLoadVariable("cache");
        compileContext.compileGetVariableValue(p1);
        
        scope.addInvokeSpecial("com.sierra.agi.res.ResourceCache", "loadPicture", "(S)V");
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
        String[] names = new String[2];

        names[0] = "load.pic";
        names[1] = "v" + p1;
        
        return names;
    }
//#endif DEBUG
}