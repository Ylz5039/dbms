package com.parser.ast.expression.primary.function.datetime;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class GetFormat extends FunctionExpression {
    public static enum FormatType {
        DATE, TIME, DATETIME
    }

    private FormatType formatType;

    public GetFormat(FormatType type, Expression format) {
        super("GET_FORMAT", wrapList(format));
        this.formatType = type;
    }

    public FormatType getFormatType() {
        return formatType;
    }

    public Expression getFormat() {
        return arguments.get(0);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of GetFormat has special arguments");
    }

    public String toString() {
        return "GetFormat{" +
                "formatType=" + formatType +
                '}';
    }
}
