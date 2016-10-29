/**
 *  ContextDebugger.java
 *  Adventure Game Interpreter Debug Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.debug;

import com.sierra.agi.logic.*;
import com.sierra.agi.logic.debug.*;
import com.sierra.agi.debug.logic.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;

public class ContextDebugger extends JFrame implements LogicContextListener, ActionListener, ItemListener, TreeSelectionListener
{
    protected JComboBox         stackCombo;
    protected boolean           stackChanging;
    protected JTable            watchTable;
    
    protected LogicContextDebug logicContext;
    protected LogicComponent    logicComponent;
    
    protected DefaultTableModel variableModel;

    public ContextDebugger(LogicContextDebug logicContext)
    {
        super("Adventure Game Debugger");
        
        logicComponent = new LogicComponent();
        variableModel  = new DefaultTableModel(new Object[256][2], new String[] {"Variable","Value"});
        
        JSplitPane  bottomPane = new JSplitPane();
        JSplitPane  pane       = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JScrollPane scroll     = new JScrollPane(logicComponent);
        
        scroll.setPreferredSize(new Dimension(600, 100));
        
        bottomPane.add(getStack(),   JSplitPane.LEFT);
        bottomPane.add(getWatches(), JSplitPane.RIGHT);
        
        pane.add(scroll,     JSplitPane.TOP);
        pane.add(bottomPane, JSplitPane.BOTTOM);
        
        getContentPane().add(pane);
        
        setMenuBar(addMenu());
        pack();
        
        this.logicContext = logicContext;
        this.logicContext.addLogicContextListener(this);
    }
    
    protected Component getStack()
    {
        JTree       tree      = generateTree();
        Container   container = new JPanel();
        JScrollPane pane      = new JScrollPane(tree);
        
        stackCombo = new JComboBox();
        stackCombo.setEditable(false);
        stackCombo.addItemListener(this);
        
        pane.setPreferredSize(new Dimension(300, 100));
        
        container.setLayout(new BorderLayout());
        container.add(stackCombo,  BorderLayout.NORTH);
        container.add(pane,        BorderLayout.CENTER);
        
        stackChanging = true;
        stackCombo.removeAllItems();
        stackCombo.addItem("<Not Running>");
        stackChanging = false;
        
        tree.addTreeSelectionListener(this);        
        return container;
    }
    
    protected Component getWatches()
    {
        JScrollPane pane;
        
        watchTable = new JTable();
        pane       = new JScrollPane(watchTable);
        
        pane.setPreferredSize(new Dimension(300, 100));
        return pane;
    }

    protected MenuBar addMenu()
    {
        MenuBar  menubar = new MenuBar();
        Menu     menu;
        MenuItem item;
        
        menu = new Menu("Debug");
        item = new MenuItem("Continue");
        item.setActionCommand("run");
        item.addActionListener(this);
        item.setShortcut(new MenuShortcut(KeyEvent.VK_R, true));
        menu.add(item);

        item = new MenuItem("Pause");
        item.setActionCommand("pause");
        item.addActionListener(this);
        item.setShortcut(new MenuShortcut(KeyEvent.VK_P, true));
        menu.add(item);
        menu.addSeparator();

        item = new MenuItem("Step Out");
        item.setActionCommand("stepout");
        item.addActionListener(this);
        item.setShortcut(new MenuShortcut(KeyEvent.VK_O, true));
        menu.add(item);

        item = new MenuItem("Step Into");
        item.setActionCommand("stepinto");
        item.addActionListener(this);
        item.setShortcut(new MenuShortcut(KeyEvent.VK_I, true));
        menu.add(item);

        item = new MenuItem("Step Over");
        item.setActionCommand("stepover");
        item.addActionListener(this);
        item.setShortcut(new MenuShortcut(KeyEvent.VK_T, true));
        menu.add(item);
        menubar.add(menu);
        
        return menubar;
    }

    public void logicBreakpointReached(LogicContextEvent ev)
    {
        Object[] stack = logicContext.getLogicStack();
        int      i;
    
        stackChanging = true;
        stackCombo.removeAllItems();
        for (i = 0; i < stack.length; i++)
        {
            stackCombo.addItem(stack[i]);
        }
        
        stackCombo.setSelectedItem(stack[i-1]);
        
        LogicStackEntry entry = (LogicStackEntry)stack[i-1];
                
        logicComponent.setLogic(entry.logic);
        logicComponent.setInstructionNumber(entry.in);
        stackChanging = false;
    }

    public void logicResumed(LogicContextEvent ev)
    {
        stackChanging = true;
        stackCombo.removeAllItems();
        stackCombo.addItem("<Running>");
        stackChanging = false;
    }
    
    public void itemStateChanged(ItemEvent ev)
    {
        if (!stackChanging)
        {
            if (ev.getStateChange() == ItemEvent.SELECTED)
            {
                Object o = ev.getItem();
            
                if (o instanceof LogicStackEntry)
                {
                    LogicStackEntry entry = (LogicStackEntry)o;
                
                    logicComponent.setLogic(entry.logic);
                    logicComponent.setInstructionNumber(entry.in);
                }
            }
        }
    }
    
    public void actionPerformed(ActionEvent ev)
    {
        String s = ev.getActionCommand();
        
        if (s.equals("run"))
        {
            logicContext.resumeExecution();
        }
        else if (s.equals("pause"))
        {
            logicContext.breakExecution();
        }
        else if (s.equals("stepinto"))
        {
            logicContext.stepIntoExecution();
        }
        else if (s.equals("stepout"))
        {
            logicContext.stepOutExecution();
        }
        else if (s.equals("stepover"))
        {
            logicContext.stepOverExecution();
        }
    }

    protected JTree generateTree()
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Environment", true);
        DefaultMutableTreeNode node;
        JTree                  tree;

        node = new DefaultMutableTreeNode("Variables", false);
        root.add(node);
        
        node = new DefaultMutableTreeNode("Flags", false);
        root.add(node);

        node = new DefaultMutableTreeNode("Inventory Objects", false);
        root.add(node);

        node = new DefaultMutableTreeNode("Logic Entry Points", false);
        root.add(node);

        //addMouseListener(this);

        tree = new JTree(root, true);
        //tree.addMouseListener(this);
     
        return tree;
    }

    public void valueChanged(TreeSelectionEvent ev)
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)ev.getPath().getLastPathComponent();
        String                 name = node.toString();
        
        if (name.equals("Variables"))
        {
            watchTable.setModel(variableModel);
        }
    }
}
