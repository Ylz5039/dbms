package com.parser.ast.fragment.ddl.datatype;

import com.parser.ast.ASTNode;
import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;

import java.util.HashMap;
import java.util.List;

public class DataType implements ASTNode {
    private final DataTypeName typeName;
    private final boolean unsigned;
    private final boolean zerofill;
    private final boolean binary;
    private final Expression length;
    private final Expression decimals;
    private final Identifier charSet;
    private Identifier collation;
    private final List<Expression> collectionVals;

    public static final HashMap<DataTypeName, Byte> mapper = new HashMap<>();

    public static final HashMap<DataTypeName, Integer> DATA_TYPE_SIZE = new HashMap<>();

    public DataType(DataTypeName typeName, boolean unsigned, boolean zerofill, boolean binary,
                    Expression length, Expression decimals, Identifier charSet, Identifier collation,
                    List<Expression> collectionVals) {
        if (typeName == null)
            throw new IllegalArgumentException("typeName is null");
        this.typeName = typeName;
        this.unsigned = unsigned;
        this.zerofill = zerofill;
        this.binary = binary;
        this.length = length;
        this.decimals = decimals;
        this.charSet = charSet;
        this.collation = collation;
        this.collectionVals = collectionVals;
    }

    public enum DataTypeName {
        BIT, TINYINT, SMALLINT, MEDIUMINT, INT, BIGINT, REAL, DOUBLE, FLOAT, DECIMAL, DATE, TIME, TIMESTAMP, DATETIME, YEAR, CHAR, VARCHAR, BINARY, VARBINARY, TINYBLOB, BLOB, MEDIUMBLOB, LONGBLOB, TINYTEXT, TEXT, MEDIUMTEXT, LONGTEXT, ENUM, SET, BOOL, BOOLEAN, SERIAL, FIXED
    }

    static {
        mapper.put(DataTypeName.BIGINT, (byte) 3);
        mapper.put(DataTypeName.BINARY, (byte) 254);
        mapper.put(DataTypeName.BIT, (byte) 16);
        mapper.put(DataTypeName.BLOB, (byte) 252);
        mapper.put(DataTypeName.BOOL, (byte) 1);
        mapper.put(DataTypeName.BOOLEAN, (byte) 1);
        mapper.put(DataTypeName.CHAR, (byte) 254);
        mapper.put(DataTypeName.DATE, (byte) 10);
        mapper.put(DataTypeName.DATETIME, (byte) 12);
        mapper.put(DataTypeName.DECIMAL, (byte) 246);
        mapper.put(DataTypeName.DOUBLE, (byte) 4);
        mapper.put(DataTypeName.ENUM, (byte) 247);
        mapper.put(DataTypeName.FIXED, (byte) 246);
        mapper.put(DataTypeName.FLOAT, (byte) 4);
        mapper.put(DataTypeName.INT, (byte) 4);
        mapper.put(DataTypeName.LONGBLOB, (byte) 252);
        mapper.put(DataTypeName.LONGTEXT, (byte) 252);
        mapper.put(DataTypeName.MEDIUMBLOB, (byte) 252);
        mapper.put(DataTypeName.MEDIUMINT, (byte) 3);
        mapper.put(DataTypeName.MEDIUMTEXT, (byte) 252);
        mapper.put(DataTypeName.REAL, (byte) 5);
        mapper.put(DataTypeName.SERIAL, (byte) 3);
        mapper.put(DataTypeName.SET, (byte) 248);
        mapper.put(DataTypeName.SMALLINT, (byte) 2);
        mapper.put(DataTypeName.TEXT, (byte) 252);
        mapper.put(DataTypeName.TIME, (byte) 11);
        mapper.put(DataTypeName.TIMESTAMP, (byte) 7);
        mapper.put(DataTypeName.TINYBLOB, (byte) 252);
        mapper.put(DataTypeName.TINYINT, (byte) 1);
        mapper.put(DataTypeName.TINYTEXT, (byte) 252);
        mapper.put(DataTypeName.VARBINARY, (byte) 253);
        mapper.put(DataTypeName.VARCHAR, (byte) 253);
        mapper.put(DataTypeName.YEAR, (byte) 13);
        DATA_TYPE_SIZE.put(DataTypeName.BIGINT, 3);
        DATA_TYPE_SIZE.put(DataTypeName.BINARY, 254);
        DATA_TYPE_SIZE.put(DataTypeName.BIT, 16);
        DATA_TYPE_SIZE.put(DataTypeName.BLOB, 252);
        DATA_TYPE_SIZE.put(DataTypeName.BOOL, 1);
        DATA_TYPE_SIZE.put(DataTypeName.BOOLEAN, 1);
        DATA_TYPE_SIZE.put(DataTypeName.CHAR, 254);
        DATA_TYPE_SIZE.put(DataTypeName.DATE, 10);
        DATA_TYPE_SIZE.put(DataTypeName.DATETIME, 12);
        DATA_TYPE_SIZE.put(DataTypeName.DECIMAL, 246);
        DATA_TYPE_SIZE.put(DataTypeName.DOUBLE, 4);
        DATA_TYPE_SIZE.put(DataTypeName.ENUM, 247);
        DATA_TYPE_SIZE.put(DataTypeName.FIXED, 246);
        DATA_TYPE_SIZE.put(DataTypeName.FLOAT, 4);
        DATA_TYPE_SIZE.put(DataTypeName.INT, 4);
        DATA_TYPE_SIZE.put(DataTypeName.LONGBLOB, 252);
        DATA_TYPE_SIZE.put(DataTypeName.LONGTEXT, 252);
        DATA_TYPE_SIZE.put(DataTypeName.MEDIUMBLOB, 252);
        DATA_TYPE_SIZE.put(DataTypeName.MEDIUMINT, 3);
        DATA_TYPE_SIZE.put(DataTypeName.MEDIUMTEXT, 252);
        DATA_TYPE_SIZE.put(DataTypeName.REAL, 5);
        DATA_TYPE_SIZE.put(DataTypeName.SERIAL, 3);
        DATA_TYPE_SIZE.put(DataTypeName.SET, 248);
        DATA_TYPE_SIZE.put(DataTypeName.SMALLINT, 2);
        DATA_TYPE_SIZE.put(DataTypeName.TEXT, 252);
        DATA_TYPE_SIZE.put(DataTypeName.TIME, 11);
        DATA_TYPE_SIZE.put(DataTypeName.TIMESTAMP, 7);
        DATA_TYPE_SIZE.put(DataTypeName.TINYBLOB, 252);
        DATA_TYPE_SIZE.put(DataTypeName.TINYINT, 1);
        DATA_TYPE_SIZE.put(DataTypeName.TINYTEXT, 252);
        DATA_TYPE_SIZE.put(DataTypeName.VARBINARY, 253);
        DATA_TYPE_SIZE.put(DataTypeName.VARCHAR, 253);
        DATA_TYPE_SIZE.put(DataTypeName.YEAR, 13);
    }

    public boolean hasCollation() {
        switch (typeName) {
            case CHAR:
            case VARCHAR:
            case TINYTEXT:
            case TEXT:
            case MEDIUMTEXT:
            case LONGTEXT:
            case ENUM:
            case SET:
                return true;
            default:
                return false;
        }
    }

    public static byte getType(DataTypeName type) {
        if (mapper.containsKey(type)) {
            return mapper.get(type);
        }
        return 6;
    }

    public static Integer getTypeSize(DataTypeName typeName) {
        if (DATA_TYPE_SIZE.containsKey(typeName)) {
            return DATA_TYPE_SIZE.get(typeName);
        }
        return -1;
    }

    public static boolean isBinary(DataTypeName type) {
        switch (type) {
            case BINARY:
            case BLOB:
            case LONGBLOB:
            case MEDIUMBLOB:
            case TINYBLOB:
            case VARBINARY:
                return true;
            default:
                return false;
        }
    }

    public static boolean isBlobFlag(DataTypeName type) {
        switch (type) {
            case BLOB:
            case LONGBLOB:
            case MEDIUMBLOB:
            case TINYBLOB:
            case TINYTEXT:
            case TEXT:
            case MEDIUMTEXT:
            case LONGTEXT:
                return true;
            default:
                return false;
        }
    }

    public DataTypeName getTypeName() {
        return typeName;
    }

    public boolean isUnsigned() {
        return unsigned;
    }

    public boolean isZerofill() {
        return zerofill;
    }

    public boolean isBinary() {
        return binary;
    }

    public Expression getLength() {
        return length;
    }

    public Expression getDecimals() {
        return decimals;
    }

    public Identifier getCharSet() {
        return charSet;
    }

    public Identifier getCollation() {
        return collation;
    }

    public void setCollation(Identifier collation) {
        this.collation = collation;
    }

    public List<Expression> getCollectionVals() {
        return collectionVals;
    }

    public String toString() {
        return "DataType{" +
                "typeName=" + typeName +
                ", unsigned=" + unsigned +
                ", zerofill=" + zerofill +
                ", binary=" + binary +
                ", length=" + length +
                ", decimals=" + decimals +
                ", charSet=" + charSet +
                ", collation=" + collation +
                ", collectionVals=" + collectionVals +
                '}';
    }
}
