
package com.sierra.jit;

import com.sierra.jit.code.*;
import java.io.*;
import java.util.*;

public class ClassCompiler extends Object
{
    protected ConstantPool constants;
    protected int          thisClass;
    protected String       thisClassName;
    protected int          superClass;
    protected short        flags;
    protected Vector       interfaces;
    protected Vector       fields;
    protected Vector       methods;
    protected Hashtable    attributes;

    protected static final short ACC_PUBLIC    = 0x0001;
    protected static final short ACC_FINAL     = 0x0010;
    protected static final short ACC_SUPER     = 0x0020;
    protected static final short ACC_INTERFACE = 0x0200;
    protected static final short ACC_ABSTRACT  = 0x0400;

    public ClassCompiler(String className, String superName)
    {
        constants     = new ConstantPool();
        thisClass     = constants.getClass(className);
        thisClassName = className;
        superClass    = constants.getClass(superName);
        flags         = ACC_PUBLIC | ACC_SUPER;
        interfaces    = new Vector();
        fields        = new Vector();
        methods       = new Vector();
        attributes    = new Hashtable();
    }

    public String getClassName()
    {
        return thisClassName;
    }

    public ConstantPool getConstantPool()
    {
        return constants;
    }

    public Class compile()
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        
        try
        {
            compile(out);
        }
        catch (IOException ioex)
        {
        }
        
        return getClassLoader().inTimeDefineClass(thisClassName, out.toByteArray());
    }
    
    protected static ClassCompilerInTimerLoader classLoader;
    
    protected static synchronized ClassCompilerInTimerLoader getClassLoader()
    {
        if (classLoader == null)
        {
            classLoader = new ClassCompilerInTimerLoader();
        }
        
        return classLoader;
    }

    protected static class ClassCompilerInTimerLoader extends ClassLoader
    {
        public ClassCompilerInTimerLoader()
        {
        }
        
        public Class inTimeDefineClass(String name, byte[] data)
        {
            return defineClass(name, data, 0, data.length);
        }
    }

    public void compile(OutputStream out) throws IOException
    {
        DataOutputStream outs;
        Enumeration      enum;
        
        if (out instanceof DataOutputStream)
        {
            outs = (DataOutputStream)out;
        }
        else
        {
            outs = new DataOutputStream(out);
        }

        outs.writeInt(0xCAFEBABE); // Signature
        outs.writeShort(3);        // Minor Version 3
        outs.writeShort(45);       // Major Version 45
        constants.compile(outs);   // Constant Pool
        outs.writeShort(flags);    // Flags
        outs.writeShort((short)thisClass);  // this
        outs.writeShort((short)superClass); // super

        outs.writeShort(interfaces.size()); // Interface Count
        enum = interfaces.elements();

        while (enum.hasMoreElements())
        {
            outs.writeShort(((Integer)enum.nextElement()).shortValue());
        }

        outs.writeShort(fields.size()); // Field Count
        enum = fields.elements();

        while (enum.hasMoreElements())
        {
            ((Field)enum.nextElement()).compile(outs);
        }

        outs.writeShort(methods.size()); // Method Count
        enum = methods.elements();

        while (enum.hasMoreElements())
        {
            ((Method)enum.nextElement()).compile(outs);
        }

        outs.writeShort(attributes.size()); // Attribute Count
        enum = attributes.elements();

        while (enum.hasMoreElements())
        {
            ((Attribute)enum.nextElement()).compile(outs);
        }
    }

    public boolean isPublic()
    {
        return (flags & ACC_PUBLIC) != 0;
    }

    public boolean isFinal()
    {
        return (flags & ACC_FINAL) != 0;
    }

    public boolean isSuper()
    {
        return (flags & ACC_SUPER) != 0;
    }

    public boolean isInterface()
    {
        return (flags & ACC_INTERFACE) != 0;
    }

    public boolean isAbstract()
    {
        return (flags & ACC_ABSTRACT) != 0;
    }

    public void setPublic(boolean v)
    {
        if (v)
        {
            flags |= ACC_PUBLIC;
        }
        else
        {
            flags &= ~ACC_PUBLIC;
        }
    }

    public void setFinal(boolean v)
    {
        if (v)
        {
            flags |= ACC_FINAL;
        }
        else
        {
            flags &= ~ACC_FINAL;
        }
    }

    public void setSuper(boolean v)
    {
        if (v)
        {
            flags |= ACC_SUPER;
        }
        else
        {
            flags &= ~ACC_SUPER;
        }
    }

    public void setInterface(boolean v)
    {
        if (v)
        {
            flags |= ACC_INTERFACE;
        }
        else
        {
            flags &= ~ACC_INTERFACE;
        }
    }

    public void setAbstract(boolean v)
    {
        if (v)
        {
            flags |= ACC_ABSTRACT;
        }
        else
        {
            flags &= ~ACC_ABSTRACT;
        }
    }

    public void setSourceFile(String file)
    {
        attributes.put("SourceFile", new SourceFileAttribute(constants.getUTF8("SourceFile"), constants.getUTF8(file)));
    }

    public String getSourceFile()
    {
        Object o = attributes.get("SourceFile");

        if (o == null)
        {
            return null;
        }

        return (String)constants.getObject(((SourceFileAttribute)o).getSourceIndex());
    }

    public void addInterface(String inter)
    {
        interfaces.add(new Integer(constants.getClass(inter)));
    }

    public Field addField(String name, String type)
    {
        Field f;
		
        f = new Field(constants, constants.getUTF8(name), constants.getUTF8(type));
        fields.add(f);
        return f;
    }

    public Method addMethod(String name, String type)
    {
        Method m;
		
        m = new Method(constants, constants.getUTF8(name), constants.getUTF8(type));
        methods.add(m);
        return m;
    }
    
    protected Scope staticInitializerScope;
    
    public Scope addStaticInitializer()
    {
        if (staticInitializerScope == null)
        {
            Method method;
            Scope  scope;
            
            method = addMethod("<clinit>", "()V");
            method.setStatic(true);
            method.setPrivate();
            
            scope                  = method.getCode().getScope();
            staticInitializerScope = scope.add(new Scope(scope, constants));
            scope.addReturns();
        }
        
        return staticInitializerScope.add(new Scope(staticInitializerScope, constants));
    }
}
