/**
 *  ExpressionLess.java
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
 * Less Expression.
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public final class ExpressionLess extends ExpressionBi implements CompilableExpression
{
    /**
     * Creates a new Less Expression.
     *
     * @param context   Game context where this instance of the expression will be used.
     * @param stream    Logic Stream. Expression must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this expression.
     * @param bytecode  Bytecode of the current expression.
     */
    public ExpressionLess(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
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
        return logicContext.getVar(p1) < p2;
    }

    public void compile(LogicCompileContext compileContext, boolean jumpOnTrue, String destination)
    {
        Scope scope = compileContext.scope;
        
        compileContext.compileGetVariableValue(p1);
        
        scope.addPushConstant(p2);
        
        scope.addConditionalGoto(
            jumpOnTrue? InstructionConditionalGoto.CONDITION_CMPLT: InstructionConditionalGoto.CONDITION_CMPGE,
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
        String[] names = new String[3];
        
        names[0] = "less";
        names[1] = "v" + p1;
        names[2] = Integer.toString(p2);
        
        return names;
    }

    /**
     * Returns a String represention of the expression.
     * <B>For debugging purpose only. Will be removed in final releases.</B>
     *
     * @return Returns a String representation.
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer("v");
        buffer.append(p1);
        buffer.append(" < ");
        buffer.append(p2);
        return buffer.toString();
    }
//#endif DEBUG
}