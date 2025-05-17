package com.parser.ast.stmt.compound.condition;

import com.parser.ast.expression.primary.literal.Literal;
import com.parser.ast.stmt.compound.CompoundStatement;
import com.parser.ast.stmt.compound.condition.SignalStatement.ConditionInfoItemName;
import com.parser.util.Pair;

import java.util.List;

public class ResignalStatement implements CompoundStatement {
    private final ConditionValue conditionValue;
    private final List<Pair<ConditionInfoItemName, Literal>> informationItems;

    public ResignalStatement(ConditionValue conditionValue,
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
        return "ResignalStatement{" +
                "conditionValue=" + conditionValue +
                ", informationItems=" + informationItems +
                '}';
    }
}
