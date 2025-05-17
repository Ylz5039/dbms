package com.parser.ast.stmt.mts;

import com.parser.ast.stmt.SQLStatement;

public class MTSStartTransactionStatement implements SQLStatement {
    public static enum TransactionCharacteristic {
        WITH_CONSISTENT_SNAPSHOT, READ_WRITE, READ_ONLY
    }

    private final TransactionCharacteristic characteristic;

    public MTSStartTransactionStatement(TransactionCharacteristic characteristic) {
        this.characteristic = characteristic;
    }

    public TransactionCharacteristic getCharacteristic() {
        return characteristic;
    }

}
