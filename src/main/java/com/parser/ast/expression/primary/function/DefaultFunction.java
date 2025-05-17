package com.parser.ast.expression.primary.function;

import com.parser.ast.expression.Expression;

import java.util.List;

public class DefaultFunction extends FunctionExpression {
    private String functionName;

    public DefaultFunction(String functionName, List<Expression> arguments) {
        super(functionName, arguments);
        this.functionName = functionName;
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new DefaultFunction(functionName, arguments);
    }

    public String toString() {
        return "DefaultFunction{" +
                "functionName='" + functionName + '\'' +
                '}';
    }
}
