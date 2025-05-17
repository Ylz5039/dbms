package com.parser.ast.fragment.tableref;

public class Dual implements TableReference {
    public Object removeLastConditionElement() {
        return null;
    }

    public boolean isSingleTable() {
        return true;
    }

    public int getPrecedence() {
        return PRECEDENCE_FACTOR;
    }

    public String toString() {
        return "Dual{}";
    }
}
