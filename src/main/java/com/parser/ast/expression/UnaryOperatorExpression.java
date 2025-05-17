package com.parser.ast.expression;

import java.util.Map;

public abstract class UnaryOperatorExpression extends AbstractExpression {
    private final Expression operand;
    protected final int precedence;

    public UnaryOperatorExpression(Expression operand, int precedence) {
        if (operand == null)
            throw new IllegalArgumentException("operand is null");
        this.operand = operand;
        this.precedence = precedence;
    }

    public Expression getOperand() {
        return operand;
    }

    public int getPrecedence() {
        return precedence;
    }

    public abstract String getOperator();

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return UNEVALUATABLE;
    }

    public String toString() {
        return "UnaryOperatorExpression{" +
                "operand=" + operand +
                ", precedence=" + precedence +
                '}';
    }
}
