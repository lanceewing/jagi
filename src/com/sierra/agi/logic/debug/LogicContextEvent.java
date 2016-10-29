/**
 *  LogicContextEvent.java
 *  Adventure Game Interpreter Logic Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.logic.debug;

public class LogicContextEvent extends java.util.EventObject
{
    public LogicContextEvent(LogicContextDebug source)
    {
        super(source);
    }
}
