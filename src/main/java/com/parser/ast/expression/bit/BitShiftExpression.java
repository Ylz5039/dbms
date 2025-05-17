package com.parser.ast.expression.bit;

import com.parser.ast.expression.BinaryOperatorExpression;
import com.parser.ast.expression.Expression;

public class BitShiftExpression extends BinaryOperatorExpression {
    private final boolean negative;

    public BitShiftExpression(boolean negative, Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_BIT_SHIFT);
        this.negative = negative;
    }

    public boolean isRightShift() {
        return negative;
    }

    public String getOperator() {
        return negative ? ">>" : "<<";
    }
}
