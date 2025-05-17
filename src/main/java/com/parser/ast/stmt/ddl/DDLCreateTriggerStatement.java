package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.SQLStatement;

public class DDLCreateTriggerStatement implements DDLStatement {

    public enum TriggerTime {
        BEFORE, AFTER
    }
    public enum TriggerEvent {
        INSERT, UPDATE, DELETE
    }

    public enum TriggerOrder {
        FOLLOWS, PRECEDES
    }

    private Expression definer;
    private final Identifier triggerName;
    private final TriggerTime triggerTime;
    private final TriggerEvent triggerEvent;
    private final Identifier table;
    private final TriggerOrder triggerOrder;
    private final Identifier otherTriggerName;
    private final SQLStatement stmt;

    public DDLCreateTriggerStatement(Expression definer, Identifier triggerName,
                                     TriggerTime triggerTime, TriggerEvent triggerEvent, Identifier table,
                                     TriggerOrder triggerOrder, Identifier otherTriggerName, SQLStatement stmt) {
        super();
        this.definer = definer;
        this.triggerName = triggerName;
        this.triggerTime = triggerTime;
        this.triggerEvent = triggerEvent;
        this.table = table;
        this.triggerOrder = triggerOrder;
        this.otherTriggerName = otherTriggerName;
        this.stmt = stmt;
    }

    public Expression getDefiner() {
        return definer;
    }

    public void setDefiner(Expression definer) {
        this.definer = definer;
    }

    public Identifier getTriggerName() {
        return triggerName;
    }

    public TriggerTime getTriggerTime() {
        return triggerTime;
    }

    public TriggerEvent getTriggerEvent() {
        return triggerEvent;
    }

    public Identifier getTable() {
        return table;
    }

    public TriggerOrder getTriggerOrder() {
        return triggerOrder;
    }

    public Identifier getOtherTriggerName() {
        return otherTriggerName;
    }

    public SQLStatement getStmt() {
        return stmt;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLCreateTriggerStatement {\n")
                .append("    definer=").append(definer).append("\n")
                .append("    triggerName=").append(triggerName).append("\n")
                .append("    triggerTime=").append(triggerTime).append("\n")
                .append("    triggerEvent=").append(triggerEvent).append("\n")
                .append("    table=").append(table).append("\n")
                .append("    triggerOrder=").append(triggerOrder).append("\n")
                .append("    otherTriggerName=").append(otherTriggerName).append("\n")
                .append("    stmt=").append(stmt).append("\n")
                .append("}");
        return sb.toString();
    }

}
