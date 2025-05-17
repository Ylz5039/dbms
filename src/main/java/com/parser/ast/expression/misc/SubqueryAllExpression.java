package com.parser.ast.expression.misc;

import com.parser.ast.expression.UnaryOperatorExpression;

public class SubqueryAllExpression extends UnaryOperatorExpression {
    public SubqueryAllExpression(QueryExpression subquery) {
        super(subquery, PRECEDENCE_PRIMARY);
    }

    public String getOperator() {
        return "ALL";
    }
}
