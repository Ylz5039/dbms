package com.parser.ast.stmt.compound.flowcontrol;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.SQLStatement;
import com.parser.ast.stmt.compound.CompoundStatement;

public class WhileStatement implements CompoundStatement {
    private final Identifier label;
    private final SQLStatement stmt;
    private final Expression whileCondition;

    public Identifier getLabel() {
        return label;
    }

    public SQLStatement getStmt() {
        return stmt;
    }

    public Expression getWhileCondition() {
        return whileCondition;
    }

    public WhileStatement(Identifier label, SQLStatement stmt, Expression whileCondition) {
        this.label = label;
        this.stmt = stmt;
        this.whileCondition = whileCondition;
    }

    public String toString() {
        return "WhileStatement{" +
                "label=" + label +
                ", stmt=" + stmt +
                ", whileCondition=" + whileCondition +
                '}';
    }
}
