package com.parser.ast.fragment.tableref;

import com.parser.ast.expression.misc.QueryExpression;

public class SubqueryFactor extends AliasableTableReference {
    private final QueryExpression subquery;

    public SubqueryFactor(QueryExpression subquery, String alias) {
        super(alias);
        if (alias == null)
            throw new IllegalArgumentException("alias is required for subquery factor");
        if (subquery == null)
            throw new IllegalArgumentException("subquery is null");
        this.subquery = subquery;
    }

    public QueryExpression getSubquery() {
        return subquery;
    }

    public Object removeLastConditionElement() {
        return null;
    }

    public boolean isSingleTable() {
        return false;
    }

    public int getPrecedence() {
        return PRECEDENCE_FACTOR;
    }

    public String toString() {
        return "SubqueryFactor{" +
                "subquery=" + subquery +
                '}';
    }
}
