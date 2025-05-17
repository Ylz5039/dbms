package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.primary.Identifier;

public class DDLDropTriggerStatement implements DDLStatement {
    private final boolean ifExists;
    private final Identifier name;

    public DDLDropTriggerStatement(boolean ifExists, Identifier name) {
        this.ifExists = ifExists;
        this.name = name;
    }

    public boolean isIfExists() {
        return ifExists;
    }

    public Identifier getName() {
        return name;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLDropTriggerStatement {\n")
                .append("    ifExists=").append(ifExists).append("\n")
                .append("    name=").append(name).append("\n")
                .append("}");
        return sb.toString();
    }

}
