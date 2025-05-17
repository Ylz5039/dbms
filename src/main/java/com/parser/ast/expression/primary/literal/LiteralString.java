package com.parser.ast.expression.primary.literal;

import java.util.Arrays;
import java.util.Map;

public class LiteralString extends Literal{
    private final String introducer;
    private final byte[] data;
    private final boolean nchars;

    public LiteralString(String introducer, byte[] data, boolean nchars) {
        super();
        this.introducer = introducer;
        if (data == null)
            throw new IllegalArgumentException("argument string is null!");
        this.data = data;
        this.nchars = nchars;
    }

    public byte[] getData() {
        return data;
    }

    public String getIntroducer() {
        return introducer;
    }

    public byte[] getBytes() {
        return data;
    }

    public boolean isNchars() {
        return nchars;
    }

    public String getUnescapedString() {
        return getUnescapedString(data, false);
    }

    public String getUnescapedString(boolean toUppercase) {
        return getUnescapedString(data, toUppercase);
    }

    public static String getUnescapedString(byte[] string) {
        return getUnescapedString(string, false);
    }

    public static String getUnescapedString(byte[] string, boolean toUppercase) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < string.length; ++i) {
            byte c = string[i];
            if (c == '\\') {
                switch (c = string[++i]) {
                    case '0':
                        sb.append('\0');
                        break;
                    case 'b':
                        sb.append('\b');
                        break;
                    case 'n':
                        sb.append('\n');
                        break;
                    case 'r':
                        sb.append('\r');
                        break;
                    case 't':
                        sb.append('\t');
                        break;
                    case 'Z':
                        sb.append((char) 26);
                        break;
                    default:
                        sb.append(c);
                }
            } else if (c == '\'') {
                ++i;
                sb.append('\'');
            } else {
                if (toUppercase && c >= 'a' && c <= 'z')
                    c -= 32;
                sb.append((char) c);
            }
        }
        return sb.toString();
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        if (data == null)
            return null;
        return getUnescapedString();
    }

    public String toString() {
        return "LiteralString{" +
                "introducer='" + introducer + '\'' +
                ", data=" + Arrays.toString(data) +
                ", nchars=" + nchars +
                '}';
    }
}
