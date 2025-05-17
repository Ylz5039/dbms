package com.parser.ast.expression.primary;

import com.parser.ast.expression.misc.QueryExpression;

public class ExistsPrimary extends PrimaryExpression {
    private QueryExpression subquery;

    public ExistsPrimary(QueryExpression subquery) {
        if (subquery == null)
            throw new IllegalArgumentException("subquery is null for EXISTS expression");
        this.subquery = subquery;
    }

    public QueryExpression getSubquery() {
        return subquery;
    }

    public void setSubquery(QueryExpression exp) {
        this.subquery = exp;
    }

    public String toString() {
        return "ExistsPrimary{" +
                "subquery=" + subquery +
                '}';
    }
}
