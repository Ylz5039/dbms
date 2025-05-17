package com.parser.ast.stmt.dal;

import com.parser.ast.expression.primary.Identifier;

public class ShowCreateDatabase extends DALShowStatement {
    public static enum Type {
        DATABASE, SCHEMA
    }

    private final Type type;
    private final boolean ifNotExists;
    private final Identifier dbName;

    public ShowCreateDatabase(Type type, boolean ifNotExists, Identifier dbName) {
        this.type = type;
        this.ifNotExists = ifNotExists;
        this.dbName = dbName;
    }


    public boolean isIfNotExists() {
        return ifNotExists;
    }


    public Identifier getDbName() {
        return dbName;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowCreateDatabase {\n")
                .append("    type=").append(type).append(",\n")
                .append("    ifNotExists=").append(ifNotExists).append(",\n")
                .append("    dbName=").append(dbName != null ? dbName.toString() : "null").append("\n")
                .append("}");
        return sb.toString();
    }

}
