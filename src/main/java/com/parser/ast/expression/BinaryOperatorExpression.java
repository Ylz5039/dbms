package com.parser.ast.expression;

import java.util.Map;

public abstract class BinaryOperatorExpression extends AbstractExpression{
    protected final Expression leftOprand;
    protected final Expression rightOprand;
    protected final int precedence;
    protected final boolean leftCombine;

    protected BinaryOperatorExpression(Expression leftOprand, Expression rightOprand,
                                       int precedence) {
        this.leftOprand = leftOprand;
        this.rightOprand = rightOprand;
        this.precedence = precedence;
        this.leftCombine = true;
    }

    protected BinaryOperatorExpression(Expression leftOprand, Expression rightOprand,
                                       int precedence, boolean leftCombine) {
        this.leftOprand = leftOprand;
        this.rightOprand = rightOprand;
        this.precedence = precedence;
        this.leftCombine = leftCombine;
    }

    public Expression getLeftOprand() {
        return leftOprand;
    }

    public Expression getRightOprand() {
        return rightOprand;
    }

    public int getPrecedence() {
        return precedence;
    }

    public boolean isLeftCombine() {
        return leftCombine;
    }

    public abstract String getOperator();

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return UNEVALUATABLE;
    }

    public String toString() {
        return "BinaryOperatorExpression{" +
                "leftOprand=" + leftOprand +
                ", rightOprand=" + rightOprand +
                ", precedence=" + precedence +
                ", leftCombine=" + leftCombine +
                '}';
    }
}
