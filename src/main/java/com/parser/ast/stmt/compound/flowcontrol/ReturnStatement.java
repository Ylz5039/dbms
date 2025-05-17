package com.parser.ast.stmt.compound.flowcontrol;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.compound.CompoundStatement;

public class ReturnStatement implements CompoundStatement {
    private final Identifier label;

    public ReturnStatement(Identifier label) {
        this.label = label;
    }

    public Identifier getLabel() {
        return this.label;
    }

    public String toString() {
        return "ReturnStatement{" +
                "label=" + label +
                '}';
    }
}
