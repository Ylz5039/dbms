package com.parser.ast.fragment.tableref;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.expression.primary.ParamMarker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableRefFactor extends AliasableTableReference {
    private Identifier table;
    private ParamMarker marker;
    private final List<IndexHint> hintList;
    private List<Identifier> partitions;

    public TableRefFactor(Identifier table, String alias, List<IndexHint> hintList) {
        super(alias);
        this.table = table;
        if (hintList == null || hintList.isEmpty()) {
            this.hintList = Collections.emptyList();
        } else if (hintList instanceof ArrayList) {
            this.hintList = hintList;
        } else {
            this.hintList = new ArrayList<IndexHint>(hintList);
        }
        this.partitions = null;
    }

    public TableRefFactor(Identifier table, String alias, List<IndexHint> hintList,
                          List<Identifier> partitions) {
        this(table, alias, hintList);
        this.partitions = partitions;
    }

    public TableRefFactor(Identifier table, List<IndexHint> hintList) {
        this(table, null, hintList);
    }

    public TableRefFactor(ParamMarker paramMarker, String alias, List<IndexHint> hintList,
                          List<Identifier> partitions) {
        this(null, alias, hintList);
        this.marker = paramMarker;
        this.partitions = partitions;
    }

    public ParamMarker getParamMarker() {
        return marker;
    }

    public Identifier getTable() {
        return table;
    }

    public List<IndexHint> getHintList() {
        return hintList;
    }

    public List<Identifier> getPartitions() {
        return partitions;
    }

    public Object removeLastConditionElement() {
        return null;
    }

    public boolean isSingleTable() {
        return true;
    }

    public int getPrecedence() {
        return PRECEDENCE_FACTOR;
    }

    public String toString() {
        return "TableRefFactor{" +
                "table=" + table +
                ", marker=" + marker +
                ", hintList=" + hintList +
                ", partitions=" + partitions +
                '}';
    }
}
