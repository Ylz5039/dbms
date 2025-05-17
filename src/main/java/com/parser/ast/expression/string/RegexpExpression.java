package com.parser.ast.expression.string;

import com.parser.ast.expression.BinaryOperatorExpression;
import com.parser.ast.expression.Expression;

public class RegexpExpression extends BinaryOperatorExpression {
    private final boolean not;

    public RegexpExpression(boolean not, Expression comparee, Expression pattern) {
        super(comparee, pattern, PRECEDENCE_COMPARISION);
        this.not = not;
    }

    public boolean isNot() {
        return not;
    }

    public String getOperator() {
        return not ? "NOT REGEXP" : "REGEXP";
    }

    public String toString() {
        return "RegexpExpression{" +
                "not=" + not +
                '}';
    }
}
