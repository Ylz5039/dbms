package com.parser.ast.stmt.dal;

import com.parser.ast.expression.Expression;

public class ShowDatabases extends DALShowStatement {
    private final String pattern;
    private final Expression where;

    public ShowDatabases(String pattern) {
        super();
        this.pattern = pattern;
        this.where = null;
    }

    public ShowDatabases(Expression where) {
        super();
        this.pattern = null;
        this.where = where;
    }

    public ShowDatabases() {
        super();
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
        sb.append("ShowDatabases {\n")
                .append("    pattern='").append(pattern != null ? pattern : "null").append("',\n")
                .append("    where=").append(where != null ? where.toString() : "null").append("\n")
                .append("}");
        return sb.toString();
    }

}
