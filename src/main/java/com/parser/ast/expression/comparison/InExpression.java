package com.parser.ast.expression.comparison;

import com.parser.ast.expression.BinaryOperatorExpression;
import com.parser.ast.expression.Expression;
import com.parser.ast.expression.ReplacableExpression;
import com.parser.ast.expression.misc.InExpressionList;
import com.parser.ast.expression.misc.QueryExpression;

public class InExpression extends BinaryOperatorExpression implements ReplacableExpression {
    private final boolean not;

    public InExpression(boolean not, Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_COMPARISION);
        this.not = not;
    }

    public boolean isNot() {
        return not;
    }

    public InExpressionList getInExpressionList() {
        if (rightOprand instanceof InExpressionList) {
            return (InExpressionList) rightOprand;
        }
        return null;
    }

    public QueryExpression getQueryExpression() {
        if (rightOprand instanceof QueryExpression) {
            return (QueryExpression) rightOprand;
        }
        return null;
    }

    public String getOperator() {
        return not ? "NOT IN" : "IN";
    }

    private Expression replaceExpr;

    public void setReplaceExpr(Expression replaceExpr) {
        this.replaceExpr = replaceExpr;
    }

    public void clearReplaceExpr() {
        this.replaceExpr = null;
    }

}
