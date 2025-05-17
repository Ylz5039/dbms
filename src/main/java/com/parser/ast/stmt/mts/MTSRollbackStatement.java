package com.parser.ast.stmt.mts;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.SQLStatement;

public class MTSRollbackStatement implements SQLStatement {
    public static enum CompleteType {
        UN_DEF, CHAIN,
        NO_CHAIN, RELEASE, NO_RELEASE
    }

    private final CompleteType completeType;
    private final Identifier savepoint;

    public MTSRollbackStatement(CompleteType completeType) {
        if (completeType == null)
            throw new IllegalArgumentException("complete type is null!");
        this.completeType = completeType;
        this.savepoint = null;
    }

    public MTSRollbackStatement(Identifier savepoint) {
        this.completeType = null;
        if (savepoint == null)
            throw new IllegalArgumentException("savepoint is null!");
        this.savepoint = savepoint;
    }

    public CompleteType getCompleteType() {
        return completeType;
    }

    public Identifier getSavepoint() {
        return savepoint;
    }
}
