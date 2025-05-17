package com.parser.ast.expression.primary.function.datetime;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class CurTimestamp extends FunctionExpression {
    public CurTimestamp() {
        super("CURTIMTSTAMP", null);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new CurTimestamp();
    }

    public String toString() {
        return "CurTimestamp{}";
    }
}
