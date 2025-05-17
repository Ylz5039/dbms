package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.primary.Identifier;

public class DDLCreateLikeStatement extends DDLCreateTableStatement implements DDLStatement {
    private final Identifier likeTable;
    private String createTableSql;

    public DDLCreateLikeStatement(boolean temporary, boolean ifNotExists, Identifier table,
                                  Identifier likeTable) {
        super(temporary, ifNotExists, table);
        this.likeTable = likeTable;
    }

    public Identifier getLikeTable() {
        return likeTable;
    }

    public String getCreateTableSql() {
        return createTableSql;
    }

    public void setCreateTableSql(String createTableSql) {
        this.createTableSql = createTableSql;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLCreateLikeStatement {\n")
                .append("    temporary=").append(isTemporary()).append("\n")
                .append("    ifNotExists=").append(isIfNotExists()).append("\n")
                .append("    table=").append(getTable()).append("\n")
                .append("    likeTable=").append(likeTable).append("\n")
                .append("    createTableSql=").append(createTableSql).append("\n")
                .append("}");
        return sb.toString();
    }

}
