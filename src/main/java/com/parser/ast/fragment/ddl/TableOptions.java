package com.parser.ast.fragment.ddl;

import com.parser.ast.ASTNode;
import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.expression.primary.literal.LiteralString;

import java.util.List;

public class TableOptions implements ASTNode {

    public enum InsertMethod {
        NO, FIRST, LAST
    }

    public enum PackKeys {
        FALSE, TRUE, DEFAULT
    }

    public enum RowFormat {
        DEFAULT, DYNAMIC, FIXED, COMPRESSED, REDUNDANT, COMPACT
    }

    public enum Compression {
        ZLIB, LZ4, NONE
    }

    private Identifier engine;
    private Expression autoIncrement;
    private Expression avgRowLength;
    private Identifier charSet;
    private Identifier collation;
    private Boolean checkSum;
    private LiteralString comment;
    private LiteralString connection;
    private LiteralString dataDir;
    private LiteralString indexDir;
    private Boolean delayKeyWrite;
    private InsertMethod insertMethod;
    private Expression keyBlockSize;
    private Expression maxRows;
    private Expression minRows;
    private PackKeys packKeys;
    private LiteralString password;
    private RowFormat rowFormat;
    private List<Identifier> union;
    private Compression compression;
    private Boolean encryption;

    public TableOptions() {}

    public Identifier getEngine() {
        return engine;
    }

    public void setEngine(Identifier engine) {
        this.engine = engine;
    }

    public Expression getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(Expression autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public Expression getAvgRowLength() {
        return avgRowLength;
    }

    public void setAvgRowLength(Expression avgRowLength) {
        this.avgRowLength = avgRowLength;
    }

    public Identifier getCharSet() {
        return charSet;
    }

    public void setCharSet(Identifier charSet) {
        this.charSet = charSet;
    }

    public Identifier getCollation() {
        return collation;
    }

    public void setCollation(Identifier collation) {
        this.collation = collation;
    }

    public Boolean getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(Boolean checkSum) {
        this.checkSum = checkSum;
    }

    public LiteralString getComment() {
        return comment;
    }

    public void setComment(LiteralString comment) {
        this.comment = comment;
    }

    public LiteralString getConnection() {
        return connection;
    }

    public void setConnection(LiteralString connection) {
        this.connection = connection;
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

    public Boolean getDelayKeyWrite() {
        return delayKeyWrite;
    }

    public void setDelayKeyWrite(Boolean delayKeyWrite) {
        this.delayKeyWrite = delayKeyWrite;
    }

    public InsertMethod getInsertMethod() {
        return insertMethod;
    }

    public void setInsertMethod(InsertMethod insertMethod) {
        this.insertMethod = insertMethod;
    }

    public Expression getKeyBlockSize() {
        return keyBlockSize;
    }

    public void setKeyBlockSize(Expression keyBlockSize) {
        this.keyBlockSize = keyBlockSize;
    }

    public Expression getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(Expression maxRows) {
        this.maxRows = maxRows;
    }

    public Expression getMinRows() {
        return minRows;
    }

    public void setMinRows(Expression minRows) {
        this.minRows = minRows;
    }

    public PackKeys getPackKeys() {
        return packKeys;
    }

    public void setPackKeys(PackKeys packKeys) {
        this.packKeys = packKeys;
    }

    public LiteralString getPassword() {
        return password;
    }

    public void setPassword(LiteralString password) {
        this.password = password;
    }

    public RowFormat getRowFormat() {
        return rowFormat;
    }

    public void setRowFormat(RowFormat rowFormat) {
        this.rowFormat = rowFormat;
    }

    public List<Identifier> getUnion() {
        return union;
    }

    public void setUnion(List<Identifier> union) {
        this.union = union;
    }

    public Compression getCompression() {
        return compression;
    }

    public void setCompression(Compression compression) {
        this.compression = compression;
    }

    public Boolean getEncryption() {
        return encryption;
    }

    public void setEncryption(Boolean encryption) {
        this.encryption = encryption;
    }

    public String toString() {
        return "TableOptions{" +
                "engine=" + engine +
                ", autoIncrement=" + autoIncrement +
                ", avgRowLength=" + avgRowLength +
                ", charSet=" + charSet +
                ", collation=" + collation +
                ", checkSum=" + checkSum +
                ", comment=" + comment +
                ", connection=" + connection +
                ", dataDir=" + dataDir +
                ", indexDir=" + indexDir +
                ", delayKeyWrite=" + delayKeyWrite +
                ", insertMethod=" + insertMethod +
                ", keyBlockSize=" + keyBlockSize +
                ", maxRows=" + maxRows +
                ", minRows=" + minRows +
                ", packKeys=" + packKeys +
                ", password=" + password +
                ", rowFormat=" + rowFormat +
                ", union=" + union +
                ", compression=" + compression +
                ", encryption=" + encryption +
                '}';
    }
}
