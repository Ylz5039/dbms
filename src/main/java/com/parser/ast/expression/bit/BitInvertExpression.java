package com.parser.ast.expression.bit;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.UnaryOperatorExpression;

public class BitInvertExpression extends UnaryOperatorExpression {
    public BitInvertExpression(Expression operand) {
        super(operand, PRECEDENCE_UNARY_OP);
    }

    public String getOperator() {
        return "~";
    }
}
