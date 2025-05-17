package com.parser.ast.expression.primary.function.spatial;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class AsWKT extends FunctionExpression {
    public AsWKT(Expression expr) {
        super("ASWKT", wrapList(expr));
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of char has special arguments");
    }

    public String toString() {
        return "AsWKT{}";
    }
}
