package com.parser.ast.stmt.dal;

import com.parser.ast.stmt.SQLStatement;

public class DALSetNamesStatement implements SQLStatement {
    private final String charsetName;
    private final String collationName;

    public DALSetNamesStatement() {
        this.charsetName = null;
        this.collationName = null;
    }

    public DALSetNamesStatement(String charsetName, String collationName) {
        if (charsetName == null)
            throw new IllegalArgumentException("charsetName is null");
        this.charsetName = charsetName;
        this.collationName = collationName;
    }

    public boolean isDefault() {
        return charsetName == null;
    }

    public String getCharsetName() {
        return charsetName;
    }

    public String getCollationName() {
        return collationName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DALSetNamesStatement {\n")
                .append("    charsetName=").append(isDefault() ? "default" : charsetName)
                .append("\n    collationName=").append(collationName != null ? collationName : "default")
                .append("\n}");
        return sb.toString();
    }
}
