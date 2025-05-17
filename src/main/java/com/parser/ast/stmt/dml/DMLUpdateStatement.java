package com.parser.ast.stmt.dml;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.fragment.Limit;
import com.parser.ast.fragment.OrderBy;
import com.parser.ast.fragment.tableref.TableReferences;
import com.parser.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DMLUpdateStatement extends DMLStatement {
    private final boolean lowPriority;
    private final boolean ignore;
    private final TableReferences tableRefs;
    private final List<Pair<Identifier, Expression>> values;
    private final Expression where;
    private final OrderBy orderBy;
    private final Limit limit;

    public DMLUpdateStatement(boolean lowPriority, boolean ignore, TableReferences tableRefs,
                              List<Pair<Identifier, Expression>> values, Expression where, OrderBy orderBy,
                              Limit limit) {
        this.lowPriority = lowPriority;
        this.ignore = ignore;
        if (tableRefs == null)
            throw new IllegalArgumentException("argument tableRefs is null for update stmt");
        this.tableRefs = tableRefs;
        if (values == null || values.size() <= 0) {
            this.values = Collections.emptyList();
        } else if (!(values instanceof ArrayList)) {
            this.values = new ArrayList<Pair<Identifier, Expression>>(values);
        } else {
            this.values = values;
        }
        this.where = where;
        this.orderBy = orderBy;
        this.limit = limit;
    }

    public boolean isLowPriority() {
        return lowPriority;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public TableReferences getTableRefs() {
        return tableRefs;
    }

    public List<Pair<Identifier, Expression>> getValues() {
        return values;
    }

    public Expression getWhere() {
        return where;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public Limit getLimit() {
        return limit;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DMLUpdateStatement {\n")
                .append("    lowPriority=").append(lowPriority).append("\n")
                .append("    ignore=").append(ignore).append("\n")
                .append("    tableRefs=").append(tableRefs).append("\n")
                .append("    values=\n");

        for (Pair<Identifier, Expression> value : values) {
            sb.append("        ").append(value.getKey()).append(" = ").append(value.getValue()).append("\n");
        }

        sb.append("    where=").append(where).append("\n")
                .append("    orderBy=").append(orderBy).append("\n")
                .append("    limit=").append(limit).append("\n")
                .append("}");
        return sb.toString();
    }

}
