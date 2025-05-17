package com.parser.ast.stmt.dml;

import com.parser.ast.expression.misc.QueryExpression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.expression.primary.RowExpression;

import java.util.List;

public abstract class DMLInsertReplaceStatement extends DMLStatement {
    protected final Identifier table;

    protected final List<Identifier> columnNameList;

    protected List<RowExpression> rowList;

    protected final QueryExpression select;

    private List<RowExpression> rowListBak;

    public DMLInsertReplaceStatement(Identifier table, List<Identifier> columnNameList,
                                     List<RowExpression> rowList) {
        this.table = table;
        this.columnNameList = ensureListType(columnNameList);
        this.rowList = ensureListType(rowList);
        this.select = null;
    }

    public DMLInsertReplaceStatement(Identifier table, List<Identifier> columnNameList,
                                     QueryExpression select) {
        if (select == null)
            throw new IllegalArgumentException("argument 'select' is empty");
        this.select = select;
        this.table = table;
        this.columnNameList = ensureListType(columnNameList);
        this.rowList = null;
    }

    public Identifier getTable() {
        return table;
    }

    public List<Identifier> getColumnNameList() {
        return columnNameList;
    }

    public List<RowExpression> getRowList() {
        return rowList;
    }

    public QueryExpression getSelect() {
        return select;
    }

    public void setReplaceRowList(List<RowExpression> list) {
        rowListBak = rowList;
        rowList = list;
    }

    public void clearReplaceRowList() {
        if (rowListBak != null) {
            rowList = rowListBak;
            rowListBak = null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DMLInsertReplaceStatement {\n")
                .append("    table=").append(table).append("\n")
                .append("    columnNameList=").append(columnNameList).append("\n")
                .append("    rowList=").append(rowList).append("\n")
                .append("    select=").append(select).append("\n")
                .append("    rowListBak=").append(rowListBak).append("\n")
                .append("}");
        return sb.toString();
    }

}
