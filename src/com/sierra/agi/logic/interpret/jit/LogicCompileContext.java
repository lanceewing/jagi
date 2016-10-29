/*
 *  LogicCompileContext.java
 *  AGI Debugger
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.logic.interpret.jit;

import com.sierra.agi.logic.*;
import com.sierra.jit.*;
import com.sierra.jit.code.*;

public class LogicCompileContext extends Object
{
    public int           pc;
    public Scope         scope;
    public ClassCompiler classCompiler;
    public Logic         logic;
    
    public LogicCompileContext()
    {
    }
    
    public void compileGetVariableValue(short variable)
    {
        Scope scope = this.scope;
        
        scope.addLoadVariable("logicContext");
        scope.addPushConstant(variable);
        scope.addInvokeSpecial("com.sierra.agi.logic.LogicContext", "getVar", "(S)S");
    }
    
    public void compilePushString(String string)
    {
        if (needProcessing(string))
        {
            scope.addLoadVariable("logicContext");
            scope.addPushConstant(string);
            scope.addInvokeSpecial("com.sierra.agi.logic.LogicContext", "processMessage", "(Ljava/lang/string;)Ljava/lang/string;");
        }
        else
        {
            scope.addPushConstant(string);
        }
    }
    
    protected boolean needProcessing(String string)
    {
        if (string.indexOf("%g") != -1)
        {
            return true;
        }
        
        if (string.indexOf("%0") != -1)
        {
            return true;
        }
        
        if (string.indexOf("%s") != -1)
        {
            return true;
        }
        
        if (string.indexOf("%v") != -1)
        {
            return true;
        }
        
        return false;
    }
}
