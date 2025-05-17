package com.parser.ast.stmt.compound.flowcontrol;

import com.parser.ast.expression.Expression;
import com.parser.ast.stmt.SQLStatement;
import com.parser.ast.stmt.compound.CompoundStatement;
import com.parser.util.Pair;

import java.util.List;

public class CaseStatement implements CompoundStatement {
    private final Expression caseValue;
    private final List<Pair<Expression, SQLStatement>> whenList;
    private final SQLStatement elseStmt;

    public CaseStatement(Expression caseValue, List<Pair<Expression, SQLStatement>> whenList,
                         SQLStatement elseStmt) {
        this.caseValue = caseValue;
        this.whenList = whenList;
        this.elseStmt = elseStmt;
    }

    public Expression getCaseValue() {
        return caseValue;
    }

    public List<Pair<Expression, SQLStatement>> getWhenList() {
        return whenList;
    }

    public SQLStatement getElseStmt() {
        return elseStmt;
    }

    public String toString() {
        return "CaseStatement{" +
                "caseValue=" + caseValue +
                ", whenList=" + whenList +
                ", elseStmt=" + elseStmt +
                '}';
    }
}
