package com.parser.ast.fragment.ddl.index;

import com.parser.ast.ASTNode;
import com.parser.ast.expression.primary.Identifier;

import java.util.Collections;
import java.util.List;

public class IndexDefinition implements ASTNode {

    public enum IndexType {
        BTREE, HASH
    }

    public enum KeyType {
        PRIMARY, UNIQUE, KEY, FULLTEXT, SPATIAL
    }

    private Identifier indexName = null;
    private final IndexType indexType;
    private final List<IndexColumnName> columns;
    private final List<IndexOption> options;
    private Identifier symbol;

    @SuppressWarnings("unchecked")
    public IndexDefinition(IndexType indexType, List<IndexColumnName> columns,
                           List<IndexOption> options) {
        this.indexType = indexType;
        if (columns == null || columns.isEmpty())
            throw new IllegalArgumentException("columns is null or empty");
        this.columns = columns;
        this.options =
                (List<IndexOption>) (options == null || options.isEmpty() ? Collections.emptyList()
                        : options);
    }

    public Identifier getIndexName() {
        return indexName;
    }

    public void setIndexName(Identifier indexName) {
        this.indexName = indexName;
    }

    public IndexType getIndexType() {
        return indexType;
    }

    public List<IndexColumnName> getColumns() {
        return columns;
    }

    public List<IndexOption> getOptions() {
        return options;
    }

    public Identifier getSymbol() {
        return symbol;
    }

    public void setSymbol(Identifier symbol) {
        this.symbol = symbol;
    }

    public String toString() {
        return "IndexDefinition{" +
                "indexName=" + indexName +
                ", indexType=" + indexType +
                ", columns=" + columns +
                ", options=" + options +
                ", symbol=" + symbol +
                '}';
    }
}

