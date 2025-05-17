package com.parser.ast.stmt.dml;

import com.parser.ast.expression.misc.QueryExpression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.expression.primary.RowExpression;

import java.util.List;

public class DMLReplaceStatement extends DMLInsertReplaceStatement {
    public enum ReplaceMode {
        UNDEF, LOW, DELAY
    }

    private final ReplaceMode mode;

    public DMLReplaceStatement(ReplaceMode mode, Identifier table, List<Identifier> columnNameList,
                               List<RowExpression> rowList) {
        super(table, columnNameList, rowList);
        this.mode = mode;
    }

    public DMLReplaceStatement(ReplaceMode mode, Identifier table, List<Identifier> columnNameList,
                               QueryExpression select) {
        super(table, columnNameList, select);
        this.mode = mode;
    }

    public ReplaceMode getMode() {
        return mode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DMLReplaceStatement {\n")
                .append("    mode=").append(mode).append("\n")
                .append("    table=").append(getTable()).append("\n")
                .append("    columnNameList=").append(getColumnNameList()).append("\n")
                .append("    rowList=").append(getRowList()).append("\n")
                .append("    select=").append(getSelect()).append("\n")
                .append("}");
        return sb.toString();
    }

}
