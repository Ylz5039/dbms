package com.parser.ast.stmt.dml;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.fragment.Limit;
import com.parser.ast.fragment.OrderBy;
import com.parser.ast.fragment.tableref.TableReferences;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

public class DMLDeleteStatement extends DMLStatement {
    private final boolean lowPriority;
    private final boolean quick;
    private final boolean ignore;
    private final List<Identifier> tableNames;
    private final TableReferences tableRefs;
    private final Expression whereCondition;
    private final OrderBy orderBy;
    private final Limit limit;

    public DMLDeleteStatement(boolean lowPriority, boolean quick, boolean ignore,
                              Identifier tableName) throws SQLSyntaxErrorException {
        this(lowPriority, quick, ignore, tableName, null, null, null);
    }

    public DMLDeleteStatement(boolean lowPriority, boolean quick, boolean ignore,
                              Identifier tableName, Expression where) throws SQLSyntaxErrorException {
        this(lowPriority, quick, ignore, tableName, where, null, null);
    }

    public DMLDeleteStatement(boolean lowPriority, boolean quick, boolean ignore,
                              Identifier tableName, Expression where, OrderBy orderBy, Limit limit)
            throws SQLSyntaxErrorException {
        this.lowPriority = lowPriority;
        this.quick = quick;
        this.ignore = ignore;
        this.tableNames = new ArrayList<Identifier>(1);
        this.tableNames.add(tableName);
        this.tableRefs = null;
        this.whereCondition = where;
        this.orderBy = orderBy;
        this.limit = limit;
    }

    public DMLDeleteStatement(boolean lowPriority, boolean quick, boolean ignore,
                              List<Identifier> tableNameList, TableReferences tableRefs)
            throws SQLSyntaxErrorException {
        this(lowPriority, quick, ignore, tableNameList, tableRefs, null);
    }

    public DMLDeleteStatement(boolean lowPriority, boolean quick, boolean ignore,
                              List<Identifier> tableNameList, TableReferences tableRefs, Expression whereCondition)
            throws SQLSyntaxErrorException {
        this.lowPriority = lowPriority;
        this.quick = quick;
        this.ignore = ignore;
        if (tableNameList == null || tableNameList.isEmpty()) {
            throw new IllegalArgumentException("argument 'tableNameList' is empty");
        } else if (tableNameList instanceof ArrayList) {
            this.tableNames = tableNameList;
        } else {
            this.tableNames = new ArrayList<Identifier>(tableNameList);
        }
        if (tableRefs == null) {
            throw new IllegalArgumentException("argument 'tableRefs' is null");
        }
        this.tableRefs = tableRefs;
        this.whereCondition = whereCondition;
        this.orderBy = null;
        this.limit = null;
    }

    public boolean isLowPriority() {
        return lowPriority;
    }

    public boolean isQuick() {
        return quick;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public List<Identifier> getTableNames() {
        return tableNames;
    }

    public TableReferences getTableRefs() {
        return tableRefs;
    }

    public Expression getWhereCondition() {
        return whereCondition;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public Limit getLimit() {
        return limit;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DMLDeleteStatement {\n")
                .append("    lowPriority=").append(lowPriority).append("\n")
                .append("    quick=").append(quick).append("\n")
                .append("    ignore=").append(ignore).append("\n")
                .append("    tableNames=").append(tableNames).append("\n")
                .append("    tableRefs=").append(tableRefs).append("\n")
                .append("    whereCondition=").append(whereCondition).append("\n")
                .append("    orderBy=").append(orderBy).append("\n")
                .append("    limit=").append(limit).append("\n")
                .append("}");
        return sb.toString();
    }

}
