/*
 *  Box.java
 *  Adventure Game Interpreter View Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2002 Dr. Z. All rights reserved.
 */

package com.sierra.agi.view;

import java.awt.event.KeyEvent;
import java.util.*;
import com.sierra.agi.logic.LogicContext;

public abstract class Box extends Object
{
    protected int timeout;

    public Box()
    {
        timeout = -1;
    }

    public abstract KeyEvent show(LogicContext logicContext, ViewScreen viewScreen, boolean modal);

    public abstract int getLineCount();
    public abstract int getColumnCount();
    public abstract int getWidth();
    public abstract int getHeight();
    
    public int getTimeout()
    {
        return timeout;
    }
    
    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }
}
