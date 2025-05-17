package com.parser.ast.expression.misc;

import com.parser.ast.expression.BinaryOperatorExpression;
import com.parser.ast.expression.Expression;

public class AssignmentExpression extends BinaryOperatorExpression {

    public AssignmentExpression(Expression left, Expression right) {
        super(left, right, Expression.PRECEDENCE_ASSIGNMENT, false);
    }

    public String getOperator() {
        return ":=";
    }
}
