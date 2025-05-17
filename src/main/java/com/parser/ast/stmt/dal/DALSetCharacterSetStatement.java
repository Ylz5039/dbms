package com.parser.ast.stmt.dal;

import com.parser.ast.stmt.SQLStatement;

public class DALSetCharacterSetStatement implements SQLStatement {
    private final String charset;

    public DALSetCharacterSetStatement() {
        this.charset = null;
    }

    public DALSetCharacterSetStatement(String charset) {
        if (charset == null)
            throw new IllegalArgumentException("charsetName is null");
        this.charset = charset;
    }

    public boolean isDefault() {
        return charset == null;
    }

    public String getCharset() {
        return charset;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DALSetCharacterSetStatement {\n")
                .append("    charset=").append(isDefault() ? "default" : charset)
                .append("\n}");
        return sb.toString();
    }
}
