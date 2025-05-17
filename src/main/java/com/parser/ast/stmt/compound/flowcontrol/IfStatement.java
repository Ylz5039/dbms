package com.parser.ast.stmt.compound.flowcontrol;

import com.parser.ast.expression.Expression;
import com.parser.ast.stmt.SQLStatement;
import com.parser.ast.stmt.compound.CompoundStatement;
import com.parser.util.Pair;

import java.util.List;

public class IfStatement implements CompoundStatement {
    private final List<Pair<Expression, List<SQLStatement>>> ifStatements;
    private final List<SQLStatement> elseStatement;

    public IfStatement(List<Pair<Expression, List<SQLStatement>>> ifStatements,
                       List<SQLStatement> elseStatements) {
        this.ifStatements = ifStatements;
        this.elseStatement = elseStatements;
    }

    public List<Pair<Expression, List<SQLStatement>>> getIfStatements() {
        return ifStatements;
    }

    public List<SQLStatement> getElseStatement() {
        return elseStatement;
    }

    public String toString() {
        return "IfStatement{" +
                "ifStatements=" + ifStatements +
                ", elseStatement=" + elseStatement +
                '}';
    }
}
