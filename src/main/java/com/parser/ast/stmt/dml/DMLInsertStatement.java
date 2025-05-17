package com.parser.ast.stmt.dml;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.misc.QueryExpression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.expression.primary.RowExpression;
import com.parser.ast.fragment.ddl.ColumnDefinition;
import com.parser.util.Pair;

import java.util.List;

public class DMLInsertStatement extends DMLInsertReplaceStatement {

    public enum InsertMode {
        UNDEF, LOW, DELAY, HIGH
    }

    private final InsertMode mode;

    private final boolean ignore;

    private final List<Pair<Identifier, Expression>> duplicateUpdate;

    public DMLInsertStatement(InsertMode mode, boolean ignore, Identifier table,
                              List<Identifier> columnNameList, List<RowExpression> rowList,
                              List<Pair<Identifier, Expression>> duplicateUpdate) {
        super(table, columnNameList, rowList);
        this.mode = mode;
        this.ignore = ignore;
        this.duplicateUpdate = ensureListType(duplicateUpdate);
    }

    public DMLInsertStatement(InsertMode mode, boolean ignore, Identifier table,
                              List<Identifier> columnNameList, QueryExpression select,
                              List<Pair<Identifier, Expression>> duplicateUpdate) {
        super(table, columnNameList, select);
        this.mode = mode;
        this.ignore = ignore;
        this.duplicateUpdate = ensureListType(duplicateUpdate);
    }

    public InsertMode getMode() {
        return mode;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public List<Pair<Identifier, Expression>> getDuplicateUpdate() {
        return duplicateUpdate;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DMLInsertStatement {\n")
                .append("    mode=").append(mode).append("\n")
                .append("    ignore=").append(ignore).append("\n")
                .append("    table=").append(getTable()).append("\n")
                .append("    columnNameList=").append(getColumnNameList()).append("\n")
                .append("    rowList=").append(getRowList()).append("\n")
                .append("    select=").append(getSelect()).append("\n")
                .append("    duplicateUpdate=").append(duplicateUpdate).append("\n")
                .append("}");
        return sb.toString();
    }

}
