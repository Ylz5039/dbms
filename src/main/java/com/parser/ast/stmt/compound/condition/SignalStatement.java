package com.parser.ast.stmt.compound.condition;

import com.parser.ast.expression.primary.literal.Literal;
import com.parser.ast.stmt.compound.CompoundStatement;
import com.parser.util.Pair;

import java.util.List;

public class SignalStatement implements CompoundStatement {
    public enum ConditionInfoItemName {
        CLASS_ORIGIN, SUBCLASS_ORIGIN, MESSAGE_TEXT, MYSQL_ERRNO, CONSTRAINT_CATALOG, CONSTRAINT_SCHEMA, CONSTRAINT_NAME, CATALOG_NAME, SCHEMA_NAME, TABLE_NAME, COLUMN_NAME, CURSOR_NAME, RETURNED_SQLSTATE
    }

    private final ConditionValue conditionValue;
    private final List<Pair<ConditionInfoItemName, Literal>> informationItems;

    public SignalStatement(ConditionValue conditionValue,
                           List<Pair<ConditionInfoItemName, Literal>> informationItems) {
        super();
        this.conditionValue = conditionValue;
        this.informationItems = informationItems;
    }

    public ConditionValue getConditionValue() {
        return conditionValue;
    }

    public List<Pair<ConditionInfoItemName, Literal>> getInformationItems() {
        return informationItems;
    }

    public String toString() {
        return "SignalStatement{" +
                "conditionValue=" + conditionValue +
                ", informationItems=" + informationItems +
                '}';
    }
}
