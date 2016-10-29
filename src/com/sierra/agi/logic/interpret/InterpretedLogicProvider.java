/*
 *  InterpretedLogicProvider.java
 *  Adventure Game Interpreter Logic Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.logic.interpret;

import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.logic.interpret.instruction.*;
import com.sierra.agi.res.*;

import java.util.*;
import java.io.*;

public class InterpretedLogicProvider extends Object implements LogicProvider
{
    protected LogicReader reader;

    public InterpretedLogicProvider(ResourceConfiguration config)
    {
        reader = new LogicReader(config.engineEmulation);
    }

    public Logic loadLogic(short logicNumber, InputStream inputStream, int size) throws IOException, LogicException
    {
        Vector     instructions = new Vector();
        String[]   messages;

        messages = reader.loadLogic(inputStream, size, instructions);
        
        if (!(instructions.get(instructions.size() - 1) instanceof InstructionReturn))
        {
            instructions.add(new InstructionReturn());
        }
        
        return new LogicInterpreter(logicNumber, instructions, messages);
    }
}
