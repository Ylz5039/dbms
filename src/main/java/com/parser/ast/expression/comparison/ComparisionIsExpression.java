package com.parser.ast.expression.comparison;

import com.parser.ast.expression.AbstractExpression;
import com.parser.ast.expression.Expression;
import com.parser.ast.expression.ReplacableExpression;

import java.util.Map;

public class ComparisionIsExpression extends AbstractExpression implements ReplacableExpression {
    public static final int IS_NULL = 1;
    public static final int IS_TRUE = 2;
    public static final int IS_FALSE = 3;
    public static final int IS_UNKNOWN = 4;
    public static final int IS_NOT_NULL = 5;
    public static final int IS_NOT_TRUE = 6;
    public static final int IS_NOT_FALSE = 7;
    public static final int IS_NOT_UNKNOWN = 8;

    private final int mode;
    private final Expression operand;

    public ComparisionIsExpression(Expression operand, int mode) {
        this.operand = operand;
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public Expression getOperand() {
        return operand;
    }

    public int getPrecedence() {
        return PRECEDENCE_COMPARISION;
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return UNEVALUATABLE;
    }

    private Expression replaceExpr;

    public void setReplaceExpr(Expression replaceExpr) {
        this.replaceExpr = replaceExpr;
    }

    public void clearReplaceExpr() {
        this.replaceExpr = null;
    }

}
