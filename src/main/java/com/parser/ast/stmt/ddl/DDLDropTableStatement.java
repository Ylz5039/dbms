package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.primary.Identifier;

import java.util.Collections;
import java.util.List;

public class DDLDropTableStatement implements DDLStatement {
    public enum Mode {
        UNDEF, RESTRICT, CASCADE
    }

    private final List<Identifier> tableNames;
    private final boolean temp;
    private final boolean ifExists;
    private final Mode mode;

    public DDLDropTableStatement(List<Identifier> tableNames, boolean temp, boolean ifExists) {
        this(tableNames, temp, ifExists, Mode.UNDEF);
    }

    public DDLDropTableStatement(List<Identifier> tableNames, boolean temp, boolean ifExists,
                                 Mode mode) {
        if (tableNames == null || tableNames.isEmpty()) {
            this.tableNames = Collections.emptyList();
        } else {
            this.tableNames = tableNames;
        }
        this.temp = temp;
        this.ifExists = ifExists;
        this.mode = mode;
    }

    public List<Identifier> getTableNames() {
        return tableNames;
    }

    public boolean isTemp() {
        return temp;
    }

    public boolean isIfExists() {
        return ifExists;
    }

    public Mode getMode() {
        return mode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLDropTableStatement {\n")
                .append("    tableNames=").append(tableNames).append("\n")
                .append("    temp=").append(temp).append("\n")
                .append("    ifExists=").append(ifExists).append("\n")
                .append("    mode=").append(mode).append("\n")
                .append("}");
        return sb.toString();
    }

}
