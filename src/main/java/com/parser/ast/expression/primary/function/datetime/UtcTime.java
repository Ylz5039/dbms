package com.parser.ast.expression.primary.function.datetime;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class UtcTime extends FunctionExpression {
    public UtcTime(List<Expression> arguments) {
        super("UTC_TIME", arguments);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new UtcTime(arguments);
    }

    public String toString() {
        return "UtcTime{}";
    }
}
