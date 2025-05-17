package com.parser.ast.expression.logical;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.UnaryOperatorExpression;
import com.parser.ast.expression.primary.literal.LiteralBoolean;
import com.parser.util.ExprEvalUtils;

import java.util.Map;

public class NegativeValueExpression extends UnaryOperatorExpression {

    public NegativeValueExpression(Expression operand) {
        super(operand, PRECEDENCE_UNARY_OP);
    }

    public String getOperator() {
        return "!";
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
