package com.parser.ast.expression.primary;

public class UsrDefVarPrimary extends VariableExpression {
    private final String varText;

    public UsrDefVarPrimary(String varText) {
        this.varText = varText;
    }

    public String getVarText() {
        return varText;
    }

    public String toString() {
        return "UsrDefVarPrimary{" +
                "varText=" + varText + '\'' +
                '}';
    }
}
