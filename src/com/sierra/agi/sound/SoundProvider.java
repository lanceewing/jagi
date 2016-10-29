/*
 *  SoundProvider.java
 *  Adventure Game Interpreter Sound Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.sound;

import java.io.*;

public interface SoundProvider
{
    public Sound loadSound(InputStream inputStream) throws IOException;
}
