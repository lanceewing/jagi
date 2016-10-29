/**
 *  LogicProvider.java
 *  Adventure Game Interpreter Logic Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.logic;

import java.io.*;

public interface LogicProvider
{
    public Logic loadLogic(short logicNumber, InputStream inputStream, int size) throws IOException, LogicException;
}
