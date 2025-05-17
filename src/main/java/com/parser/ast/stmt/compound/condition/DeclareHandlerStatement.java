package com.parser.ast.stmt.compound.condition;

import com.parser.ast.stmt.SQLStatement;
import com.parser.ast.stmt.compound.CompoundStatement;

import java.util.List;

public class DeclareHandlerStatement implements CompoundStatement {
    public enum HandlerAction {
        CONTINUE, EXIT, UNDO
    }

    private final HandlerAction action;
    private final List<ConditionValue> conditionValues;
    private final SQLStatement stmt;


    public DeclareHandlerStatement(HandlerAction action, List<ConditionValue> conditionValues,
                                   SQLStatement stmt) {
        this.action = action;
        this.conditionValues = conditionValues;
        this.stmt = stmt;
    }

    public HandlerAction getAction() {
        return action;
    }

    public List<ConditionValue> getConditionValues() {
        return conditionValues;
    }

    public SQLStatement getStmt() {
        return stmt;
    }

    public String toString() {
        return "DeclareHandlerStatement{" +
                "action=" + action +
                ", conditionValues=" + conditionValues +
                ", stmt=" + stmt +
                '}';
    }
}
