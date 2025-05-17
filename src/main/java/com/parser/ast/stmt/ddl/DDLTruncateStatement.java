package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.primary.Identifier;

public class DDLTruncateStatement implements DDLStatement {
    private final Identifier table;

    public DDLTruncateStatement(Identifier table) {
        this.table = table;
    }

    public Identifier getTable() {
        return table;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLTruncateStatement {\n")
                .append("    table=").append(table).append("\n")
                .append("}");
        return sb.toString();
    }

}
