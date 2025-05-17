package com.parser.ast.stmt.compound.flowcontrol;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.compound.CompoundStatement;

public class LeaveStatement implements CompoundStatement {
    private final Identifier label;

    public LeaveStatement(Identifier label) {
        this.label = label;
    }

    public Identifier getLabel() {
        return this.label;
    }

    public String toString() {
        return "LeaveStatement{" +
                "label=" + label +
                '}';
    }
}
