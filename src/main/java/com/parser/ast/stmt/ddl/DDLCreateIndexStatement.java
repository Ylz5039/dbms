package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.fragment.ddl.index.IndexDefinition;

public class DDLCreateIndexStatement implements DDLStatement {
    public enum Algorithm {
        DEFAULT, INPLACE, COPY
    }

    public enum Lock {
        DEFAULT, NONE, SHARED, EXCLUSIVE
    }

    private final Identifier table;
    private final IndexDefinition indexDefinition;
    private final Algorithm algorithm;
    private final Lock lock;

    public DDLCreateIndexStatement(Identifier table, IndexDefinition indexDefinition,
                                   Algorithm algorithm, Lock lock) {
        this.table = table;
        this.indexDefinition = indexDefinition;
        this.algorithm = algorithm;
        this.lock = lock;
    }

    public Identifier getTable() {
        return table;
    }

    public IndexDefinition getIndexDefinition() {
        return indexDefinition;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Lock getLock() {
        return lock;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLCreateIndexStatement {\n")
                .append("    table=").append(table).append("\n")
                .append("    indexDefinition=").append(indexDefinition).append("\n")
                .append("    algorithm=").append(algorithm).append("\n")
                .append("    lock=").append(lock).append("\n")
                .append("}");
        return sb.toString();
    }

}
