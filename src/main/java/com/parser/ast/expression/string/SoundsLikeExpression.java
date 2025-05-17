package com.parser.ast.expression.string;

import com.parser.ast.expression.BinaryOperatorExpression;
import com.parser.ast.expression.Expression;

public class SoundsLikeExpression extends BinaryOperatorExpression {
    public SoundsLikeExpression(Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_COMPARISION);
    }

    public String getOperator() {
        return "SOUNDS LIKE";
    }

    public String toString() {
        return "SoundsLikeExpression{}";
    }
}
