
/*
 *  AGI.java
 *  Adventure Game Debugger 
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi;

import com.sierra.agi.debug.*;
import com.sierra.agi.res.*;
import com.sierra.agi.logic.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class AGId extends Object
{
    public AGId(String[] args) throws Exception
    {
        File resFile;
    
        if (args.length == 0)
        {
            resFile = obtainResourceFile();
        }
        else
        {
            resFile = new File(args[0]);
        }
    
        ResourceCache resCache = new ResourceCacheFileDebug(resFile);
        ResourceFrame frame    = new ResourceFrame(resCache);

        try
        {
            Class.forName("com.sierra.agi.apple.AppleHandler");
        }
        catch (Throwable thr)
        {
        }

        frame.setVisible(true);
    }

    public static File obtainResourceFile()
    {
        FileDialog dialog = new FileDialog(new Frame(), "Open a Game's Resources", FileDialog.LOAD);
        String     file, dir;
        
        dialog.setVisible(true);
        dir  = dialog.getDirectory();
        file = dialog.getFile();
        dialog.dispose();

        if ((dir != null) && (file != null))
        {
            return new File(dir, file);
        }
        
        System.exit(-1);
        return null;
    }

    public static void main(String[] args) throws LogicException
    {
        System.setProperty("com.sierra.agi.logic.LogicProvider", "com.sierra.agi.logic.debug.DebugLogicProvider");
    
        new com.sierra.agi.view.MessageBox("OK.");
        new com.sierra.agi.view.MessageBox("    Center    \r\n12341234561234");
    
        Compiler.enable();
        Compiler.compileClass(com.sierra.agi.logic.LogicContext.class);
        Compiler.compileClass(com.sierra.agi.view.ViewTable.class);
    
        try
        {
            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
        }
        
        try
        {
            new AGId(args);
        }
        catch (Throwable thr)
        {
            ExceptionDialog.showException(thr);
        }
    }
}