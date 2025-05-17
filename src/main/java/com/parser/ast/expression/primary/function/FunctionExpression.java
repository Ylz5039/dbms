package com.parser.ast.expression.primary.function;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.PrimaryExpression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class FunctionExpression extends PrimaryExpression {
    protected static List<Expression> wrapList(Expression expr) {
        List<Expression> list = new ArrayList<Expression>(1);
        list.add(expr);
        return list;
    }

    public abstract FunctionExpression constructFunction(List<Expression> arguments);

    protected final String functionName;
    protected final List<Expression> arguments;

    public FunctionExpression(String functionName, List<Expression> arguments) {
        super();
        this.functionName = functionName;
        if (arguments == null || arguments.isEmpty()) {
            this.arguments = Collections.emptyList();
        } else {
            if (arguments instanceof ArrayList) {
                this.arguments = arguments;
            } else {
                this.arguments = new ArrayList<Expression>(arguments);
            }
        }
    }

    public void init() {}

    public List<Expression> getArguments() {
        return arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public Expression setCacheEvalRst(boolean cacheEvalRst) {
        return super.setCacheEvalRst(cacheEvalRst);
    }

    public String toString() {
        return "FunctionExpression{" +
                "functionName='" + functionName + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
