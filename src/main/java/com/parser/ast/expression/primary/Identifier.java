package com.parser.ast.expression.primary;

import java.util.Objects;

public class Identifier extends PrimaryExpression{
    protected Identifier parent;
    protected String idText;
    protected String idTextUpUnescape;

    public Identifier(Identifier parent, String idText) {
        this(parent, idText, idText.toUpperCase());
    }

    public Identifier(Identifier parent, String idText, String idTextUp) {
        this.parent = parent;
        this.idText = idText;
        this.idTextUpUnescape = unescapeName(idTextUp);
    }

    public static String unescapeName(String name) {
        return unescapeName(name, false);
    }

    public static String unescapeName(String name, boolean toUppercase) {
        if (name == null || name.length() <= 0) {
            return name;
        }
        if (name.charAt(0) != '`') {
            return toUppercase ? name.toUpperCase() : name;
        }
        StringBuilder sb = new StringBuilder(name.length() - 2);
        final int endIndex = name.length() - 1;
        boolean hold = false;
        for (int i = 1; i < endIndex; ++i) {
            char c = name.charAt(i);
            if (c == '`' && !hold) {
                hold = true;
                continue;
            }
            hold = false;
            if (toUppercase && c >= 'a' && c <= 'z') {
                c -= 32;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public String getLevelUnescapeUpName(int level) {
        Identifier id = this;
        for (int i = level; i > 1 && id != null; --i) {
            id = id.parent;
        }
        if (id != null) {
            return id.idTextUpUnescape;
        }
        return null;
    }

    public static final int PARENT_ABSENT = 0;

    public static final int PARENT_TRIMED = 1;

    public static final int PARENT_IGNORED = 2;

    public int trimParent(int level, String trimSchema) {
        Identifier id = this;
        for (int i = 1; i < level; ++i) {
            if (id.parent == null) {
                return PARENT_ABSENT;
            }
            id = id.parent;
        }
        if (id.parent == null) {
            return PARENT_ABSENT;
        }
        if (trimSchema != null && !trimSchema.equals(id.parent.idTextUpUnescape)) {
            return PARENT_IGNORED;
        } else {
            id.parent = null;
            return PARENT_TRIMED;
        }
    }

    public Identifier getParent() {
        return parent;
    }

    public void setParent(Identifier parent) {
        this.parent = parent;
    }

    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
    }

    public String getIdTextUpUnescape() {
        return idTextUpUnescape;
    }

    public void setIdTextUpUnescape(String idTextUpUnescape) {
        this.idTextUpUnescape = idTextUpUnescape;
    }

    public String getIdTextWithParentUpUnescape() {
        StringBuilder sb = new StringBuilder();
        if (parent != null) {
            sb.append(parent.getIdTextUpUnescape()).append('.');
        }
        return sb.append(this.idTextUpUnescape).toString();
    }

    public String getIdTextWithParent() {
        StringBuilder sb = new StringBuilder();
        if (parent != null) {
            sb.append(parent.getIdText()).append('.');
        }
        return sb.append(this.idText).toString();
    }

    public int hashCode() {
        final int constant = 37;
        int hash = 17;
        if (parent == null) {
            hash += constant;
        } else {
            hash = hash * constant + parent.hashCode();
        }
        if (idText == null) {
            hash += constant;
        } else {
            hash = hash * constant + idTextUpUnescape.hashCode();
        }
        return hash;
    }

    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof Identifier) {
            Identifier that = (Identifier) obj;
            if(that.parent == null && strEquals(this.idTextUpUnescape, that.idTextUpUnescape)) {
                return true;
            }
            return objEquals(this.parent, that.parent)
                    && strEquals(this.idTextUpUnescape, that.idTextUpUnescape);
        }
        return false;
    }

    private static boolean objEquals(Object obj, Object obj2) {
        if (obj == obj2)
            return true;
        if (obj == null)
            return obj2 == null;
        return obj.equals(obj2);
    }

    private static boolean strEquals(String str1, String str2) {
        if(Objects.equals(str1, str2)) {
            return true;
        }
        return str1.equals("*") || str2.equals("*");
    }

    public Identifier clone() {
        Identifier clone = null;
        try {
            clone = (Identifier) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); // won't happen
        }
        return clone;
    }

    public String toString() {
        return "Identifier{" +
                "parent=" + parent +
                ", idText='" + idText + '\'' +
                ", idTextUpUnescape='" + idTextUpUnescape + '\'' +
                '}';
    }
}
