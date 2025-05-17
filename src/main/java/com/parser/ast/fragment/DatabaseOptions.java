package com.parser.ast.fragment;

import com.parser.ast.ASTNode;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.expression.primary.literal.LiteralString;

public class DatabaseOptions implements ASTNode {

    public enum Encryption {
        YES, NO
    }

    public enum Charset {
        UTF8, UTF8MB4, LATIN1, ASCII
    }

    public enum Collation {
        UTF8_GENERAL_CI, UTF8MB4_UNICODE_CI, LATIN1_SWEDISH_CI, ASCII_GENERAL_CI
    }

    private Charset charSet;
    private Collation collation;
    private LiteralString comment;
    private LiteralString dataDir;
    private LiteralString indexDir;
    private Encryption encryption;

    public DatabaseOptions() {}

    public Charset getCharSet() {
        return charSet;
    }

    public void setCharSet(Charset charSet) {
        this.charSet = charSet;
    }

    public Collation getCollation() {
        return collation;
    }

    public void setCollation(Collation collation) {
        this.collation = collation;
    }

    public LiteralString getComment() {
        return comment;
    }

    public void setComment(LiteralString comment) {
        this.comment = comment;
    }

    public LiteralString getDataDir() {
        return dataDir;
    }

    public void setDataDir(LiteralString dataDir) {
        this.dataDir = dataDir;
    }

    public LiteralString getIndexDir() {
        return indexDir;
    }

    public void setIndexDir(LiteralString indexDir) {
        this.indexDir = indexDir;
    }

    public Encryption getEncryption() {
        return encryption;
    }

    public void setEncryption(Encryption encryption) {
        this.encryption = encryption;
    }

    public String toString() {
        return "DatabaseOptions{" +
                "charSet=" + charSet +
                ", collation=" + collation +
                ", comment=" + comment +
                ", dataDir=" + dataDir +
                ", indexDir=" + indexDir +
                ", encryption=" + encryption +
                '}';
    }
}
