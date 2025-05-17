package com.parser.ast.expression.primary.function.datetime;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Curtime extends FunctionExpression {
    public Curtime() {
        super("CURTIME", null);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Curtime();
    }

    public String toString() {
        return "Curtime{}";
    }
}
