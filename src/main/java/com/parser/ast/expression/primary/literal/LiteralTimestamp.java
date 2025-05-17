package com.parser.ast.expression.primary.literal;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

public class LiteralTimestamp extends Literal {
    private Timestamp timestamp;

    public LiteralTimestamp(Timestamp timestamp) {
        super();
        if (timestamp == null)
            throw new IllegalArgumentException("timestamp is null!");
        this.timestamp = timestamp;
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        return timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean equals(Object obj) {
        if (obj instanceof LiteralTimestamp) {
            return getTimestamp().equals(((LiteralTimestamp) obj).getTimestamp());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(timestamp);
    }

    public int compareTo(LiteralTimestamp o) {
        if (o == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        return this.timestamp.compareTo(o.timestamp);
    }

    public String toString() {
        return "LiteralTimeStamp{" +
                "timestamp=" + timestamp +
                '}';
    }
}
