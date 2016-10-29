package com.sierra.agi.logic.interpret.expression;

import java.io.IOException;
import java.io.InputStream;

import com.sierra.agi.logic.interpret.LogicReader;

public class ExpressionSaidOneWord extends ExpressionSaid {

    public ExpressionSaidOneWord(InputStream stream, LogicReader reader, short bytecode, short engineEmulation) throws Exception {
        super(stream, reader, bytecode, engineEmulation);
    }

    protected int getWordCount(InputStream stream) throws IOException {
        return 1;
    }
}