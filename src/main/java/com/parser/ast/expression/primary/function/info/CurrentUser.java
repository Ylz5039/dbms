package com.parser.ast.expression.primary.function.info;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class CurrentUser extends FunctionExpression {
    public CurrentUser() {
        super("CURRENT_USER", null);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new CurrentUser();
    }

    public String toString() {
        return "CurrentUser{}";
    }
}
