package com.parser.ast.fragment.tableref;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

public class TableReferences implements TableReference {
    private final List<TableReference> list;

    public TableReferences(List<TableReference> list) throws SQLSyntaxErrorException {
        if (list == null || list.isEmpty()) {
            throw new SQLSyntaxErrorException("at least one table reference");
        }
        this.list = ensureListType(list);
    }

    protected static List<TableReference> ensureListType(List<TableReference> list) {
        if (list instanceof ArrayList)
            return list;
        return new ArrayList<TableReference>(list);
    }

    public List<TableReference> getList() {
        return list;
    }

    public Object removeLastConditionElement() {
        if (list != null && !list.isEmpty()) {
            return list.get(list.size() - 1).removeLastConditionElement();
        }
        return null;
    }

    public boolean isSingleTable() {
        if (list == null) {
            return false;
        }
        int count = 0;
        TableReference first = null;
        for (TableReference ref : list) {
            if (ref != null && 1 == ++count) {
                first = ref;
            }
        }
        return count == 1 && first.isSingleTable();
    }

    public int getPrecedence() {
        return PRECEDENCE_REFS;
    }

    public String toString() {
        return "TableReferences{" +
                "list=" + list +
                '}';
    }
}
