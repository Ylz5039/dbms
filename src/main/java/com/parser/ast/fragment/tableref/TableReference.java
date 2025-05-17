package com.parser.ast.fragment.tableref;

import com.parser.ast.ASTNode;

public interface TableReference extends ASTNode {
    int PRECEDENCE_REFS = 0;
    int PRECEDENCE_JOIN = 1;
    int PRECEDENCE_FACTOR = 2;

    Object removeLastConditionElement();

    public boolean isSingleTable();

    int getPrecedence();

}
