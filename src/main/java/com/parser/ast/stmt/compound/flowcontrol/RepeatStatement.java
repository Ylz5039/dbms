package com.parser.ast.stmt.compound.flowcontrol;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.SQLStatement;
import com.parser.ast.stmt.compound.CompoundStatement;

public class RepeatStatement implements CompoundStatement {
    private final Identifier label;
    private final SQLStatement stmt;
    private final Expression utilCondition;

    public Identifier getLabel() {
        return label;
    }

    public SQLStatement getStmt() {
        return stmt;
    }

    public Expression getUtilCondition() {
        return utilCondition;
    }

    public RepeatStatement(Identifier label, SQLStatement stmt, Expression utilCondition) {
        this.label = label;
        this.stmt = stmt;
        this.utilCondition = utilCondition;
    }

    public String toString() {
        return "RepeatStatement{" +
                "label=" + label +
                ", stmt=" + stmt +
                ", utilCondition=" + utilCondition +
                '}';
    }
}
