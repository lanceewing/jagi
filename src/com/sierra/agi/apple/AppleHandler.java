/*
 *  AppleHandler.java
 *  Adventure Game Interface Apple Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.apple;

import com.apple.mrj.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class AppleHandler implements MRJAboutHandler, MRJQuitHandler
{
    protected static JFrame aboutFrame;

    static
    {
        if (MRJApplicationUtils.isMRJToolkitAvailable())
        {
            AppleHandler handler = new AppleHandler();
            
            MRJApplicationUtils.registerAboutHandler(handler);
            MRJApplicationUtils.registerQuitHandler(handler);
        }
    }

    public void handleAbout()
    {
        if (aboutFrame == null)
        {
            JTextArea      area;
            StringWriter   writer = new StringWriter();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("AppleAbout.txt")));
            String         line;
        
            aboutFrame = new JFrame("About Adventure Game Interpreter and Debugger");
            
            try
            {
                while ((line = reader.readLine()) != null)
                {
                    writer.write(line);
                    writer.write("\n");
                }
            }
            catch (IOException ioex)
            {
            }
            
            area = new JTextArea(writer.toString(), 15, 80);
            area.setFont(new Font("Monospaced", Font.PLAIN, 12));
            area.setEditable(false);
            area.setSelectionStart(0);
            area.setSelectionEnd(0);
            
            aboutFrame.getContentPane().add(new JScrollPane(area));
            aboutFrame.pack();
        }
        
        aboutFrame.show();
    }
    
    public void handleQuit()
    {
        if (JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to quit?",
                    "Are you sure?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION)
        {
            throw new IllegalStateException();
        }
    }
}
