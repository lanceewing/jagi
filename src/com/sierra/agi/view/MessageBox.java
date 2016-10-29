/*
 *  Box.java
 *  Adventure Game Interpreter View Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2002 Dr. Z. All rights reserved.
 */

package com.sierra.agi.view;

import com.sierra.agi.awt.EgaComponent;
import com.sierra.agi.awt.EgaUtils;
import com.sierra.agi.logic.LogicContext;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.*;

public class MessageBox extends Box
{
    protected String[] lines;
    protected int      x = -1;
    protected int      y = -1;
    protected int      column;

    public MessageBox(String content)
    {
        init(content, 30);
        trim();
    }
    
    public MessageBox(String content, int x, int y, int column)
    {
        init(content, column);
        trim();
        this.x = x;
        this.y = y;
    }
    
    public MessageBox(String content, int column)
    {
        init(content, column);
    }
    
    public void draw(ViewScreen viewScreen)
    {
        int x           = this.x;
        int y           = this.y;
        int width       = getWidth();
        int height      = getHeight();
        int textColor   = viewScreen.translatePixel(Color.black);
        int backColor   = viewScreen.translatePixel(Color.white);
        int borderColor = viewScreen.translatePixel(Color.red.darker());
        int[]  screen   = viewScreen.getScreenData();
        int[]  font     = viewScreen.getFont();
        String text;
        int line, oy;
        int textEnd, textLength, index, end;
    
        if (x < 0)
        {
            x = (ViewScreen.WIDTH - width) / 2;
        }
        
        if (y < 0)
        {
            y = (ViewScreen.HEIGHT - height) / 2;
        }

        oy  = y;
        end = x + width - ViewScreen.CHAR_WIDTH;
        
        viewScreen.drawTopLine(borderColor, backColor, x, y, width);
        y += ViewScreen.CHAR_HEIGHT;
        
        for (line = 0; line < lines.length; line++)
        {
            text       = lines[line];
            textLength = text.length();
            textEnd    = x + ((textLength + 1) * ViewScreen.CHAR_WIDTH);
        
            viewScreen.drawLeftLine (borderColor, backColor, x, y);
            
            EgaUtils.putString(screen, font, text, x + ViewScreen.CHAR_WIDTH, y, ViewScreen.WIDTH, textColor, backColor, true);
           
            if (end != textEnd)
            {
                viewScreen.drawBlanks(backColor, textEnd, y, end - textEnd);
            }
            
            viewScreen.drawRightLine(borderColor, backColor, end, y);
            y += ViewScreen.CHAR_HEIGHT;
        }
        
        viewScreen.drawBottomLine(borderColor, backColor, x, y, width);
        viewScreen.putBlock(x, oy, width, height);
    }
    
    public KeyEvent show(LogicContext logicContext, ViewScreen viewScreen, boolean modal)
    {
        KeyEvent ev      = null;
        int      timeout = this.timeout;
        
        if (logicContext != null)
        {
            logicContext.stopClock();
            
            if (timeout == -1)
            {
                timeout = logicContext.getVar(LogicContext.VAR_WINDOW_RESET) * 500;
            
                if (timeout == 0)
                {
                    timeout = -1;
                }
            }
        }
        
        if (modal)
        {
            viewScreen.save();
        }
    
        draw(viewScreen);
        
        if (modal)
        {
            EgaComponent ega     = viewScreen.getComponent();
            boolean      looping = true;

            ega.clearEvents();
            
            do
            {
                if ((ev = ega.popCharEvent(timeout)) == null)
                {
                    break;
                }
                
                switch (ev.getKeyCode())
                {
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_ESCAPE:
                    looping = false;
                    break;
                }
            } while (looping);
            
            viewScreen.restore(true);
        }

        if (logicContext != null)
        {
            logicContext.startClock();
        }
        
        return ev;
    }
    
    protected void init(String content, int maxColumn)
    {
        Vector          lines = new Vector();
        StringBuffer    current;
        String          token;
        StringTokenizer tokenizer;
        String          word;
        StringTokenizer words;
        
        tokenizer = new StringTokenizer(content, "\r\n", false);
        
        while (tokenizer.hasMoreTokens())
        {
            current = new StringBuffer();
            token   = tokenizer.nextToken();
            words   = new StringTokenizer(token, " ", true);
            
            while (words.hasMoreTokens())
            {
                word = words.nextToken();
                
                if ((current.length() + word.length()) > maxColumn)
                {
                    lines.add(current.toString());
                    current = new StringBuffer();
                }

                if (word.equals(" ") && (current.length() == 0))
                {
                    continue;
                }
                
                current.append(word);
            }
            
            lines.add(current.toString());
        }
        
        lines.toArray(this.lines = new String[lines.size()]);
    }
    
    protected void trim()
    {
        int    index;
        int    length;
        String line;
        
        for (index = 0; index < lines.length; index++)
        {
            line   = lines[index];
            length = line.length() - 1;
            
            while (length >= 0)
            {
                if (line.charAt(length) == ' ')
                {
                    length--;
                }
                else
                {
                    break;
                }
            }
            
            lines[index] = line = line.substring(0, length + 1);
            
            if (column < line.length())
            {
                column = line.length();
            }
        }
    }
    
    public int getLineCount()
    {
        return lines.length;
    }
    
    public int getColumnCount()
    {
        return column;
    }
    
    public int getWidth()
    {
        return ViewScreen.CHAR_WIDTH * (column + 2);
    }

    public int getHeight()
    {
        return ViewScreen.CHAR_HEIGHT * (lines.length + 2);
    }
}
