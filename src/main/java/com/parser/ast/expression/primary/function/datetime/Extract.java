package com.parser.ast.expression.primary.function.datetime;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;
import com.parser.ast.expression.primary.literal.IntervalPrimary;

import java.util.List;

public class Extract extends FunctionExpression {
    private IntervalPrimary.Unit unit;

    public Extract(IntervalPrimary.Unit unit, Expression date) {
        super("EXTRACT", wrapList(date));
        this.unit = unit;
    }

    public IntervalPrimary.Unit getUnit() {
        return unit;
    }

    public Expression getDate() {
        return arguments.get(0);
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of extract has special arguments");
    }

    public String toString() {
        return "Extract{" +
                "unit=" + unit +
                '}';
    }
}
