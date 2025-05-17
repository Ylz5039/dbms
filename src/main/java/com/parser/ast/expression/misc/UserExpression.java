package com.parser.ast.expression.misc;

import com.parser.ast.expression.primary.PrimaryExpression;

public class UserExpression extends PrimaryExpression {
    private final String userAtHost;

    public UserExpression(String userAtHost) {
        super();
        this.userAtHost = userAtHost;
    }

    public String getUserAtHost() {
        return userAtHost;
    }
}
