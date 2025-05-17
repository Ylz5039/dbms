package com.parser.ast.expression.primary.function.string;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Locate extends FunctionExpression {
    public Locate(List<Expression> arguments) {
        super("LOCATE", arguments);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Locate(arguments);
    }

    public String toString() {
        return "Locate{}";
    }
}
