package com.parser.ast.expression.string;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.TernaryOperatorExpression;

public class LikeExpression extends TernaryOperatorExpression {
    private final boolean not;

    public LikeExpression(boolean not, Expression comparee, Expression pattern, Expression escape) {
        super(comparee, pattern, escape);
        this.not = not;
    }

    public boolean isNot() {
        return not;
    }

    public int getPrecedence() {
        return PRECEDENCE_COMPARISION;
    }

    public String toString() {
        return "LikeExpression{" +
                "not=" + not +
                '}';
    }
}
