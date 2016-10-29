/*
 *  ViewProvider.java
 *  Adventure Game Interpreter View Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.view;

import java.io.*;
import com.sierra.agi.view.*;

public interface ViewProvider
{
    public View loadView(InputStream inputStream, int size) throws IOException, ViewException;
}
