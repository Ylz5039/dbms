package com.parser.ast.stmt;

public interface SQLStatement {
    public enum StmtType {
        DML_SELECT, DML_DELETE, DML_INSERT, DML_REPLACE, DML_UPDATE, DML_CALL, DAL_SET, DAL_SHOW, MTL_START,
        /** COMMIT or ROLLBACK */
        MTL_TERMINATE, MTL_ISOLATION
    }
}