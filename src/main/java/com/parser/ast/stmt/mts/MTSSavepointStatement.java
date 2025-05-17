package com.parser.ast.stmt.mts;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.SQLStatement;

public class MTSSavepointStatement implements SQLStatement {
    private final Identifier savepoint;

    public MTSSavepointStatement(Identifier savepoint) {
        if (savepoint == null)
            throw new IllegalArgumentException("savepoint is null");
        this.savepoint = savepoint;
    }

    public Identifier getSavepoint() {
        return savepoint;
    }

}
