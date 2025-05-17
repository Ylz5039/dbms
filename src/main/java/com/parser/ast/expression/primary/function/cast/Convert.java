package com.parser.ast.expression.primary.function.cast;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Convert extends FunctionExpression {
    private final String transcodeName;

    public Convert(Expression arg, String transcodeName) {
        super("CONVERT", wrapList(arg));
        if (null == transcodeName) {
            throw new IllegalArgumentException("transcodeName is null");
        }
        this.transcodeName = transcodeName;
    }

    public String getTranscodeName() {
        return transcodeName;
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of char has special arguments");
    }

    public String toString() {
        return "Convert{" +
                "transcodeName='" + transcodeName + '\'' +
                '}';
    }
}
