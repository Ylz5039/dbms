package com.parser.ast.expression.primary.literal;

import com.parser.util.ParseString;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

public class LiteralHexadecimal extends Literal {
    private byte[] bytes;
    private final String introducer;
    private final String charset;
    private final byte[] string;
    private final int offset;
    private final int size;

    public LiteralHexadecimal(String introducer, byte[] string, int offset, int size,
                              String charset) {
        super();
        if (string == null || offset + size > string.length)
            throw new IllegalArgumentException("hex text is invalid");
        if (charset == null)
            throw new IllegalArgumentException("charset is null");
        this.introducer = introducer;
        this.charset = charset;
        this.string = string;
        this.offset = offset;
        this.size = size;
    }

    public String getText() {
        return new String(string, offset, size);
    }

    public String getIntroducer() {
        return introducer;
    }

    public void appendTo(StringBuilder sb) {
        sb.append(new String(string, offset, size));
    }

    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        try {
            this.bytes = ParseString.hexString2Bytes(string, offset, size);
            return new String(bytes, introducer == null ? charset : introducer.substring(1));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("", e);
        }
    }

    public String toString() {
        return "LiteralHexadecimal{" +
                "bytes=" + Arrays.toString(bytes) +
                ", introducer='" + introducer + '\'' +
                ", charset='" + charset + '\'' +
                ", string=" + Arrays.toString(string) +
                ", offset=" + offset +
                ", size=" + size +
                '}';
    }
}
