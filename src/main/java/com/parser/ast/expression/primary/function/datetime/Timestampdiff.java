package com.parser.ast.expression.primary.function.datetime;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;
import com.parser.ast.expression.primary.literal.IntervalPrimary;

import java.util.List;

public class Timestampdiff extends FunctionExpression {
    private IntervalPrimary.Unit unit;

    public Timestampdiff(IntervalPrimary.Unit unit, List<Expression> arguments) {
        super("TIMESTAMPDIFF", arguments);
        this.unit = unit;
    }

    public IntervalPrimary.Unit getUnit() {
        return unit;
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of Timestampdiff has special arguments");
    }

    public String toString() {
        return "Timestampdiff{" +
                "unit=" + unit +
                '}';
    }
}
