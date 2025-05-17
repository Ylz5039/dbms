package com.parser.ast.expression.type;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.UnaryOperatorExpression;

public class CastBinaryExpression extends UnaryOperatorExpression {
    public CastBinaryExpression(Expression operand) {
        super(operand, PRECEDENCE_BINARY);
    }

    public String getOperator() {
        return "BINARY";
    }

    public String toString() {
        return "CastBinaryExpression{}";
    }
}
