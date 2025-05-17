package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;

public class DDLCreateEventStatement implements DDLStatement {
    private final Expression definer;

    private final Identifier eventName;

    public DDLCreateEventStatement(Expression definer, Identifier eventName) {
        super();
        this.definer = definer;
        this.eventName = eventName;
    }

    public Expression getDefiner() {
        return definer;
    }

    public Identifier getEventName() {
        return eventName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLCreateEventStatement {\n")
                .append("    definer=").append(definer).append("\n")
                .append("    eventName=").append(eventName).append("\n")
                .append("}");
        return sb.toString();
    }

}
