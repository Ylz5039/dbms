package com.parser.ast.expression.type;

import com.parser.ast.expression.AbstractExpression;
import com.parser.ast.expression.Expression;

import java.util.Map;

public class CollateExpression extends AbstractExpression {
    private final String collateName;
    private final Expression string;

    public CollateExpression(Expression string, String collateName) {
        if (collateName == null)
            throw new IllegalArgumentException("collateName is null");
        this.string = string;
        this.collateName = collateName;
    }

    public String getCollateName() {
        return collateName;
    }

    public Expression getString() {
        return string;
    }

    public int getPrecedence() {
        return PRECEDENCE_COLLATE;
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return string.evaluation(parameters);
    }

    public String toString() {
        return "CollateExpression{" +
                "collateName='" + collateName + '\'' +
                ", string=" + string +
                '}';
    }
}
