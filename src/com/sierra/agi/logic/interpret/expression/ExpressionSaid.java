/**
 *  ExpressionSaid.java
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
import com.sierra.agi.io.*;
import com.sierra.agi.word.*;
import com.sierra.jit.*;
import com.sierra.jit.code.*;
import java.io.*;
import java.util.*;

/**
 * Said Expression.
 *
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public final class ExpressionSaid extends Expression implements CompilableExpression
{
    /** Word Numbers */
    protected int wordNumbers[];
    
    /**
     * Creates a new Said Expression.
     *
     * @param context   Game context where this instance of the expression will be used.
     * @param stream    Logic Stream. Expression must be written in uninterpreted format.
     * @param reader    LogicReader used in the reading of this expression.
     * @param bytecode  Bytecode of the current expression.
     */
    public ExpressionSaid(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws Exception
    {
        int              i, count;
        ByteCasterStream bstream = new ByteCasterStream(stream);
        
        count       = stream.read();
        wordNumbers = new int[count];
        
        for (i = 0; i < count; i++)
        {
            wordNumbers[i] = bstream.lohiReadUnsignedShort();
        }
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
        return logicContext.said(wordNumbers);
    }

    /**
     * Determine Expression Size.
     *
     * @return Returns the expression size.
     */
    public int getSize()
    {
        return 2 + (wordNumbers.length * 2);
    }

    public void compile(LogicCompileContext compileContext, boolean jumpOnTrue, String destination)
    {
        ClassCompiler classCompiler = compileContext.classCompiler;
        Scope         scope         = compileContext.scope;
        Scope         staticScope   = classCompiler.addStaticInitializer();
        String        fieldName     = "sf" + compileContext.pc;
        Field         field         = classCompiler.addField(fieldName, "[I");
        int           i;
        
        field.setStatic(true);
        field.setPrivate();
        
        staticScope.addNewArray("I");
        
        for (i = 0; i < wordNumbers.length; i++)
        {
            staticScope.addDuplicate();
            staticScope.addPushConstant(i);
            staticScope.addPushConstant(wordNumbers[i]);
            staticScope.addIntegerStore();
        }
        
        staticScope.addPutStatic(classCompiler.getClassName(), fieldName, "[I");
        
        scope.addLoadVariable("logicContext");
        scope.addGetStatic(classCompiler.getClassName(), fieldName, "[I");
        scope.addInvokeVirtual("com.sierra.agi.logic.LogicContext", "said", "([I)Z");
        
        scope.addConditionalGoto(
            jumpOnTrue? InstructionConditionalGoto.CONDITION_IFNE: InstructionConditionalGoto.CONDITION_IFEQ,
            destination);
    }
    
//#ifdef DEBUG
    /**
     * Retreive the AGI Expression name and parameters.
     * <B>For debugging purpose only. Will be removed in final releases.</B>
     *
     * @return Always return <CODE>null</CODE> in this implentation.
     */
    public String[] getNames()
    {
        String[] names = new String[1 + wordNumbers.length];
        int      i;
        
        names[0] = "said";
        
        for (i = 0; i < wordNumbers.length; i++)
        {
            names[i+1] = "w" + wordNumbers[i];
        }
        
        return names;
    }
//#endif DEBUG
}