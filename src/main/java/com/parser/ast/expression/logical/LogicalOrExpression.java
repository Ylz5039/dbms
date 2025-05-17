package com.parser.ast.expression.logical;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.PolyadicOperatorExpression;
import com.parser.ast.expression.primary.literal.LiteralBoolean;
import com.parser.util.ExprEvalUtils;

import java.util.Map;

public class LogicalOrExpression extends PolyadicOperatorExpression {
    public LogicalOrExpression() {
        super(PRECEDENCE_LOGICAL_OR);
    }

    public String getOperator() {
        return "OR";
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        for (Expression operand : operands) {
            Object val = operand.evaluation(parameters);
            if (val == null)
                return null;
            if (val == UNEVALUATABLE)
                return UNEVALUATABLE;
            if (ExprEvalUtils.obj2bool(val)) {
                return LiteralBoolean.TRUE;
            }
        }
        return LiteralBoolean.FALSE;
    }

}
