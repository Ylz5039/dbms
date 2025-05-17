package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.primary.Identifier;

public class DDLDropIndexStatement implements DDLStatement {
    public enum Algorithm {
        DEFAULT, INPLACE, COPY
    }

    public enum Lock {
        DEFAULT, NONE, SHARED, EXCLUSIVE
    }

    private final Identifier indexName;
    private final Identifier table;
    private Algorithm algorithm;
    private Lock lock;

    public DDLDropIndexStatement(Identifier indexName, Identifier table) {
        this.indexName = indexName;
        this.table = table;
    }

    public Identifier getIndexName() {
        return indexName;
    }

    public Identifier getTable() {
        return table;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLDropIndexStatement {\n")
                .append("    indexName=").append(indexName).append("\n")
                .append("    table=").append(table).append("\n")
                .append("    algorithm=").append(algorithm).append("\n")
                .append("    lock=").append(lock).append("\n")
                .append("}");
        return sb.toString();
    }

}
