package com.parser.ast.stmt.compound.cursors;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.compound.CompoundStatement;

public class CursorOpenStatement implements CompoundStatement {
    private final Identifier name;

    public CursorOpenStatement(Identifier name) {
        this.name = name;
    }

    public Identifier getName() {
        return this.name;
    }

    public String toString() {
        return "CursorOpenStatement{" +
                "name=" + name +
                '}';
    }
}
