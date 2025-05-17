package com.parser.ast.stmt.dal;

import com.parser.ast.expression.primary.Identifier;

public class ShowCreate extends DALShowStatement {
    public static enum Type {
        DATABASE, EVENT, FUNCTION, PROCEDURE, TABLE, TRIGGER, VIEW, USER
    }

    private final Type type;
    private final Identifier id;

    public ShowCreate(Type type, Identifier id) {
        this.type = type;
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public Identifier getId() {
        return id;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowCreate {\n")
                .append("    type=").append(type).append(",\n")
                .append("    id=").append(id != null ? id.toString() : "null").append("\n")
                .append("}");
        return sb.toString();
    }

}
