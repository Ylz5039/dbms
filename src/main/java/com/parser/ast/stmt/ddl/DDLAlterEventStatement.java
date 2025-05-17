package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;

public class DDLAlterEventStatement implements DDLStatement {
    private final Expression definer;

    private final Identifier eventName;


    public DDLAlterEventStatement(Expression definer, Identifier eventName) {
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
        sb.append("DDLAlterEventStatement {\n")
                .append("    definer=").append(definer).append("\n")
                .append("    eventName=").append(eventName).append("\n")
                .append("}");
        return sb.toString();
    }

}
