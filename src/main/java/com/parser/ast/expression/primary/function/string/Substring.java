package com.parser.ast.expression.primary.function.string;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Substring extends FunctionExpression {
    public Substring(List<Expression> arguments) {
        super("SUBSTRING", arguments);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Substring(arguments);
    }

    public String toString() {
        return "Substring{}";
    }
}
