package com.parser.ast.expression.logical;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.UnaryOperatorExpression;
import com.parser.ast.expression.primary.literal.LiteralBoolean;
import com.parser.util.ExprEvalUtils;

import java.util.Map;

public class LogicalNotExpression extends UnaryOperatorExpression {

    public LogicalNotExpression(Expression operand) {
        super(operand, PRECEDENCE_LOGICAL_NOT);
    }

    public String getOperator() {
        return "NOT";
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        Object operand = getOperand().evaluation(parameters);
        if (operand == null)
            return null;
        if (operand == UNEVALUATABLE)
            return UNEVALUATABLE;
        boolean bool = ExprEvalUtils.obj2bool(operand);
        return bool ? LiteralBoolean.FALSE : LiteralBoolean.TRUE;
    }

}
