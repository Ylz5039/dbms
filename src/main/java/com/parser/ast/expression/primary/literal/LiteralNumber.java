package com.parser.ast.expression.primary.literal;

import java.util.Comparator;
import java.util.Map;

public class LiteralNumber extends Literal implements Comparable<Object> {
    private Number number;

    public LiteralNumber(Number number) {
        super();
        if (number == null)
            throw new IllegalArgumentException("number is null!");
        this.number = number;
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return number;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public boolean equals(Object obj) {
        if(obj instanceof LiteralNumber){
            return getNumber().equals(((LiteralNumber) obj).getNumber());
        } else {
            return false;
        }
    }

    public int compareTo(Object o) {
        if(o == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        if(o instanceof LiteralNumber) {
            return Double.compare(this.number.doubleValue(), ((LiteralNumber) o).number.doubleValue());
        }
        return 0;
    }

    public String toString() {
        return "LiteralNumber{" +
                "number=" + number +
                '}';
    }
}
