package com.parser.ast.stmt.mts;

import com.parser.ast.fragment.VariableScope;
import com.parser.ast.stmt.SQLStatement;

public class MTSSetTransactionStatement implements SQLStatement {
    public static enum IsolationLevel {
        READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE
    }

    private final VariableScope scope;
    private final IsolationLevel level;

    public MTSSetTransactionStatement(VariableScope scope, IsolationLevel level) {
        super();
        if (level == null)
            throw new IllegalArgumentException("isolation level is null");
        this.level = level;
        this.scope = scope;
    }

    public VariableScope getScope() {
        return scope;
    }

    public IsolationLevel getLevel() {
        return level;
    }
}
