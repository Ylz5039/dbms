package com.parser.ast.expression.comparison;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.ReplacableExpression;
import com.parser.ast.expression.TernaryOperatorExpression;

public class BetweenAndExpression
        extends TernaryOperatorExpression
        implements ReplacableExpression {
    private final boolean not;

    public BetweenAndExpression(boolean not, Expression comparee, Expression notLessThan,
                                Expression notGreaterThan) {
        super(comparee, notLessThan, notGreaterThan);
        this.not = not;
    }

    public boolean isNot() {
        return not;
    }

    public int getPrecedence() {
        return PRECEDENCE_BETWEEN_AND;
    }

    private Expression replaceExpr;

    public void setReplaceExpr(Expression replaceExpr) {
        this.replaceExpr = replaceExpr;
    }

    public void clearReplaceExpr() {
        this.replaceExpr = null;
    }

}
