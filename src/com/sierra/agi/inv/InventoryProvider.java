/**
 *  InventoryProvider.java
 *  Adventure Game Interpreter Inventory Package
 *
 *  Created by Dr. Z
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.inv;

import java.io.InputStream;
import java.io.IOException;

public interface InventoryProvider
{
    public InventoryObjects loadInventory(InputStream in) throws IOException;
}
