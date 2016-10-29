/**
 *  LogicContextListener.java
 *  Adventure Game Interpreter Logic Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.logic.debug;

public interface LogicContextListener
{
    public void logicBreakpointReached(LogicContextEvent ev);
    public void logicResumed(LogicContextEvent ev);
}
