package com.parser.ast.expression.logical;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.PolyadicOperatorExpression;
import com.parser.ast.expression.primary.literal.LiteralBoolean;
import com.parser.util.ExprEvalUtils;

import java.util.Map;

public class LogicalAndExpression extends PolyadicOperatorExpression {
    public LogicalAndExpression() {
        super(PRECEDENCE_LOGICAL_AND);
    }

    public String getOperator() {
        return "AND";
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        for (Expression operand : operands) {
            Object val = operand.evaluation(parameters);
            if (val == null)
                return null;
            if (val == UNEVALUATABLE)
                return UNEVALUATABLE;
            if (!ExprEvalUtils.obj2bool(val)) {
                return LiteralBoolean.FALSE;
            }
        }
        return LiteralBoolean.TRUE;
    }

}
