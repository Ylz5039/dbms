package com.parser.ast.stmt.dal;

import com.parser.ast.expression.Expression;

public class ShowCollation extends DALShowStatement {
    private final String pattern;
    private final Expression where;

    public ShowCollation(Expression where) {
        this.pattern = null;
        this.where = where;
    }

    public ShowCollation(String pattern) {
        this.pattern = pattern;
        this.where = null;
    }

    public ShowCollation() {
        this.pattern = null;
        this.where = null;
    }

    public String getPattern() {
        return pattern;
    }

    public Expression getWhere() {
        return where;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowCollation {\n")
                .append("    pattern='").append(pattern != null ? pattern : "null").append("',\n")
                .append("    where=").append(where != null ? where.toString() : "null").append("\n")
                .append("}");
        return sb.toString();
    }
}
