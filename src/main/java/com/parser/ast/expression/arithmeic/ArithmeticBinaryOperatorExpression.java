package com.parser.ast.expression.arithmeic;

import com.parser.ast.expression.BinaryOperatorExpression;
import com.parser.ast.expression.Expression;
import com.parser.util.BinaryOperandCalculator;
import com.parser.util.ExprEvalUtils;
import com.parser.util.Pair;

import java.util.Map;

public abstract class ArithmeticBinaryOperatorExpression extends BinaryOperatorExpression implements BinaryOperandCalculator {
    protected ArithmeticBinaryOperatorExpression(Expression leftOprand, Expression rightOprand,
                                                 int precedence) {
        super(leftOprand, rightOprand, precedence, true);
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        Object left = leftOprand.evaluation(parameters);
        Object right = rightOprand.evaluation(parameters);
        if (left == null || right == null)
            return null;
        if (left == UNEVALUATABLE || right == UNEVALUATABLE)
            return UNEVALUATABLE;
        Pair<Number, Number> pair = ExprEvalUtils.convertNum2SameLevel(left, right);
        return ExprEvalUtils.calculate(this, pair.getKey(), pair.getValue());
    }
}
