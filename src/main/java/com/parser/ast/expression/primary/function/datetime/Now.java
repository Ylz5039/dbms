package com.parser.ast.expression.primary.function.datetime;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Now extends FunctionExpression {
    public Now() {
        super("NOW", null);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Now();
    }

    public String toString() {
        return "Now{}";
    }
}
