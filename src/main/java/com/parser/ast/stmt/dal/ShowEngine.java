package com.parser.ast.stmt.dal;

public class ShowEngine extends DALShowStatement {
    public static enum Type {
        INNODB_STATUS, INNODB_MUTEX, PERFORMANCE_SCHEMA_STATUS
    }

    private final Type type;

    public ShowEngine(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowEngine {\n")
                .append("    type=").append(type).append("\n")
                .append("}");
        return sb.toString();
    }

}
