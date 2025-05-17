package com.parser.ast.expression.primary;

public class Wildcard extends Identifier {
    public Wildcard(Identifier parent) {
        super(parent, "*", "*");
    }

    public String toString() {
        return "Wildcard{}";
    }
}
