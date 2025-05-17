package com.parser.ast.stmt.compound.flowcontrol;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.compound.CompoundStatement;

public class IterateStatement implements CompoundStatement {
    private final Identifier label;

    public IterateStatement(Identifier label) {
        this.label = label;
    }

    public Identifier getLabel() {
        return this.label;
    }

    public String toString() {
        return "IterateStatement{" +
                "label=" + label +
                '}';
    }
}
