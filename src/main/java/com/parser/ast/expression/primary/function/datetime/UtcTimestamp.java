package com.parser.ast.expression.primary.function.datetime;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class UtcTimestamp extends FunctionExpression {
    public UtcTimestamp(List<Expression> arguments) {
        super("UTC_TIMESTAMP", arguments);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new UtcTimestamp(arguments);
    }

    public String toString() {
        return "UtcTimestamp{}";
    }
}
