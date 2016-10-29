/**
 *  LogicViewer.java
 *  Adventure Game Interpreter Debug Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.debug;

import com.sierra.agi.debug.logic.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.debug.*;
import com.sierra.agi.logic.interpret.*;
import com.sierra.agi.res.ResourceCache;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class LogicViewer extends JFrame implements ActionListener
{
    protected LogicComponent component;

    public LogicViewer(ResourceCache cache, String title, Logic logic)
    {
        super(title);
    
        JScrollPane pane;
        
        component = new LogicComponent(cache, (LogicDebug)logic);
        pane      = new JScrollPane(component);
        pane.setPreferredSize(new Dimension(350, 350));
        
        setMenuBar(generateMenubar());
        
        getContentPane().add(pane, BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
    }
    
    protected MenuBar generateMenubar()
    {
        MenuBar  menubar = new MenuBar();
        Menu     menu;
        MenuItem item;
        
        menu = new Menu("Resource");
        item = new MenuItem("Save as Text File");
        item.addActionListener(this);
        item.setShortcut(new MenuShortcut(KeyEvent.VK_T));
        item.setActionCommand("save");
        menu.add(item);
        menubar.add(menu);
        
        return menubar;
    }
    
    public void actionPerformed(ActionEvent ev)
    {
        String           s     = ev.getActionCommand();
        LogicInterpreter logic = component.getLogic();
        PrintWriter      writer;
        String           file, dir;
        FileDialog       dialog;
        int              i, j;
        
        if (s.equals("save"))
        {
            dialog = new FileDialog(this, "Save Logic to Text File", FileDialog.SAVE);
            dialog.setVisible(true);
            dir  = dialog.getDirectory();
            file = dialog.getFile();
            dialog.dispose();

            if ((dir != null) && (file != null))
            {
                try
                {
                    String[]  m;
                    
                    writer = new PrintWriter(new FileOutputStream(new File(dir, file)));
                
                    for (i = 0; i < component.getLineCount(); i++)
                    {
                        writer.println(component.getLineText(i));
                    }
                    
                    m = logic.getMessages();

                    if (m != null)
                    {
                        writer.println();
                        writer.println();
                        writer.println("Messages:");

                        for (i = 0; i < m.length; i++)
                        {
                            writer.print("Message #" + i + ": ");
                            writer.println(m[i]);
                        }
                    }
                
                    writer.close();
                }
                catch (IOException ioex)
                {
                }
            }
        }
    }
}