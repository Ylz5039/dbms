package com.parser.ast.stmt.extension;

import com.parser.ast.stmt.ddl.DDLStatement;

public class DropPrepareStatement implements DDLStatement {
    private final String preparedName;

    public DropPrepareStatement(String preparedName) {
        this.preparedName = preparedName;
    }

    public String getPreparedName() {
        return preparedName;
    }
}
