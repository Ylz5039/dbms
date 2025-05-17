package com.parser.ast.stmt.extension;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.stmt.ddl.DDLStatement;

public class ExtDDLDropPolicy implements DDLStatement {
    private final Identifier policyName;

    public ExtDDLDropPolicy(Identifier policyName) {
        this.policyName = policyName;
    }

    public Identifier getPolicyName() {
        return policyName;
    }
}
