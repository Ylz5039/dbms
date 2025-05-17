package com.parser.ast.expression.primary.literal;

public class LiteralBitField extends Literal {
    private final String text;
    private final String introducer;

    public LiteralBitField(String introducer, String bitFieldText) {
        super();
        if (bitFieldText == null)
            throw new IllegalArgumentException("bit text is null");
        this.introducer = introducer;
        this.text = bitFieldText;
    }

    public String getText() {
        return text;
    }

    public String getIntroducer() {
        return introducer;
    }

    public String toString() {
        return "LiteralBitField{" +
                "text='" + text + '\'' +
                ", introducer='" + introducer + '\'' +
                '}';
    }
}
