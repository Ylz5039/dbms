package com.parser.ast.stmt.extension;

import com.parser.ast.stmt.SQLStatement;

public class PrepareStatement implements SQLStatement {
    private final String name;
    private final String stmt;
    private String[] sqls;

    public PrepareStatement(String stmt_name, String preparable_stmt) {
        this.name = stmt_name;
        this.stmt = preparable_stmt;
        this.sqls = stmt.split("\\?");
    }

    public String getStmt() {
        return stmt;
    }

    public String getName() {
        return name;
    }

    public String[] getSqls() {
        return sqls;
    }
}
