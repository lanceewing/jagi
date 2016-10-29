/*
 * InventoryObject.java
 */

package com.sierra.agi.inv;

/**
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public final class InventoryObject extends Object
{
    /** Location */
    protected short location;
    
    /** Name */
    public String name;

    public InventoryObject(short location)
    {
        this.location = location;
    }
    
    public String getName()
    {
        return name;
    }
    
    public short getLocation()
    {
        return location;
    }
}