package com.parser.ast.expression.primary.function.datetime;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Curdate extends FunctionExpression {
    public Curdate() {
        super("CURDATE", null);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Curdate();
    }

    public String toString() {
        return "Curdate{}";
    }
}
