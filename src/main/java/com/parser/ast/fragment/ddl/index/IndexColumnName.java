package com.parser.ast.fragment.ddl.index;

import com.parser.ast.ASTNode;
import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;

public class IndexColumnName implements ASTNode {
    private final Identifier columnName;
    private final Expression length;
    private final boolean asc;

    public IndexColumnName(Identifier columnName, Expression length, boolean asc) {
        this.columnName = columnName;
        this.length = length;
        this.asc = asc;
    }

    public Identifier getColumnName() {
        return columnName;
    }

    public Expression getLength() {
        return length;
    }

    public boolean isAsc() {
        return asc;
    }

    public String toString() {
        return "IndexColumnName{" +
                "columnName=" + columnName +
                ", length=" + length +
                ", asc=" + asc +
                '}';
    }
}
