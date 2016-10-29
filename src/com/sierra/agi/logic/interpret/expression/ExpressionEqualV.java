package com.sierra.agi.logic.interpret.expression;

import java.io.IOException;
import java.io.InputStream;

import com.sierra.agi.logic.Logic;
import com.sierra.agi.logic.LogicContext;
import com.sierra.agi.logic.interpret.LogicReader;
import com.sierra.agi.logic.interpret.jit.CompilableExpression;
import com.sierra.agi.logic.interpret.jit.LogicCompileContext;
import com.sierra.jit.code.InstructionConditionalGoto;
import com.sierra.jit.code.Scope;

public class ExpressionEqualV extends ExpressionBi implements CompilableExpression
{
    /**
     * Creates a new Equal Expression (V).
     *
     * @param context   Game context where this instance of the expression will be used.
     * @param stream    Logic Stream. Expression must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this expression.
     * @param bytecode  Bytecode of the current expression.
     */
    public ExpressionEqualV(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws IOException
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
        short p = logicContext.getVar(p2);
        
        return logicContext.getVar(p1) == p;
    }

    public void compile(LogicCompileContext compileContext, boolean jumpOnTrue, String destination)
    {
        Scope scope = compileContext.scope;
        
        compileContext.compileGetVariableValue(p1);
        compileContext.compileGetVariableValue(p2);
        
        scope.addConditionalGoto(
            jumpOnTrue? InstructionConditionalGoto.CONDITION_CMPEQ: InstructionConditionalGoto.CONDITION_CMPNE,
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
        
        names[0] = "equalv";
        names[1] = "v" + p1;
        names[2] = "v" + p2;
        
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
        buffer.append(" == ");
        buffer.append("v");
        buffer.append(p2);
        return buffer.toString();
    }
//#endif DEBUG
}