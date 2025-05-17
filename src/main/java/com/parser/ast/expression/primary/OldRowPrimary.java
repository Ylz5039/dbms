package com.parser.ast.expression.primary;

public class OldRowPrimary extends VariableExpression {
    private final String varText;

    public OldRowPrimary(String varText) {
        this.varText = varText;
    }

    public String getVarText() {
        return varText;
    }

    public String toString() {
        return "OldRowPrimary{" +
                "varText='" + varText + '\'' +
                '}';
    }
}
