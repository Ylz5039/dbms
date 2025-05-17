package com.parser.ast.expression.primary.function.spatial;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Equals extends FunctionExpression {
    public Equals(List<Expression> arguments) {
        super("EQUALS", arguments);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Equals(arguments);
    }

    public String toString() {
        return "Equals{}";
    }
}
