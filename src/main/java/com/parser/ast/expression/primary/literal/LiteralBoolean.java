package com.parser.ast.expression.primary.literal;

import java.util.Map;

public class LiteralBoolean extends Literal {
    public static final Integer TRUE = Integer.valueOf(1);
    public static final Integer FALSE = Integer.valueOf(0);
    private final boolean value;

    public LiteralBoolean(boolean value) {
        super();
        this.value = value;
    }

    public boolean isTrue() {
        return value;
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return value ? TRUE : FALSE;
    }

    public String toString() {
        return "LiteralBoolean{" +
                "value=" + value +
                '}';
    }
}
