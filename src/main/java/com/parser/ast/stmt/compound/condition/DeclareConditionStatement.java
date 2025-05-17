package com.parser.ast.stmt.compound.condition;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.compound.CompoundStatement;

public class DeclareConditionStatement implements CompoundStatement {
    private final Identifier name;
    private final ConditionValue value;

    public DeclareConditionStatement(Identifier name, ConditionValue value) {
        this.name = name;
        this.value = value;
    }

    public Identifier getName() {
        return name;
    }

    public ConditionValue getValue() {
        return value;
    }

    public String toString() {
        return "DeclareConditionStatement{" +
                "name=" + name +
                ", value=" + value +
                '}';
    }
}
