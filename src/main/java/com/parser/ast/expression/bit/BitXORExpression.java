package com.parser.ast.expression.bit;

import com.parser.ast.expression.BinaryOperatorExpression;
import com.parser.ast.expression.Expression;

public class BitXORExpression extends BinaryOperatorExpression {
    public BitXORExpression(Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_BIT_XOR);
    }

    public String getOperator() {
        return "^";
    }
}
