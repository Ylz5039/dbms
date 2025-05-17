package com.parser.ast.expression.primary;

import java.util.Map;

import com.parser.ast.expression.AbstractExpression;

public abstract class PrimaryExpression extends AbstractExpression {
    public int getPrecedence() {
        return PRECEDENCE_PRIMARY;
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return UNEVALUATABLE;
    }

    public String toString() {
        return "PrimaryExpression{}";
    }
}
