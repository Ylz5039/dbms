package com.parser.ast.stmt.compound.cursors;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.compound.CompoundStatement;

public class CursorCloseStatement implements CompoundStatement {
    private final Identifier name;

    public CursorCloseStatement(Identifier name) {
        this.name = name;
    }

    public Identifier getName() {
        return this.name;
    }

    public String toString() {
        return "CursorCloseStatement{" +
                "name=" + name +
                '}';
    }
}
