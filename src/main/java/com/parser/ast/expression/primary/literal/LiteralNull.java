package com.parser.ast.expression.primary.literal;

import java.util.Map;

public class LiteralNull extends Literal {
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return null;
    }
}
