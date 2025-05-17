package com.parser.ast.expression.primary.function.spatial;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Contains extends FunctionExpression {
    public Contains(List<Expression> arguments) {
        super("CONTAINS", arguments);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Contains(arguments);
    }

    public String toString() {
        return "Contains{}";
    }
}
