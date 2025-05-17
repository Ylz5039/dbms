package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.primary.Identifier;
import com.parser.util.Pair;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DDLRenameTableStatement implements DDLStatement {
    private final List<Pair<Identifier, Identifier>> list;

    public DDLRenameTableStatement() {
        this.list = new LinkedList<Pair<Identifier, Identifier>>();
    }

    public DDLRenameTableStatement(List<Pair<Identifier, Identifier>> list) {
        if (list == null) {
            this.list = Collections.emptyList();
        } else {
            this.list = list;
        }
    }

    public DDLRenameTableStatement addRenamePair(Identifier from, Identifier to) {
        list.add(new Pair<Identifier, Identifier>(from, to));
        return this;
    }

    public List<Pair<Identifier, Identifier>> getList() {
        return list;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DDLRenameTableStatement {\n")
                .append("    list=").append(list).append("\n")
                .append("}");
        return sb.toString();
    }

}
