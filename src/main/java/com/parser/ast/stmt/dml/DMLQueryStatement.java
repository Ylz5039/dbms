package com.parser.ast.stmt.dml;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.misc.QueryExpression;

import java.util.Map;

public abstract class DMLQueryStatement extends DMLStatement implements QueryExpression {
    public int getPrecedence() {
        return PRECEDENCE_QUERY;
    }

    public Expression setCacheEvalRst(boolean cacheEvalRst) {
        return this;
    }

    public Object evaluation(Map<? extends Object, ? extends Object> parameters) {
        return UNEVALUATABLE;
    }

    private boolean isInParen = false;

    public boolean isInParen() {
        return isInParen;
    }

    public void setInParen(boolean isInParen) {
        this.isInParen = isInParen;
    }

    public String originSQLStr() {
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DMLQueryStatement {\n")
                .append("    precedence=").append(getPrecedence()).append("\n")
                .append("    isInParen=").append(isInParen).append("\n")
                .append("}");
        return sb.toString();
    }

}