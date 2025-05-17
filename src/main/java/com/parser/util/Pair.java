package com.parser.util;

public final class Pair<K, V> implements Cloneable {
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(key).append(", ").append(value).append(")");
        return sb.toString();
    }

    private static final int HASH_CONST = 37;

    public int hashCode() {
        int hash = 17;
        if (key == null) {
            hash += HASH_CONST;
        } else {
            hash = hash << 5 + hash << 1 + hash + key.hashCode();
        }
        if (value == null) {
            hash += HASH_CONST;
        } else {
            hash = hash << 5 + hash << 1 + hash + value.hashCode();
        }
        return hash;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Pair))
            return false;
        Pair that = (Pair) obj;
        return isEquals(this.key, that.key) && isEquals(this.value, that.value);
    }

    private boolean isEquals(Object o1, Object o2) {
        if (o1 == o2)
            return true;
        if (o1 == null)
            return o2 == null;
        return o1.equals(o2);
    }

    public Pair<K, V> clone() {
        Pair<K, V> clone = null;
        try {
            clone = (Pair<K, V>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); // won't happen
        }
        return clone;
    }
}
