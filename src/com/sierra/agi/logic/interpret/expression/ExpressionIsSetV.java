/**
 *  ExpressionIsSet.java
 *  Adventure Game Interpreter Logic Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.logic.interpret.expression;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.logic.interpret.jit.*;
import com.sierra.jit.code.*;
import java.io.*;

/**
 * Is Set Expression.
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public final class ExpressionIsSetV extends ExpressionUni implements CompilableExpression
{
    /**
     * Creates a new Is Set Expression (V).
     *
     * @param context   Game context where this instance of the expression will be used.
     * @param stream    Logic Stream. Expression must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this expression.
     * @param bytecode  Bytecode of the current expression.
     */
    public ExpressionIsSetV(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
    {
        super(stream, bytecode);
    }

    /**
     * Evaluate Expression.
     *
     * @param logic         Logic used to evaluate the expression.
     * @param logicContext  Logic Context used to evaluate the expression.
     * @return Returns the result of the evaluation.
     */
    public boolean evaluate(Logic logic, LogicContext logicContext)
    {
        short p = logicContext.getVar(p1);

        return logicContext.getFlag(p);
    }

    public void compile(LogicCompileContext compileContext, boolean jumpOnTrue, String destination)
    {
        Scope scope = compileContext.scope;
        
        scope.addLoadVariable("logicContext");
        
        compileContext.compileGetVariableValue(p1);
        
        scope.addInvokeVirtual("com.sierra.agi.logic.LogicContext", "getFlag", "(S)Z");
        
        scope.addConditionalGoto(
            jumpOnTrue? InstructionConditionalGoto.CONDITION_IFNE: InstructionConditionalGoto.CONDITION_IFEQ,
            destination);
    }

//#ifdef DEBUG
    /**
     * Retreive the AGI Expression name and parameters.
     * <B>For debugging purpose only. Will be removed in final releases.</B>
     *
     * @return Returns the textual name of the expression.
     */
    public String[] getNames()
    {
        String[] names = new String[2];
        
        names[0] = "issetv";
        names[1] = "vf" + p1;
        
        return names;
    }
//#endif DEBUG
}