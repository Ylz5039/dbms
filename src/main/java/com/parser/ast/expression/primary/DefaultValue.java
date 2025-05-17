package com.parser.ast.expression.primary;

import java.util.Map;

public class DefaultValue extends PrimaryExpression {
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return null;
    }

    public String toString() {
        return "DEFAULT";
    }
}
