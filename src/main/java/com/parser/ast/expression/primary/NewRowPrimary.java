package com.parser.ast.expression.primary;

public class NewRowPrimary extends VariableExpression {
    private final String varText;

    public NewRowPrimary(String varText) {
        this.varText = varText;
    }

    public String getVarText() {
        return varText;
    }

    public String toString() {
        return "NewRowPrimary{" +
                "varText='" + varText + '\'' +
                '}';
    }
}
