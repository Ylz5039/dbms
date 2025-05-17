package com.parser.ast.stmt.dal;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;

public class ShowIndex extends DALShowStatement {
    public static enum Type {
        INDEX, INDEXES, KEYS
    }

    private final Type type;
    private final Identifier table;
    private final Expression where;

    public ShowIndex(Type type, Identifier table, Identifier database, Expression where) {
        this.table = table;
        if (database != null) {
            this.table.setParent(database);
        }
        this.type = type;
        this.where = where;
    }

    public Type getType() {
        return type;
    }

    public Identifier getTable() {
        return table;
    }

    public Expression getWhere() {
        return where;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowIndex {\n")
                .append("    type=").append(type).append(",\n")
                .append("    table=").append(table != null ? table.toString() : "null").append(",\n")
                .append("    where=").append(where != null ? where.toString() : "null").append("\n")
                .append("}");
        return sb.toString();
    }

}
