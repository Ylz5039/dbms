package com.parser.ast.expression.primary.function.comparison;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Interval extends FunctionExpression {
    public Interval(List<Expression> arguments) {
        super("INTERVAL", arguments);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Interval(arguments);
    }

    public String toString() {
        return "Interval{}";
    }
}
