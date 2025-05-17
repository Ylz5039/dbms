package com.parser.ast.expression;

import java.util.Map;

public abstract class TernaryOperatorExpression extends AbstractExpression {
    private final Expression first;
    private final Expression second;
    private final Expression third;

    public TernaryOperatorExpression(Expression first, Expression second, Expression third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Expression getFirst() {
        return first;
    }

    public Expression getSecond() {
        return second;
    }

    public Expression getThird() {
        return third;
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return UNEVALUATABLE;
    }

    public String toString() {
        return "TernaryOperatorExpression{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                '}';
    }
}
