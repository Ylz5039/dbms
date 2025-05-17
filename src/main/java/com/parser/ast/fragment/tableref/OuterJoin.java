package com.parser.ast.fragment.tableref;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OuterJoin implements TableReference {
    private static List<String> ensureListType(List<String> list) {
        if (list == null)
            return null;
        if (list.isEmpty())
            return Collections.emptyList();
        if (list instanceof ArrayList)
            return list;
        return new ArrayList<String>(list);
    }

    private final boolean isLeftJoin;
    private final TableReference leftTableRef;
    private final TableReference rightTableRef;
    private final Expression onCond;
    private final List<String> using;

    private Identifier alias = null;

    private OuterJoin(boolean isLeftJoin, TableReference leftTableRef, TableReference rightTableRef,
                      Expression onCond, List<String> using) {
        super();
        this.isLeftJoin = isLeftJoin;
        this.leftTableRef = leftTableRef;
        this.rightTableRef = rightTableRef;
        this.onCond = onCond;
        this.using = ensureListType(using);
    }

    public OuterJoin(boolean isLeftJoin, TableReference leftTableRef, TableReference rightTableRef,
                     Expression onCond) {
        this(isLeftJoin, leftTableRef, rightTableRef, onCond, null);
    }

    public OuterJoin(boolean isLeftJoin, TableReference leftTableRef, TableReference rightTableRef,
                     List<String> using) {
        this(isLeftJoin, leftTableRef, rightTableRef, null, using);
    }

    public boolean isLeftJoin() {
        return isLeftJoin;
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
        return null;
    }

    public boolean isSingleTable() {
        return false;
    }

    public int getPrecedence() {
        return PRECEDENCE_JOIN;
    }

    public void setAlias(Identifier alias) {
        this.alias = alias;
    }

    public Identifier getAlias() {
        return alias;
    }

    public String toString() {
        return "OuterJoin{" +
                "isLeftJoin=" + isLeftJoin +
                ", leftTableRef=" + leftTableRef +
                ", rightTableRef=" + rightTableRef +
                ", onCond=" + onCond +
                ", using=" + using +
                ", alias=" + alias +
                '}';
    }
}
