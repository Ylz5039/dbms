package com.parser.ast.stmt.dal;

import com.parser.ast.expression.primary.Identifier;

public class ShowFunctionCode extends DALShowStatement {
    private final Identifier functionName;

    public ShowFunctionCode(Identifier functionName) {
        this.functionName = functionName;
    }

    public Identifier getFunctionName() {
        return functionName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShowFunctionCode {\n")
                .append("    functionName=").append(functionName != null ? functionName.toString() : "null").append("\n")
                .append("}");
        return sb.toString();
    }

}
