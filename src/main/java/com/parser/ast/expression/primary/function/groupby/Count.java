package com.parser.ast.expression.primary.function.groupby;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Count extends FunctionExpression {
    private final boolean distinct;

    public Count(List<Expression> arguments) {
        super("COUNT", arguments);
        this.distinct = true;
    }

    public Count(Expression arg) {
        super("COUNT", wrapList(arg));
        this.distinct = false;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        return new Count(arguments);
    }

    public String toString() {
        return "Count{" +
                "distinct=" + distinct +
                '}';
    }
}
