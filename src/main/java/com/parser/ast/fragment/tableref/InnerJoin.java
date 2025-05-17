package com.parser.ast.fragment.tableref;

import com.parser.ast.expression.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InnerJoin implements TableReference {
    private final TableReference leftTableRef;
    private final TableReference rightTableRef;
    private Expression onCond;
    private List<String> using;

    private InnerJoin(TableReference leftTableRef, TableReference rightTableRef, Expression onCond,
                      List<String> using) {
        super();
        this.leftTableRef = leftTableRef;
        this.rightTableRef = rightTableRef;
        this.onCond = onCond;
        this.using = ensureListType(using);
    }

    private static List<String> ensureListType(List<String> list) {
        if (list == null)
            return null;
        if (list.isEmpty())
            return Collections.emptyList();
        if (list instanceof ArrayList)
            return list;
        return new ArrayList<String>(list);
    }

    public InnerJoin(TableReference leftTableRef, TableReference rightTableRef) {
        this(leftTableRef, rightTableRef, null, null);
    }

    public InnerJoin(TableReference leftTableRef, TableReference rightTableRef, Expression onCond) {
        this(leftTableRef, rightTableRef, onCond, null);
    }

    public InnerJoin(TableReference leftTableRef, TableReference rightTableRef,
                     List<String> using) {
        this(leftTableRef, rightTableRef, null, using);
    }

    public TableReference getLeftTableRef() {
        return leftTableRef;
    }

    public TableReference getRightTableRef() {
        return rightTableRef;
    }

    public Expression getOnCond() {
        return onCond;
    }

    public List<String> getUsing() {
        return using;
    }

    public Object removeLastConditionElement() {
        Object obj;
        if (onCond != null) {
            obj = onCond;
            onCond = null;
        } else if (using != null) {
            obj = using;
            using = null;
        } else {
            return null;
        }
        return obj;
    }

    public boolean isSingleTable() {
        return false;
    }

    public int getPrecedence() {
        return PRECEDENCE_JOIN;
    }

    public String toString() {
        return "InnerJoin{" +
                "leftTableRef=" + leftTableRef +
                ", rightTableRef=" + rightTableRef +
                ", onCond=" + onCond +
                ", using=" + using +
                '}';
    }
}
