package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.SQLStatement;

public class DescTableStatement implements SQLStatement {
    private final Identifier table;

    public DescTableStatement(Identifier table) {
        if (table == null)
            throw new IllegalArgumentException("table is null for desc table");
        this.table = table;
    }

    public Identifier getTable() {
        return table;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DescTableStatement {\n")
                .append("    table=").append(table).append("\n")
                .append("}");
        return sb.toString();
    }

}
