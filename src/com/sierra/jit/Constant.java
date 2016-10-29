
package com.sierra.jit;

import java.io.*;

public abstract class Constant extends Object
{
    public abstract void compile(DataOutputStream outs) throws IOException;
}
