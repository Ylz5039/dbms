package com.parser.ast.stmt.dal;

import com.parser.ast.expression.primary.Identifier;

public class ShowProcedureCode extends DALShowStatement {
    private final Identifier procedureName;

    public ShowProcedureCode(Identifier procedureName) {
        this.procedureName = procedureName;
    }

    public Identifier getProcedureName() {
        return procedureName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowProcedureCode {\n")
                .append("    procedureName=").append(procedureName != null ? procedureName.toString() : "null").append("\n")
                .append("}");
        return sb.toString();
    }

}
