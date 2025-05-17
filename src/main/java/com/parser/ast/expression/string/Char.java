package com.parser.ast.expression.string;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Char extends FunctionExpression {
    private final String charset;

    public Char(List<Expression> arguments, String charset) {
        super("CHAR", arguments);
        this.charset = charset;
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of char has special arguments");
    }

    public String getCharset() {
        return charset;
    }

    public String toString() {
        return "Char{" +
                "charset='" + charset + '\'' +
                '}';
    }
}
