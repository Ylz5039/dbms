package com.parser.ast.expression.primary;

import com.parser.ast.fragment.VariableScope;

public class SysVarPrimary extends VariableExpression {
    private final VariableScope scope;
    private final String scopeStr;
    private final String varText;
    private final String varTextUp;

    public SysVarPrimary(VariableScope scope, String scopeStr, String varText, String varTextUp) {
        this.scope = scope;
        this.scopeStr = scopeStr;
        this.varText = varText;
        this.varTextUp = varTextUp;
    }

    public VariableScope getScope() {
        return scope;
    }

    public String getScopeStr() {
        return scopeStr;
    }

    public String getVarText() {
        return varText;
    }

    public String getVarTextUp() {
        return varTextUp;
    }

    public String toString() {
        return "SysVarPrimary{" +
                "scope=" + scope +
                ", scopeStr='" + scopeStr + '\'' +
                ", varText='" + varText + '\'' +
                ", varTextUp='" + varTextUp + '\'' +
                '}';
    }
}
