package com.parser.ast.stmt.dal;

import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;

public class ShowTableStatus extends DALShowStatement {
    private Identifier database;
    private final String pattern;
    private final Expression where;

    public ShowTableStatus(Identifier database, Expression where) {
        this.database = database;
        this.pattern = null;
        this.where = where;
    }

    public ShowTableStatus(Identifier database, String pattern) {
        this.database = database;
        this.pattern = pattern;
        this.where = null;
    }

    public ShowTableStatus(Identifier database) {
        this.database = database;
        this.pattern = null;
        this.where = null;
    }

    public void setDatabase(Identifier database) {
        this.database = database;
    }

    public Identifier getDatabase() {
        return database;
    }

    public String getPattern() {
        return pattern;
    }

    public Expression getWhere() {
        return where;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowTableStatus {\n")
                .append("    database=").append(database).append("\n")
                .append("    pattern='").append(pattern).append("'\n")
                .append("    where=").append(where).append("\n")
                .append("}");
        return sb.toString();
    }

}
