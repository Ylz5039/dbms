package com.parser.ast.expression.arithmeic;

import com.parser.ast.expression.Expression;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ArithmeticMultiplyExpression extends ArithmeticBinaryOperatorExpression{
    public ArithmeticMultiplyExpression(Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_ARITHMETIC_FACTOR_OP);
    }

    public String getOperator() {
        return "*";
    }

    public Number calculate(Integer integer1, Integer integer2) {
        throw new UnsupportedOperationException();
    }

    public Number calculate(Long long1, Long long2) {
        throw new UnsupportedOperationException();
    }

    public Number calculate(BigInteger bigint1, BigInteger bigint2) {
        throw new UnsupportedOperationException();
    }

    public Number calculate(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        throw new UnsupportedOperationException();
    }
}
