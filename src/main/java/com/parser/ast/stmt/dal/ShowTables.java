package com.parser.ast.stmt.dal;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;

public class ShowTables extends DALShowStatement {
    private final boolean full;
    private Identifier schema;
    private final String pattern;
    private final Expression where;

    public ShowTables(boolean full, Identifier schema, String pattern) {
        this.full = full;
        this.schema = schema;
        this.pattern = pattern;
        this.where = null;
    }

    public ShowTables(boolean full, Identifier schema, Expression where) {
        this.full = full;
        this.schema = schema;
        this.pattern = null;
        this.where = where;
    }

    public ShowTables(boolean full, Identifier schema) {
        this.full = full;
        this.schema = schema;
        this.pattern = null;
        this.where = null;
    }

    public boolean isFull() {
        return full;
    }

    public void setSchema(Identifier schema) {
        this.schema = schema;
    }

    public Identifier getSchema() {
        return schema;
    }

    public String getPattern() {
        return pattern;
    }

    public Expression getWhere() {
        return where;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowTables {\n")
                .append("    full=").append(full).append("\n")
                .append("    schema=").append(schema).append("\n")
                .append("    pattern='").append(pattern).append("'\n")
                .append("    where=").append(where).append("\n")
                .append("}");
        return sb.toString();
    }

}
