package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.fragment.DatabaseOptions;

import java.util.List;

public class DDLCreateDatabaseStatement implements DDLStatement {
    private final boolean ifNotExists;

    private final Identifier databaseName;

    private final List<DatabaseOptions> options;

    public DDLCreateDatabaseStatement(boolean ifNotExists, Identifier databaseName, List<DatabaseOptions> options) {
        this.ifNotExists = ifNotExists;
        this.databaseName = databaseName;
        this.options = options;
    }

    public boolean isIfNotExists() {
        return ifNotExists;
    }

    public Identifier getDatabaseName() {
        return databaseName;
    }

    public List<DatabaseOptions> getOptions() {
        return options;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLCreateDatabaseStatement {\n")
                .append("    ifNotExists=").append(ifNotExists).append("\n")
                .append("    databaseName=").append(databaseName).append("\n")
                .append("    options=").append(options).append("\n")
                .append("}");
        return sb.toString();
    }

}
