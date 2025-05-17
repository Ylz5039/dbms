package com.parser.ast.expression.bit;

import com.parser.ast.expression.BinaryOperatorExpression;
import com.parser.ast.expression.Expression;

public class BitAndExpression extends BinaryOperatorExpression {
    public BitAndExpression(Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_BIT_AND);
    }

    public String getOperator() {
        return "&";
    }

}
