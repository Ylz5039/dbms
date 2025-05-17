package com.parser.ast.expression.primary.function.datetime;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class UtcDate extends FunctionExpression {
    public UtcDate(List<Expression> arguments) {
        super("UTC_DATE", arguments);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new UtcDate(arguments);
    }


    public String toString() {
        return "UtcDate{}";
    }
}
