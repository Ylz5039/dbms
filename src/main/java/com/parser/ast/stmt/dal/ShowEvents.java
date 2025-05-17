package com.parser.ast.stmt.dal;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;

public class ShowEvents extends DALShowStatement {
    private Identifier schema;
    private final String pattern;
    private final Expression where;

    public ShowEvents(Identifier schema, String pattern) {
        this.schema = schema;
        this.pattern = pattern;
        this.where = null;
    }

    public ShowEvents(Identifier schema, Expression where) {
        this.schema = schema;
        this.pattern = null;
        this.where = where;
    }

    public ShowEvents(Identifier schema) {
        this.schema = schema;
        this.pattern = null;
        this.where = null;
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
        sb.append("ShowEvents {\n")
                .append("    schema=").append(schema != null ? schema.toString() : "null").append(",\n")
                .append("    pattern='").append(pattern != null ? pattern : "null").append("',\n")
                .append("    where=").append(where != null ? where.toString() : "null").append("\n")
                .append("}");
        return sb.toString();
    }

}
