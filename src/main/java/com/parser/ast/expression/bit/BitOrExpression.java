package com.parser.ast.expression.bit;

import com.parser.ast.expression.BinaryOperatorExpression;
import com.parser.ast.expression.Expression;

public class BitOrExpression extends BinaryOperatorExpression {
    public BitOrExpression(Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_BIT_OR);
    }

    public String getOperator() {
        return "|";
    }
}
