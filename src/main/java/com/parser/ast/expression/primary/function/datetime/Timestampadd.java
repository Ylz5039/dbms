package com.parser.ast.expression.primary.function.datetime;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;
import com.parser.ast.expression.primary.literal.IntervalPrimary;

import java.util.List;

public class Timestampadd extends FunctionExpression {
    private IntervalPrimary.Unit unit;

    public Timestampadd(IntervalPrimary.Unit unit, List<Expression> arguments) {
        super("TIMESTAMPADD", arguments);
        this.unit = unit;
    }

    public IntervalPrimary.Unit getUnit() {
        return unit;
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of Timestampadd has special arguments");
    }

    public String toString() {
        return "Timestampadd{" +
                "unit=" + unit +
                '}';
    }
}
