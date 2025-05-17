package com.parser.ast.expression;

import java.util.Map;

public abstract class AbstractExpression implements Expression {
    private boolean cacheEvalRst = true;
    private boolean evaluated;
    private Object evaluationCache;
    protected String originSQL;

    public Expression setCacheEvalRst(boolean cacheEvalRst) {
        this.cacheEvalRst = cacheEvalRst;
        return this;
    }

    public final Object evaluation(Map<? extends Object, ? extends Object> parameters) {
        if (cacheEvalRst) {
            if (evaluated) {
                return evaluationCache;
            }
            evaluationCache = evaluationInternal(parameters);
            evaluated = true;
            return evaluationCache;
        }
        return evaluationInternal(parameters);
    }

    protected abstract Object evaluationInternal(
            Map<? extends Object, ? extends Object> parameters);

    public String originSQLStr() {
        return originSQL;
    }

    public void setOriginSQL(String originSQL) {
        this.originSQL = originSQL;
    }

    public String toString() {
        return "AbstractExpression{" +
                "cacheEvalRst=" + cacheEvalRst +
                ", evaluated=" + evaluated +
                ", evaluationCache=" + evaluationCache +
                ", originSQL='" + originSQL + '\'' +
                '}';
    }
}
