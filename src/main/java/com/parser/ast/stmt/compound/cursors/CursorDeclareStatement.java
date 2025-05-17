package com.parser.ast.stmt.compound.cursors;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.SQLStatement;
import com.parser.ast.stmt.compound.CompoundStatement;

public class CursorDeclareStatement implements CompoundStatement {
    private final Identifier name;
    private final SQLStatement stmt;

    public CursorDeclareStatement(Identifier name, SQLStatement stmt) {
        this.name = name;
        this.stmt = stmt;
    }

    public Identifier getName() {
        return this.name;
    }

    public SQLStatement getStmt() {
        return this.stmt;
    }

    public String toString() {
        return "CursorDeclareStatement{" +
                "name=" + name +
                ", stmt=" + stmt +
                '}';
    }
}
