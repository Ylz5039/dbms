package com.parser.ast.stmt.ddl;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.expression.primary.literal.LiteralNumber;
import com.parser.ast.stmt.SQLStatement;
import com.parser.ast.stmt.dml.DMLStatement;

public class ExplainStatement implements SQLStatement {
    public enum Commands {
        EXPLAIN, DESCRIBE, DESC;
    }

    private final Commands command;

    private final Identifier tblName;

    private final Identifier colName;

    private final String wild;

    public enum ExplainType {
        EXTENDED, PARTITIONS, FORMAT;
    }

    public enum FormatName {
        TRADITIONAL, JSON;
    }

    private final ExplainType explainType;

    private final FormatName formatName;

    private final DMLStatement explainableStmt;

    private final LiteralNumber connectionId;

    public ExplainStatement(Commands command, Identifier tblName, Identifier colName, String wild) {
        this.command = command;
        if (tblName == null)
            throw new IllegalArgumentException(
                    "tbl_name is null for {EXPLAIN | DESCRIBE | DESC} tbl_name");
        this.tblName = tblName;
        this.colName = colName;
        this.wild = wild;
        this.explainType = null;
        this.formatName = null;
        this.explainableStmt = null;
        this.connectionId = null;
    }

    public ExplainStatement(Commands command, ExplainType explainType, FormatName formatName,
                            DMLStatement explainableStmt) {
        this.command = command;
        this.tblName = null;
        this.colName = null;
        this.wild = null;
        this.explainType = explainType;
        if (explainType == ExplainType.FORMAT && null == formatName) {
            throw new IllegalArgumentException(
                    "FORMAT is null for {EXPLAIN | DESCRIBE | DESC} FORMAT");
        }
        this.formatName = formatName;
        if (explainableStmt == null) {
            throw new IllegalArgumentException(
                    "explainable_stmt is null for {EXPLAIN | DESCRIBE | DESC} explainable_stmt");
        }
        this.explainableStmt = explainableStmt;
        this.connectionId = null;
    }

    public ExplainStatement(Commands command, ExplainType explainType, FormatName formatName,
                            LiteralNumber connectionId) {
        this.command = command;
        this.tblName = null;
        this.colName = null;
        this.wild = null;
        this.explainType = explainType;
        if (explainType == ExplainType.FORMAT && null == formatName) {
            throw new IllegalArgumentException(
                    "FORMAT is null for {EXPLAIN | DESCRIBE | DESC} FORMAT");
        }
        this.formatName = formatName;
        this.explainableStmt = null;
        if (connectionId == null) {
            throw new IllegalArgumentException(
                    "connection_id is null for {EXPLAIN | DESCRIBE | DESC} FOR CONNECTION connection_id");
        }
        this.connectionId = connectionId;
    }

    public Commands getCommand() {
        return command;
    }

    public Identifier getTblName() {
        return tblName;
    }

    public Identifier getColName() {
        return colName;
    }

    public String getWild() {
        return wild;
    }

    public ExplainType getExplainType() {
        return explainType;
    }

    public FormatName getFormatName() {
        return formatName;
    }

    public DMLStatement getExplainableStmt() {
        return explainableStmt;
    }

    public LiteralNumber getConnectionId() {
        return connectionId;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ExplainStatement {\n")
                .append("    command=").append(command).append("\n")
                .append("    tblName=").append(tblName).append("\n")
                .append("    colName=").append(colName).append("\n")
                .append("    wild=").append(wild).append("\n")
                .append("    explainType=").append(explainType).append("\n")
                .append("    formatName=").append(formatName).append("\n")
                .append("    explainableStmt=").append(explainableStmt).append("\n")
                .append("    connectionId=").append(connectionId).append("\n")
                .append("}");
        return sb.toString();
    }

}
