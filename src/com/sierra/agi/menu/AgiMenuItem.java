/*
 *  AgiMenuItem.java
 *  Adventure Game Intrepreter Menu Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.menu;

public class AgiMenuItem extends Object
{
    protected String  name;
    protected short   controller;
    protected boolean enabled = true;
    
    public AgiMenuItem(String name, short controller)
    {
        this.name       = name;
        this.controller = controller;
    }
    
    public String toString()
    {
        return name;
    }
    
    public boolean isEnabled()
    {
        return enabled;
    }
    
    public void setEnable(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    public short getController()
    {
        return controller;
    }
}
