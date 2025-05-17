package com.parser.ast.stmt.dml;

import com.parser.ast.fragment.Limit;
import com.parser.ast.fragment.OrderBy;

import java.util.LinkedList;
import java.util.List;

public class DMLSelectUnionStatement extends DMLQueryStatement {
    private final List<DMLSelectStatement> selectStmtList;

    private int firstDistinctIndex = 0;
    private OrderBy orderBy;
    private Limit limit;

    public DMLSelectUnionStatement(DMLSelectStatement select) {
        super();
        this.selectStmtList = new LinkedList<DMLSelectStatement>();
        this.selectStmtList.add(select);
    }

    public DMLSelectUnionStatement addSelect(DMLSelectStatement select, boolean unionAll) {
        selectStmtList.add(select);
        if (!unionAll) {
            firstDistinctIndex = selectStmtList.size() - 1;
        }
        return this;
    }

    public DMLSelectUnionStatement setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public DMLSelectUnionStatement setLimit(Limit limit) {
        this.limit = limit;
        return this;
    }

    public List<DMLSelectStatement> getSelectStmtList() {
        return selectStmtList;
    }

    public int getFirstDistinctIndex() {
        return firstDistinctIndex;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public Limit getLimit() {
        return limit;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DMLSelectUnionStatement {\n")
                .append("    selectStmtList=\n");

        for (DMLSelectStatement stmt : selectStmtList) {
            sb.append("        ").append(stmt.toString()).append("\n");
        }

        sb.append("    firstDistinctIndex=").append(firstDistinctIndex).append("\n")
                .append("    orderBy=").append(orderBy).append("\n")
                .append("    limit=").append(limit).append("\n")
                .append("}");
        return sb.toString();
    }

}
