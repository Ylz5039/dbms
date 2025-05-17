package com.parser.ast.stmt.compound;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.SQLStatement;

import java.util.List;

public class BeginEndStatement implements CompoundStatement {
    private final Identifier label;
    private final List<SQLStatement> statements;

    public BeginEndStatement(Identifier label, List<SQLStatement> statements) {
        this.label = label;
        this.statements = statements;
    }

    public Identifier getLabel() {
        return label;
    }

    public List<SQLStatement> getStatements() {
        return statements;
    }

    public String toString() {
        return "BeginEndStatement{" +
                "label=" + label +
                ", statements=" + statements +
                '}';
    }
}
