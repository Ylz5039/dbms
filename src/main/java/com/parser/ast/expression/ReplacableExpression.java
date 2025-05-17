package com.parser.ast.expression;

import com.parser.ast.expression.primary.literal.LiteralBoolean;

public interface ReplacableExpression extends Expression {
    LiteralBoolean BOOL_FALSE = new LiteralBoolean(false);

    void setReplaceExpr(Expression replaceExpr);

    void clearReplaceExpr();
}
