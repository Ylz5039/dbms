package com.parser.ast.stmt.dal;

import com.parser.ast.expression.Expression;
import com.parser.ast.fragment.Limit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowProfile extends DALShowStatement {
    public static enum Type {
        ALL, BLOCK_IO, CONTEXT_SWITCHES, CPU, IPC, MEMORY, PAGE_FAULTS, SOURCE, SWAPS
    }

    private final List<Type> types;
    private final Expression forQuery;
    private final Limit limit;

    public ShowProfile(List<Type> types, Expression forQuery, Limit limit) {
        if (types == null || types.isEmpty()) {
            this.types = Collections.emptyList();
        } else if (types instanceof ArrayList) {
            this.types = types;
        } else {
            this.types = new ArrayList<ShowProfile.Type>(types);
        }
        this.forQuery = forQuery;
        this.limit = limit;
    }

    public List<Type> getTypes() {
        return types;
    }

    public Expression getForQuery() {
        return forQuery;
    }

    public Limit getLimit() {
        return limit;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowProfile {\n")
                .append("    types=").append(types).append("\n")
                .append("    forQuery=").append(forQuery).append("\n")
                .append("    limit=").append(limit).append("\n")
                .append("}");
        return sb.toString();
    }

}
