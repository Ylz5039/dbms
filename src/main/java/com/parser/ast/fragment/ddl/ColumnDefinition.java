package com.parser.ast.fragment.ddl;

import com.parser.ast.ASTNode;
import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.literal.LiteralString;
import com.parser.ast.fragment.ddl.datatype.DataType;

public class ColumnDefinition implements ASTNode {
    public enum SpecialIndex {
        PRIMARY, UNIQUE,
    }

    public enum ColumnFormat {
        FIXED, DYNAMIC, DEFAULT,
    }

    public enum Storage {
        DISK, MEMORY, DEFAULT
    }

    private final DataType dataType;
    private final boolean notNull;
    private final Expression defaultVal;
    private final boolean autoIncrement;
    private final SpecialIndex specialIndex;
    private final LiteralString comment;
    private final ColumnFormat columnFormat;
    private final Expression onUpdate;
    private final Storage storage;
    private final Boolean virtual;
    private final Boolean stored;
    private final Expression as;

    public ColumnDefinition(DataType dataType, boolean notNull, Expression defaultVal,
                            boolean autoIncrement, SpecialIndex specialIndex, LiteralString comment,
                            ColumnFormat columnFormat, Expression onUpdate, Storage storage, Boolean virtual,
                            Boolean stored, Expression as) {
        if (dataType == null)
            throw new IllegalArgumentException("data type is null");
        this.dataType = dataType;
        this.notNull = notNull;
        this.defaultVal = defaultVal;
        this.autoIncrement = autoIncrement;
        this.specialIndex = specialIndex;
        this.comment = comment;
        this.columnFormat = columnFormat;
        this.onUpdate = onUpdate;
        this.storage = storage;
        this.virtual = virtual;
        this.stored = stored;
        this.as = as;
    }

    public DataType getDataType() {
        return dataType;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public Expression getDefaultVal() {
        return defaultVal;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public SpecialIndex getSpecialIndex() {
        return specialIndex;
    }

    public LiteralString getComment() {
        return comment;
    }

    public ColumnFormat getColumnFormat() {
        return columnFormat;
    }

    public Expression getOnUpdate() {
        return onUpdate;
    }

    public Storage getStorage() {
        return storage;
    }

    public Boolean getVirtual() {
        return virtual;
    }

    public Boolean getStored() {
        return stored;
    }

    public Expression getAs() {
        return as;
    }

    public String toString() {
        return "ColumnDefinition{" +
                "dataType=" + dataType +
                ", notNull=" + notNull +
                ", defaultVal=" + defaultVal +
                ", autoIncrement=" + autoIncrement +
                ", specialIndex=" + specialIndex +
                ", comment=" + comment +
                ", columnFormat=" + columnFormat +
                ", onUpdate=" + onUpdate +
                ", storage=" + storage +
                ", virtual=" + virtual +
                ", stored=" + stored +
                ", as=" + as +
                '}';
    }
}
