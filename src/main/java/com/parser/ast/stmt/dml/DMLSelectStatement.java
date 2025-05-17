package com.parser.ast.stmt.dml;

import com.parser.ast.expression.Expression;
import com.parser.ast.fragment.GroupBy;
import com.parser.ast.fragment.Limit;
import com.parser.ast.fragment.OrderBy;
import com.parser.ast.fragment.tableref.TableReferences;
import com.parser.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DMLSelectStatement extends DMLQueryStatement {
    public static enum SelectDuplicationStrategy {
        ALL, DISTINCT, DISTINCTROW
    }

    public static enum QueryCacheStrategy {
        UNDEF, SQL_CACHE, SQL_NO_CACHE
    }

    public static enum SmallOrBigResult {
        UNDEF, SQL_SMALL_RESULT, SQL_BIG_RESULT
    }

    public static enum LockMode {
        UNDEF, FOR_UPDATE, LOCK_IN_SHARE_MODE
    }

    public static final class SelectOption {
        public int version;// /*!40001 SQL_NO_CACHE */ * 指定最低版本号
        public SelectDuplicationStrategy resultDup = SelectDuplicationStrategy.ALL;
        public boolean highPriority = false;
        public boolean straightJoin = false;
        public SmallOrBigResult resultSize = SmallOrBigResult.UNDEF;
        public boolean sqlBufferResult = false;
        public QueryCacheStrategy queryCache = QueryCacheStrategy.UNDEF;
        public boolean sqlCalcFoundRows = false;
        public LockMode lockMode = LockMode.UNDEF;
        public boolean unknownOption = false;

        public void merge(SelectOption option) {
            if (version == 0) {
                version = option.version;
            }
            if (resultDup == SelectDuplicationStrategy.ALL) {
                resultDup = option.resultDup;
            }
            highPriority |= option.highPriority;
            sqlBufferResult |= option.sqlBufferResult;
            sqlCalcFoundRows |= option.sqlCalcFoundRows;
            unknownOption |= option.unknownOption;
            if (resultSize == SmallOrBigResult.UNDEF) {
                resultSize = option.resultSize;
            }
            if (queryCache == QueryCacheStrategy.UNDEF) {
                queryCache = option.queryCache;
            }
            if (lockMode == LockMode.UNDEF) {
                lockMode = option.lockMode;
            }
        }
    }

    private final SelectOption option;
    /** string: id | `id` | 'id' */
    private List<Pair<Expression, String>> selectExprList;
    private TableReferences tables;
    private Expression where;
    private GroupBy group;
    private Expression having;
    private OrderBy order;
    private Limit outermostLimit;
    private OrderBy outermostOrderBy;
    private Limit limit;

    @SuppressWarnings("unchecked")
    public DMLSelectStatement(SelectOption option, List<Pair<Expression, String>> selectExprList,
                              TableReferences tables, Expression where, GroupBy group, Expression having,
                              OrderBy order, Limit limit) {
        if (option == null)
            throw new IllegalArgumentException("argument 'option' is null");
        this.option = option;
        if (selectExprList == null || selectExprList.isEmpty()) {
            this.selectExprList = Collections.emptyList();
        } else {
            this.selectExprList = ensureListType(selectExprList);
        }
        this.tables = tables;
        this.where = where;
        this.group = group;
        this.having = having;
        this.order = order;
        this.limit = limit;
    }

    public SelectOption getOption() {
        return option;
    }

    public List<Pair<Expression, String>> getSelectExprList() {
        return selectExprList;
    }

    public List<Expression> getSelectExprListWithoutAlias() {
        if (selectExprList == null || selectExprList.isEmpty())
            return Collections.emptyList();
        List<Expression> list = new ArrayList<Expression>(selectExprList.size());
        for (Pair<Expression, String> p : selectExprList) {
            if (p != null && p.getKey() != null) {
                list.add(p.getKey());
            }
        }
        return list;
    }

    public void setSelectExprList(List<Pair<Expression, String>> selectExprList) {
        this.selectExprList = selectExprList;
    }

    public TableReferences getTables() {
        return tables;
    }

    public void setTables(TableReferences tables) {
        this.tables = tables;
    }

    public Expression getWhere() {
        return where;
    }

    public void setWhere(Expression where) {
        this.where = where;
    }

    public GroupBy getGroup() {
        return group;
    }

    public void setGroup(GroupBy group) {
        this.group = group;
    }

    public Expression getHaving() {
        return having;
    }

    public void setHaving(Expression having) {
        this.having = having;
    }

    public OrderBy getOrder() {
        return order;
    }

    public void setOrder(OrderBy order) {
        this.order = order;
    }

    public Limit getOutermostLimit() {
        return outermostLimit;
    }

    public void setOutermostLimit(Limit outermostLimit) {
        this.outermostLimit = outermostLimit;
    }

    public OrderBy getOutermostOrderBy() {
        return outermostOrderBy;
    }

    public void setOutermostOrderBy(OrderBy outermostOrderBy) {
        this.outermostOrderBy = outermostOrderBy;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DMLSelectStatement {\n")
                .append("    option=").append(option).append("\n")
                .append("    selectExprList=").append(selectExprList).append("\n")
                .append("    tables=").append(tables).append("\n")
                .append("    where=").append(where).append("\n")
                .append("    group=").append(group).append("\n")
                .append("    having=").append(having).append("\n")
                .append("    order=").append(order).append("\n")
                .append("    limit=").append(limit).append("\n")
                .append("    outermostLimit=").append(outermostLimit).append("\n")
                .append("    outermostOrderBy=").append(outermostOrderBy).append("\n")
                .append("}");
        return sb.toString();
    }

}
