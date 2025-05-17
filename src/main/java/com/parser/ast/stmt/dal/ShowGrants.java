package com.parser.ast.stmt.dal;

import com.parser.ast.expression.Expression;

public class ShowGrants extends DALShowStatement {
    private final Expression user;

    public ShowGrants(Expression user) {
        this.user = user;
    }

    public ShowGrants() {
        this.user = null;
    }

    public Expression getUser() {
        return user;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowGrants {\n")
                .append("    user=").append(user != null ? user.toString() : "null").append("\n")
                .append("}");
        return sb.toString();
    }

}
