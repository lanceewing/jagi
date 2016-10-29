/*
 *  WordsProvider.java
 *  Adventure Game Interpreter Word Package
 *
 *  Created by Dr. Z
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.word;

import java.io.InputStream;
import java.io.IOException;

public interface WordsProvider
{
    public Words loadWords(InputStream in) throws IOException;
}
