/**
 *  Instruction.java
 *  Just-in-Time Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.jit.code;

import java.util.*;
import java.io.*;

public abstract class Instruction extends Object
{
    public abstract void compile(CompileContext context, Scope scope, DataOutputStream outs, int pc) throws IOException;
    public abstract int  getSize(CompileContext context, Scope scope, int pc);
    public abstract int  getPopCount();
    public abstract int  getPushCount();
}
