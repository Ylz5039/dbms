package com.parser.ast.expression.logical;

import com.parser.ast.expression.BinaryOperatorExpression;
import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.literal.LiteralBoolean;
import com.parser.util.ExprEvalUtils;

import java.util.Map;

public class LogicalXORExpression extends BinaryOperatorExpression {
    public LogicalXORExpression(Expression left, Expression right) {
        super(left, right, PRECEDENCE_LOGICAL_XOR);
    }

    public String getOperator() {
        return "XOR";
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        Object left = leftOprand.evaluation(parameters);
        Object right = rightOprand.evaluation(parameters);
        if (left == null || right == null)
            return null;
        if (left == UNEVALUATABLE || right == UNEVALUATABLE)
            return UNEVALUATABLE;
        boolean b1 = ExprEvalUtils.obj2bool(left);
        boolean b2 = ExprEvalUtils.obj2bool(right);
        return b1 != b2 ? LiteralBoolean.TRUE : LiteralBoolean.FALSE;
    }

}
