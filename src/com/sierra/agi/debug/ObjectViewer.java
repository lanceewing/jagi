/**
 *  ObjectViewer.java
 *  Adventure Game Interpreter Debugger Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.debug;

import com.sierra.agi.inv.*;
import javax.swing.*;
import javax.swing.table.*;

public class ObjectViewer extends JFrame
{
    protected InventoryObjects objects;

    public static class ObjectTableModel extends DefaultTableModel
    {
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return false;
        }
        
        public void setValueAt(Object aValue, int rowIndex, int columnIndex)
        {
        }
    }

    public ObjectViewer(InventoryObjects objects)
    {
        super("Objects");
        this.objects = objects;

        DefaultTableModel model = new ObjectTableModel();
        Object[]          data  = new Object[2];
        short             i, c;
        InventoryObject   o;
        
        model.addColumn("Object");
        model.addColumn("Location");
        
        c = objects.getCount();
        
        for (i = 0; i < c; i++)
        {
            o = objects.getObject(i);
            data[0] = o.getName();
            data[1] = new Short(o.getLocation());
            model.addRow(data);
        }
        
        getContentPane().add(new JScrollPane(new JTable(model)));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
    }
}
