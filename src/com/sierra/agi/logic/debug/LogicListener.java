/**
 *  LogicListener.java
 *  Adventure Game Interface Logic Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.logic.debug;

public interface LogicListener
{
    public void logicBreakpointAdded(LogicEvent ev);
    public void logicBreakpointRemoved(LogicEvent ev);
    public void logicBreakpointEnabled(LogicEvent ev);
    public void logicBreakpointDisabled(LogicEvent ev);
}
