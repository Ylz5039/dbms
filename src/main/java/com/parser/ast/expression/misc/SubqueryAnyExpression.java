package com.parser.ast.expression.misc;

import com.parser.ast.expression.UnaryOperatorExpression;

public class SubqueryAnyExpression extends UnaryOperatorExpression {
    public SubqueryAnyExpression(QueryExpression subquery) {
        super(subquery, PRECEDENCE_PRIMARY);
    }

    public String getOperator() {
        return "ANY";
    }
}
