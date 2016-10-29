/*
 *  AgiMenuBar.java
 *  Adventure Game Interpreter Menu Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */
 
package com.sierra.agi.menu;

import com.sierra.agi.awt.*;
import com.sierra.agi.view.*;
import java.awt.*;
import java.util.*;

public class AgiMenuBar extends Object
{
    protected Vector menus;

    public AgiMenuBar()
    {
        menus = new Vector();
    }
    
    public void addMenu(String name)
    {
        menus.add(new AgiMenu(name));
    }
    
    public void addMenuItem(String name, short controller)
    {
        ((AgiMenu)menus.lastElement()).add(new AgiMenuItem(name, controller));
    }
    
    public void enableMenuItem(boolean enable, short controller)
    {
        int menu;
        
        for (menu = 0; menu < menus.size(); menu++)
        {
            ((AgiMenu)menus.get(menu)).enableMenuItem(enable, controller);
        }
    }
    
    public boolean isEnabled(int menu, int item)
    {
        try
        {
            return ((AgiMenu)menus.get(menu)).isEnabled(item);
        }
        catch (Exception ex)
        {
            return false;
        }
    }
    
    public int getController(int menu, int item)
    {
        try
        {
            return ((AgiMenu)menus.get(menu)).getController(item);
        }
        catch (Exception ex)
        {
            return -1;
        }
    }
    
    public int getMenuCount()
    {
        return menus.size();
    }
    
    public int getItemCount(int menu)
    {
        return ((AgiMenu)menus.get(menu)).getItemCount();
    }
    
    public void drawMenuBar(ViewScreen viewScreen, int textColor, int backgroundColor, int selectedMenu)
    {
        int     i, j, c, l, x;
        String  name;
        int[]   screen = viewScreen.getScreenData();
        int[]   font   = viewScreen.getFont();
        
        Arrays.fill(screen, 0, ViewScreen.WIDTH * ViewScreen.CHAR_HEIGHT, backgroundColor);
        
        x = 0;
        c = menus.size();

        if (selectedMenu >= c)
        {
            selectedMenu %= c;
        }

        for (i = 0; i < c; i++)
        {
            name = menus.get(i).toString();
            l    = name.length();
            x   += ViewScreen.CHAR_WIDTH;
            
            if (selectedMenu == i)
            {
                for (j = 0; j < l; j++)
                {
                    EgaUtils.putCharacter(screen, font, name.charAt(j), x, 0, ViewScreen.WIDTH, backgroundColor, textColor, true);
                    x += ViewScreen.CHAR_WIDTH;
                }
            }
            else
            {
                for (j = 0; j < l; j++)
                {
                    EgaUtils.putCharacter(screen, font, name.charAt(j), x, 0, ViewScreen.WIDTH, textColor, 0, false);
                    x += ViewScreen.CHAR_WIDTH;
                }
            }
        }
        
        viewScreen.putBlock(0, 0, ViewScreen.WIDTH, ViewScreen.CHAR_HEIGHT);
    }

    public void drawMenu(ViewScreen viewScreen, int textColor, int disabledColor, int backgroundColor, int selectedMenu, int selectedItem, Rectangle changedRectangle)
    {
        AgiMenu menu;
        int     i, c, x;

        if (selectedMenu < 0)
        {
            if (changedRectangle != null)
            {
                changedRectangle.x      = 0;
                changedRectangle.y      = 0;
                changedRectangle.width  = 0;
                changedRectangle.height = 0;
            }
            return;
        }
        
        x             = 0;
        c             = menus.size();
        selectedMenu %= c;

        for (i = 0; i < c; i++)
        {
            x += ViewScreen.CHAR_WIDTH;
            
            if (selectedMenu == i)
            {
                break;
            }
            else
            {
                x += menus.get(i).toString().length() * ViewScreen.CHAR_WIDTH;
            }
        }
        
        ((AgiMenu)menus.get(selectedMenu)).drawMenu(viewScreen, textColor, disabledColor, backgroundColor, selectedItem, x, changedRectangle);
    }
}
