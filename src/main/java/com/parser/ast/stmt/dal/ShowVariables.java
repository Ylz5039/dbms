package com.parser.ast.stmt.dal;

import com.parser.ast.expression.Expression;
import com.parser.ast.fragment.VariableScope;

public class ShowVariables extends DALShowStatement {
    private final VariableScope scope;
    private final String pattern;
    private final Expression where;

    public ShowVariables(VariableScope scope, String pattern) {
        this.scope = scope;
        this.pattern = pattern;
        this.where = null;
    }

    public ShowVariables(VariableScope scope, Expression where) {
        this.scope = scope;
        this.pattern = null;
        this.where = where;
    }

    public ShowVariables(VariableScope scope) {
        this.scope = scope;
        this.pattern = null;
        this.where = null;
    }

    public VariableScope getScope() {
        return scope;
    }

    public String getPattern() {
        return pattern;
    }

    public Expression getWhere() {
        return where;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowVariables {\n")
                .append("    scope=").append(scope).append("\n")
                .append("    pattern='").append(pattern).append("'\n")
                .append("    where=").append(where).append("\n")
                .append("}");
        return sb.toString();
    }

}
