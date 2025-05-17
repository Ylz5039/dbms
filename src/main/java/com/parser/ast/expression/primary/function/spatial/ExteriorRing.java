package com.parser.ast.expression.primary.function.spatial;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class ExteriorRing extends FunctionExpression {
    public ExteriorRing(Expression expr) {
        super("EXTERIORRING", wrapList(expr));
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of char has special arguments");
    }

    public String toString() {
        return "ExteriorRing{}";
    }
}
