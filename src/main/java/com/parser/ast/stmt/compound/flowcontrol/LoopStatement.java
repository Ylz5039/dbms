package com.parser.ast.stmt.compound.flowcontrol;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.SQLStatement;
import com.parser.ast.stmt.compound.CompoundStatement;

public class LoopStatement implements CompoundStatement {
    private final Identifier label;
    private final SQLStatement stmt;

    public Identifier getLabel() {
        return label;
    }

    public SQLStatement getStmt() {
        return stmt;
    }

    public LoopStatement(Identifier label, SQLStatement stmt) {
        this.label = label;
        this.stmt = stmt;
    }

    public String toString() {
        return "LoopStatement{" +
                "label=" + label +
                ", stmt=" + stmt +
                '}';
    }
}
