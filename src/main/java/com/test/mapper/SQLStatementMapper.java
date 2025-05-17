package com.test.mapper;

import com.parser.ast.stmt.dal.*;
import com.parser.ast.stmt.ddl.*;
import com.parser.ast.stmt.dml.*;
import com.parser.recognizer.SQLParserDelegate;

import java.util.HashMap;
import java.util.Map;

public class SQLStatementMapper {
    public static final Map<String, Class<?>> SQL_STATEMENT_MAP = new HashMap<>();

    static {
        // DAL Statements
        SQL_STATEMENT_MAP.put("SET CHARACTER SET", DALSetCharacterSetStatement.class);
        SQL_STATEMENT_MAP.put("SET NAMES", DALSetNamesStatement.class);
        SQL_STATEMENT_MAP.put("SET", DALSetStatement.class);
        SQL_STATEMENT_MAP.put("SHOW BINLOG EVENTS", ShowBinLogEvent.class);
        SQL_STATEMENT_MAP.put("SHOW CHARACTER SET", ShowCharaterSet.class);
        SQL_STATEMENT_MAP.put("SHOW CHARSET", ShowCharset.class);
        SQL_STATEMENT_MAP.put("SHOW COLLATION", ShowCollation.class);
        SQL_STATEMENT_MAP.put("SHOW COLUMNS", ShowColumns.class);
        SQL_STATEMENT_MAP.put("SHOW CREATE", ShowCreate.class);
        SQL_STATEMENT_MAP.put("SHOW CREATE DATABASE", ShowCreateDatabase.class);
        SQL_STATEMENT_MAP.put("SHOW DATABASES", ShowDatabases.class);
        SQL_STATEMENT_MAP.put("SHOW ENGINE", ShowEngine.class);
        SQL_STATEMENT_MAP.put("SHOW ERRORS", ShowErrors.class);
        SQL_STATEMENT_MAP.put("SHOW EVENTS", ShowEvents.class);
        SQL_STATEMENT_MAP.put("SHOW FIELDS", ShowFields.class);
        SQL_STATEMENT_MAP.put("SHOW FUNCTION CODE", ShowFunctionCode.class);
        SQL_STATEMENT_MAP.put("SHOW FUNCTION STATUS", ShowFunctionStatus.class);
        SQL_STATEMENT_MAP.put("SHOW GRANTS", ShowGrants.class);
        SQL_STATEMENT_MAP.put("SHOW INDEX", ShowIndex.class);
        SQL_STATEMENT_MAP.put("SHOW OPEN TABLES", ShowOpenTables.class);
        SQL_STATEMENT_MAP.put("SHOW PROCEDURE CODE", ShowProcedureCode.class);
        SQL_STATEMENT_MAP.put("SHOW PROCEDURE STATUS", ShowProcedureStatus.class);
        SQL_STATEMENT_MAP.put("SHOW PROCESSLIST", ShowProcesslist.class);
        SQL_STATEMENT_MAP.put("SHOW PROFILE", ShowProfile.class);
        SQL_STATEMENT_MAP.put("SHOW STATUS", ShowStatus.class);
        SQL_STATEMENT_MAP.put("SHOW TABLES", ShowTables.class);
        SQL_STATEMENT_MAP.put("SHOW TABLE STATUS", ShowTableStatus.class);
        SQL_STATEMENT_MAP.put("SHOW TRIGGERS", ShowTriggers.class);
        SQL_STATEMENT_MAP.put("SHOW VARIABLES", ShowVariables.class);
        SQL_STATEMENT_MAP.put("SHOW WARNINGS", ShowWarnings.class);
        // DDL Statements
        SQL_STATEMENT_MAP.put("ALTER EVENT", DDLAlterEventStatement.class);
        SQL_STATEMENT_MAP.put("ALTER TABLE", DDLAlterTableStatement.class);
        SQL_STATEMENT_MAP.put("ALTER VIEW", DDLAlterViewStatement.class);
        SQL_STATEMENT_MAP.put("CREATE DATABASE", DDLCreateDatabaseStatement.class);
        SQL_STATEMENT_MAP.put("CREATE EVENT", DDLCreateEventStatement.class);
        SQL_STATEMENT_MAP.put("CREATE FUNCTION", DDLCreateFunctionStatement.class); // TODO
        SQL_STATEMENT_MAP.put("CREATE INDEX", DDLCreateIndexStatement.class);
        SQL_STATEMENT_MAP.put("USE",DDLUseDatabaseStatement.class);
        // TODO CREATE LIKE
        SQL_STATEMENT_MAP.put("CREATE PROCEDURE", DDLCreateProcedureStatement.class); // TODO
        SQL_STATEMENT_MAP.put("CREATE TABLE", DDLCreateTableStatement.class);
        SQL_STATEMENT_MAP.put("CREATE TRIGGER", DDLCreateTriggerStatement.class);
        SQL_STATEMENT_MAP.put("CREATE VIEW", DDLCreateViewStatement.class);
        SQL_STATEMENT_MAP.put("DROP INDEX", DDLDropIndexStatement.class);
        SQL_STATEMENT_MAP.put("DROP TABLE", DDLDropTableStatement.class);
        SQL_STATEMENT_MAP.put("DROP TRIGGER", DDLDropTriggerStatement.class);
        SQL_STATEMENT_MAP.put("RENAME TABLE", DDLRenameTableStatement.class);
        SQL_STATEMENT_MAP.put("TRUNCATE TABLE", DDLTruncateStatement.class);
        // DML Statements
        SQL_STATEMENT_MAP.put("CALL", DMLCallStatement.class);
        SQL_STATEMENT_MAP.put("DELETE", DMLDeleteStatement.class);
        // TODO INSERT REPLACE
        SQL_STATEMENT_MAP.put("INSERT", DMLInsertStatement.class);
        SQL_STATEMENT_MAP.put("SELECT", DMLSelectStatement.class);
        SQL_STATEMENT_MAP.put("REPLACE", DMLReplaceStatement.class);
        SQL_STATEMENT_MAP.put("UPDATE", DMLUpdateStatement.class);
        SQL_STATEMENT_MAP.put("SELECT UNION", DMLSelectUnionStatement.class); // TODO
    }

    public static Class<?> getStatementClass(String statement) {
        return SQL_STATEMENT_MAP.get(statement.toUpperCase());
    }
}
