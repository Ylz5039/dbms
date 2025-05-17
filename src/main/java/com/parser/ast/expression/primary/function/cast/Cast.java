package com.parser.ast.expression.primary.function.cast;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.function.FunctionExpression;

import java.util.List;

public class Cast extends FunctionExpression {
    private final String typeName;
    private final Expression typeInfo1;
    private final Expression typeInfo2;

    public Cast(Expression expr, String typeName, Expression typeInfo1, Expression typeInfo2) {
        super("CAST", wrapList(expr));
        if (null == typeName) {
            throw new IllegalArgumentException("typeName is null");
        }
        this.typeName = typeName;
        this.typeInfo1 = typeInfo1;
        this.typeInfo2 = typeInfo2;
    }

    public Expression getExpr() {
        return getArguments().get(0);
    }

    public String getTypeName() {
        return typeName;
    }

    public Expression getTypeInfo1() {
        return typeInfo1;
    }

    public Expression getTypeInfo2() {
        return typeInfo2;
    }

    public FunctionExpression constructFunction(List<Expression> arguments) {
        throw new UnsupportedOperationException("function of char has special arguments");
    }

    public String toString() {
        return "Cast{" +
                "typeName='" + typeName + '\'' +
                ", typeInfo1=" + typeInfo1 +
                ", typeInfo2=" + typeInfo2 +
                '}';
    }
}
