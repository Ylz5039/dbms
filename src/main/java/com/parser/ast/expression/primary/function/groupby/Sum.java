package com.parser.ast.expression.primary.function.groupby;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Sum extends FunctionExpression {
    private final boolean distinct;

    public Sum(Expression expr, boolean distinct) {
        super("SUM", wrapList(expr));
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of char has special arguments");
    }

    public String toString() {
        return "Sum{" +
                "distinct=" + distinct +
                '}';
    }
}
