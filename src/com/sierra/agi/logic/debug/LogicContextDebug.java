/**
 *  LogicContextDebug.java
 *  Adventure Game Interpreter Logic Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.logic.debug;

import com.sierra.agi.logic.*;
import com.sierra.agi.res.*;
import java.util.*;

public final class LogicContextDebug extends LogicContext
{
    protected boolean breaked   = false;
    protected Vector  listeners = new Vector();

    public LogicContextDebug(ResourceCache cache)
    {
        super(cache);
    }
    
    public void breakpointReached()
    {
        Enumeration       en  = listeners.elements();
        LogicContextEvent event = new LogicContextEvent(this);
    
        breaked = true;
        
        while (en.hasMoreElements())
        {
            ((LogicContextListener)en.nextElement()).logicBreakpointReached(event);
        }
    }
    
    protected synchronized boolean ensureExecution()
    {
        if (!isRunning())
        {
            Thread thread;
                
            thread = new Thread(this);
            thread.start();

            Enumeration       en  = listeners.elements();
            LogicContextEvent event = new LogicContextEvent(this);
    
            while (en.hasMoreElements())
            {
                ((LogicContextListener)en.nextElement()).logicResumed(event);
            }
            
            return true;
        }
        
        return false;
    }
    
    public void breakExecution()
    {
        ensureExecution();
        breaked = true;
    }
    
    public boolean isBreaked()
    {
        return breaked;
    }
    
    public void resumeExecution()
    {
        breaked = false;

        if (!ensureExecution())
        {
            Enumeration       en  = listeners.elements();
            LogicContextEvent event = new LogicContextEvent(this);
            
            ((LogicStackEntry)peekLogic()).command = LogicStackEntry.RUNNING;
    
            while (en.hasMoreElements())
            {
                ((LogicContextListener)en.nextElement()).logicResumed(event);
            }
        }
    }

    public void stepIntoExecution()
    {
        ensureExecution();
        breaked = false;
        
        try
        {
            ((LogicStackEntry)peekLogic()).command = LogicStackEntry.STEP_INTO;
        }
        catch (EmptyStackException esex)
        {
        }
    }

    public void stepOverExecution()
    {
        ensureExecution();
        breaked = false;
        
        try
        {
            ((LogicStackEntry)peekLogic()).command = LogicStackEntry.STEP_OVER;
        }
        catch (EmptyStackException esex)
        {
        }
    }

    public void stepOutExecution()
    {
        ensureExecution();
        breaked = false;
        
        try
        {
            ((LogicStackEntry)peekLogic()).command = LogicStackEntry.STEP_OUT;
        }
        catch (EmptyStackException esex)
        {
        }
    }
    
    public void addLogicContextListener(LogicContextListener listener)
    {
        listeners.add(listener);
    }
    
    public void removeLogicContextListener(LogicContextListener listener)
    {
        listeners.remove(listener);
    }
}
