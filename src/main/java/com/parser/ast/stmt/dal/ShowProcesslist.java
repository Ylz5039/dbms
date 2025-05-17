package com.parser.ast.stmt.dal;

public class ShowProcesslist extends DALShowStatement {
    private final boolean full;

    public ShowProcesslist(boolean full) {
        this.full = full;
    }

    public boolean isFull() {
        return full;
    }

    public String toString() {
        return "ShowProcesslist {\n" +
                "    full=" + full + "\n" +
                "}";
    }

}
