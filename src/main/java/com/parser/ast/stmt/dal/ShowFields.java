package com.parser.ast.stmt.dal;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;

public class ShowFields extends DALShowStatement {
    private final boolean full;
    private final Identifier table;
    private final String pattern;
    private final Expression where;

    public ShowFields(boolean full, Identifier table, Identifier database, Expression where) {
        this.full = full;
        this.table = table;
        if (database != null) {
            this.table.setParent(database);
        }
        this.pattern = null;
        this.where = where;
    }

    public ShowFields(boolean full, Identifier table, Identifier database, String pattern) {
        this.full = full;
        this.table = table;
        if (database != null) {
            this.table.setParent(database);
        }
        this.pattern = pattern;
        this.where = null;
    }

    public ShowFields(boolean full, Identifier table, Identifier database) {
        this.full = full;
        this.table = table;
        if (database != null) {
            this.table.setParent(database);
        }
        this.pattern = null;
        this.where = null;
    }

    public boolean isFull() {
        return full;
    }

    public Identifier getTable() {
        return table;
    }

    public String getPattern() {
        return pattern;
    }

    public Expression getWhere() {
        return where;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowFields {\n")
                .append("    full=").append(full).append(",\n")
                .append("    table=").append(table != null ? table.toString() : "null").append(",\n")
                .append("    pattern='").append(pattern != null ? pattern : "null").append("',\n")
                .append("    where=").append(where != null ? where.toString() : "null").append("\n")
                .append("}");
        return sb.toString();
    }

}
