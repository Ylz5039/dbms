package com.parser.ast.expression.arithmeic;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.UnaryOperatorExpression;
import com.parser.util.ExprEvalUtils;
import com.parser.util.UnaryOperandCalculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class MinusExpression extends UnaryOperatorExpression implements UnaryOperandCalculator {
    public MinusExpression(Expression operand) {
        super(operand, PRECEDENCE_UNARY_OP);
    }

    public String getOperator() {
        return "-";
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        Object operand = getOperand().evaluation(parameters);
        if (operand == null)
            return null;
        if (operand == UNEVALUATABLE)
            return UNEVALUATABLE;
        Number num = null;
        if (operand instanceof String) {
            num = ExprEvalUtils.string2Number((String) operand);
        } else {
            num = (Number) operand;
        }
        return ExprEvalUtils.calculate(this, num);
    }

    public Number calculate(Integer num) {
        if (num == null)
            return null;
        int n = num.intValue();
        if (n == Integer.MIN_VALUE) {
            return Long.valueOf(-(long) n);
        }
        return Integer.valueOf(-n);
    }

    public Number calculate(Long num) {
        if (num == null)
            return null;
        long n = num.longValue();
        if (n == Long.MIN_VALUE) {
            return Long.valueOf(-(long) n);
        }
        return Long.valueOf(-n);
    }

    public Number calculate(BigInteger num) {
        if (num == null)
            return null;
        return num.negate();
    }

    public Number calculate(BigDecimal num) {
        if (num == null)
            return null;
        return num.negate();
    }
}
