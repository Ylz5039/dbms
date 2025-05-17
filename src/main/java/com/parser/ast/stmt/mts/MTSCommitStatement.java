package com.parser.ast.stmt.mts;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.SQLStatement;

public class MTSCommitStatement implements SQLStatement {
    public static enum CompleteType {
        UN_DEF, CHAIN,
        NO_CHAIN, RELEASE, NO_RELEASE
    }

    private final CompleteType completeType;

    public MTSCommitStatement(CompleteType completeType) {
        if (completeType == null)
            throw new IllegalArgumentException("complete type is null!");
        this.completeType = completeType;
    }

    public MTSCommitStatement(Identifier savepoint) {
        this.completeType = null;
        if (savepoint == null)
            throw new IllegalArgumentException("savepoint is null!");
    }

    public CompleteType getCompleteType() {
        return completeType;
    }
}
