/*
 * LogicReader.java
 */

package com.sierra.agi.logic.interpret;

import com.sierra.agi.*;
import com.sierra.agi.logic.*;
import com.sierra.agi.logic.interpret.expression.*;
import com.sierra.agi.logic.interpret.instruction.*;
import com.sierra.agi.logic.interpret.jit.Compilable;
import com.sierra.agi.io.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public class LogicReader extends Object
{
    /** Instruction Table */
    protected Properties instructionTable;
    
    /** Expression Table */
    protected Properties expressionTable;
    
    /** Logic Messages */
    protected String[] messages;
    
    /** Instructions */
    protected Vector instructions = new Vector(30, 10);
    
    protected short engineEmulation;
    
    protected boolean compilable = true;
    
    /** Creates new Logic Reader */
    public LogicReader(short engineEmulation)
    {
        instructionTable     = new Properties();
        expressionTable      = new Properties();
        this.engineEmulation = engineEmulation;
        
        String versionStr = String.format("%04X", engineEmulation);
        
        try
        {
            // Use the engineEmulation value (i.e. the interpreter version) to see if 
            // there is a version specific instruction mapping conf file.
            InputStream is = null;
            for (int i=versionStr.length(); i >= 0 && is == null; i--) {
                String suffix = versionStr.substring(0, i);
                suffix = ((suffix.length() > 0? "_" : "") + suffix);
                is = getClass().getResourceAsStream("instruction" + suffix + ".conf");
            }
            instructionTable.load(is);
        }
        catch (IOException ioex)
        {
        }

        try
        {
            // Use the engineEmulation value (i.e. the interpreter version) to see if 
            // there is a version specific expression mapping conf file.
            InputStream is = null;
            for (int i=versionStr.length(); i >= 0 && is == null; i--) {
                String suffix = versionStr.substring(0, i);
                suffix = ((suffix.length() > 0? "_" : "") + suffix);
                is = getClass().getResourceAsStream("expression" + suffix + ".conf");
            }
            expressionTable.load(is);
        }
        catch (IOException ioex)
        {
        }
    }

    public boolean isCompilable()
    {
        return compilable;
    }

    public String[] loadLogic(InputStream stream, int size, Vector instructions) throws IOException, LogicException
    {
        byte b[] = new byte[size];
        
        IOUtils.fill(stream, b, 0, size);
        
        try
        {
            readInstructions(instructions, new ByteArrayInputStream(b, 2, ByteCaster.lohiUnsignedShort(b, 0)));
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
        
        return readMessages(b);
    }
    
    /** Load Instruction Parameters Definitions */
    protected Class loadInstructionTypes[] = {InputStream.class, LogicReader.class, short.class, short.class};

    public Expression readExpression(short bytecode, InputStream stream) throws LogicException
    {
        Expression  expression;
        String      expressionByte;
        String      expressionClass;
        Class       clazz;
        Constructor cons;
        Object      o[];
        
        expressionByte  = Integer.toHexString((int)bytecode);
        expressionClass = expressionTable.getProperty(expressionByte);

        System.out.println("  expressionByte: " + expressionByte + ", expressionClass: " + expressionClass);
        
        if (expressionClass == null)
        {
            throw new UnknownExpressionException(expressionByte);
        }

        if (expressionClass.indexOf(".") < 0)
        {
            expressionClass = "com.sierra.agi.logic.interpret.expression." + expressionClass;
        }

        try
        {
            clazz = Class.forName(expressionClass);
            cons  = clazz.getConstructor(loadInstructionTypes);

            o    = new Object[4];
            o[0] = stream;
            o[1] = this;
            o[2] = new Short((short)bytecode);
            o[3] = new Short(engineEmulation);

            expression = (Expression)cons.newInstance(o);
            return expression;
        }
        catch (ClassNotFoundException ex)
        {
            throw new UnknownExpressionException();
        }
        catch (InvocationTargetException ex)
        {
            if (ex.getTargetException() instanceof LogicException)
            {
                throw (LogicException)ex.getTargetException();
            }

            throw new InternalLogicException(null, expressionByte, ex.getTargetException());
        }
        catch (Exception ex)
        {
            throw new InternalLogicException(null, expressionByte, ex);
        }
    }

    public Expression readExpression(InputStream stream) throws IOException, LogicException
    {
        int bytecode = stream.read();

        if (bytecode < 0)
        {
            return null;
        }

        return readExpression((short)bytecode, stream);
    }
    
    public Instruction readInstruction(short bytecode, InputStream stream) throws LogicException
    {
        Instruction    instruct;
        String         instructionByte;
        String         instructionClass;
        Class          clazz;
        Constructor    cons;
        Object         o[];

        instructionByte  = Integer.toHexString((int)bytecode);
        instructionClass = instructionTable.getProperty(instructionByte);
        
        System.out.println("  instructionByte: " + instructionByte + ", instructionClass: " + instructionClass);

        if (instructionClass == null)
        {
            throw new UnknownInstructionException(instructionByte);
        }

        if (instructionClass.indexOf(".") < 0)
        {
            instructionClass = "com.sierra.agi.logic.interpret.instruction." + instructionClass;
        }

        try
        {
            clazz = Class.forName(instructionClass);
            cons  = clazz.getConstructor(loadInstructionTypes);

            o    = new Object[4];
            o[0] = stream;
            o[1] = this;
            o[2] = new Short((short)bytecode);
            o[3] = new Short(engineEmulation);

            instruct = (Instruction)cons.newInstance(o);
            return instruct;
        }
        catch (ClassNotFoundException ex)
        {
            throw new UnknownInstructionException(instructionClass + " (" + instructionByte + ")");
        }
        catch (InvocationTargetException ex)
        {
            if (ex.getTargetException() instanceof LogicException)
            {
                throw (LogicException)ex.getTargetException();
            }

            throw new InternalLogicException(null, instructionByte, ex.getTargetException());
        }
        catch (Exception ex)
        {
            throw new InternalLogicException(null, instructionByte, ex);
        }
    }
    
    public Instruction readInstruction(InputStream stream) throws IOException, LogicException
    {
        int bytecode = stream.read();

        if (bytecode < 0)
        {
            return null;
        }

        return readInstruction((short)bytecode, stream);
    }
    
    public void readInstructions(Vector instructions, InputStream stream) throws IOException, LogicException
    {
        Instruction instruct;
        int byteCount = 0;
        
        try
        {
            while (true)
            {
                System.out.print(String.format("Offset: %04X, Ins#: %02d -", byteCount, instructions.size() + 1));
                instruct = readInstruction(stream);
                
                if (instruct == null)
                {
                    break;
                }
                
                if (!(instruct instanceof Compilable))
                {
                    /*System.out.print(instruct.getClass().getName());
                    System.out.println(" can't be compiled");*/
                    compilable = false;
                }
                
                instructions.add(instruct);
                
                System.out.println("   " + instruct.toString());
                
                byteCount += instruct.getSize();
            }
        }
        catch (EOFException ex)
        {
            ex.printStackTrace();
        }
    }
    
    protected String[] readMessages(byte[] b)
    {
        int      startPos;
        int      numMessages;
        int      fileData, marker;
        int      i, j, k, l;
        String[] m;
    
        startPos    = ByteCaster.lohiUnsignedShort(b, 0) + 2;
        numMessages = ByteCaster.lohiUnsignedByte(b, startPos);
        fileData    = startPos + 3;
        
        m      = new String[numMessages + 1];
        marker = fileData;
        
        for (i = 1; i <= numMessages; i++, marker += 2)
        {
            j = ByteCaster.lohiUnsignedShort(b, marker);
            
            if (j == 0)
            {
                continue;
            }
            
            j -= 2;
            j += fileData;
            l  = j;
            
            while (b[l] != 0)
            {
                l++;
            }
            
            try
            {
                m[i] = new String(b, j, l - j, "US-ASCII");
            }
            catch (UnsupportedEncodingException ex)
            {
            }
        }
        
        return m;
    }
    
    public String[] getMessages()
    {
        return messages;
    }
    
    public Vector getInstructions()
    {
        return instructions;
    }
}