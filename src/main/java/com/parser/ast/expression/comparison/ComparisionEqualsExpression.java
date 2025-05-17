package com.parser.ast.expression.comparison;

import com.parser.ast.expression.BinaryOperatorExpression;
import com.parser.ast.expression.Expression;
import com.parser.ast.expression.ReplacableExpression;
import com.parser.ast.expression.primary.literal.LiteralBoolean;
import com.parser.util.ExprEvalUtils;
import com.parser.util.Pair;

import java.util.Map;

public class ComparisionEqualsExpression extends BinaryOperatorExpression implements ReplacableExpression {
    public ComparisionEqualsExpression(Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_COMPARISION);
    }

    public String getOperator() {
        return "=";
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        Object left = leftOprand.evaluation(parameters);
        Object right = rightOprand.evaluation(parameters);
        if (left == null || right == null)
            return null;
        if (left == UNEVALUATABLE || right == UNEVALUATABLE)
            return UNEVALUATABLE;
        if (left instanceof Number || right instanceof Number) {
            Pair<Number, Number> pair = ExprEvalUtils.convertNum2SameLevel(left, right);
            left = pair.getKey();
            right = pair.getValue();
        }
        return left.equals(right) ? LiteralBoolean.TRUE : LiteralBoolean.FALSE;
    }

    private Expression replaceExpr;

    public void setReplaceExpr(Expression replaceExpr) {
        this.replaceExpr = replaceExpr;
    }

    public void clearReplaceExpr() {
        this.replaceExpr = null;
    }

}
