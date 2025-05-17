package com.parser.ast.stmt.dal;

import com.parser.ast.fragment.Limit;

public class ShowErrors extends DALShowStatement {
    private final boolean count;
    private final Limit limit;

    public ShowErrors(boolean count, Limit limit) {
        this.count = count;
        this.limit = limit;
    }

    public boolean isCount() {
        return count;
    }

    public Limit getLimit() {
        return limit;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowErrors {\n")
                .append("    count=").append(count).append(",\n")
                .append("    limit=").append(limit != null ? limit.toString() : "null").append("\n")
                .append("}");
        return sb.toString();
    }

}
