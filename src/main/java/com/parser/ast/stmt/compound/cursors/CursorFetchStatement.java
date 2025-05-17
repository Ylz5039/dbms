package com.parser.ast.stmt.compound.cursors;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.compound.CompoundStatement;

import java.util.List;

public class CursorFetchStatement implements CompoundStatement {
    private final Identifier name;
    private final List<Identifier> varNames;

    public CursorFetchStatement(Identifier name, List<Identifier> varNames) {
        this.name = name;
        this.varNames = varNames;
    }

    public Identifier getName() {
        return this.name;
    }

    public List<Identifier> getVarNames() {
        return varNames;
    }

    public String toString() {
        return "CursorFetchStatement{" +
                "name=" + name +
                ", varNames=" + varNames +
                '}';
    }
}
