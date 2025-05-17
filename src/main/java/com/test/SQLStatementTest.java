package com.test;

import com.parser.ast.stmt.ddl.DDLUseDatabaseStatement;
import com.parser.recognizer.SQLParserDelegate;
import com.structuer.Result;
import com.structuer.func.AnalysisClassUtil;
import com.structuer.func.IbdFileHandler;
import com.test.parser.SQLStatementIdentifier;

import java.io.IOException;
import java.sql.SQLOutput;
import java.sql.SQLSyntaxErrorException;
import java.util.List;

import static com.test.mapper.SQLStatementMapper.SQL_STATEMENT_MAP;

public class SQLStatementTest {

    static String[] DALSqlStatements = {
            "SET CHARACTER SET utf8;",
            "SET NAMES 'utf8';",
            "SET @variable_name = value;",
            "SHOW BINLOG EVENTS;",
            "SHOW CHARACTER SET;",
            "SHOW CHARSET;",
            "SHOW COLLATION;",
            "SHOW COLUMNS FROM users;",
            "SHOW CREATE TABLE users;",
            "SHOW DATABASES;",
            "SHOW ENGINE INNODB STATUS;",
            "SHOW ERRORS;",
            "SHOW EVENTS;",
            "SHOW FIELDS FROM users;",
            "SHOW FUNCTION CODE my_function;",
            "SHOW FUNCTION STATUS;",
            "SHOW GRANTS FOR CURRENT_USER;",
            "SHOW INDEX FROM users;",
            "SHOW OPEN TABLES;",
            "SHOW PROCEDURE CODE my_procedure;",
            "SHOW PROCEDURE STATUS;",
            "SHOW PROCESSLIST;",
            "SHOW PROFILE FOR QUERY 1;",
            "SHOW STATUS;",
            "SHOW TABLES;",
            "SHOW TABLE STATUS;",
            "SHOW TRIGGERS;",
            "SHOW VARIABLES;",
            "SHOW WARNINGS;"
    };

    static String[] DDLSqlStatements = {
            "ALTER EVENT my_event ON SCHEDULE AT '2024-12-31 12:00:00'",
            "ALTER TABLE users ADD COLUMN age INT",
            "ALTER VIEW user_view AS SELECT name, email FROM users",
            "CREATE DATABASE test_db",
            "CREATE EVENT my_daily_event ON SCHEDULE EVERY 1 DAY DO INSERT INTO my_table (column1) VALUES (CURRENT_DATE)",
//                "CREATE FUNCTION multiply_numbers(a INT, b INT) RETURNS INT BEGIN RETURN a * b; END",
            "CREATE INDEX idx_email ON users(email)",
//                "CREATE PROCEDURE add_to_table(IN value1 INT, IN value2 INT) BEGIN INSERT INTO my_table (column1, column2) VALUES (value1, value2); END",
            "CREATE TABLE users (id INT, name VARCHAR(100), email VARCHAR(100))",
            "CREATE TRIGGER before_insert_users BEFORE INSERT ON users FOR EACH ROW SET NEW.created_at = NOW()",
            "CREATE VIEW user_view AS SELECT id, name FROM users",
            "DROP INDEX idx_email ON users",
            "DROP TABLE users",
            "DROP TRIGGER before_insert_users",
            "RENAME TABLE users TO customers",
            "TRUNCATE TABLE customers",
    };
    static String[] DMLSqlStatements = {
//            "CALL my_procedure()",
//            "DELETE FROM users WHERE id = 1",
//            "INSERT INTO users (name, email, age) VALUES ('Alice', 'alice@example.com', 32)",
//            "SELECT * FROM users",
//            "REPLACE INTO users (id, name, email) VALUES (2, 'Charlie', 'charlie@example.com')",
//            "UPDATE users SET email = 'alice_new@example.com' WHERE name = 'Alice'",
//                "SELECT id, name FROM users UNION SELECT id, name FROM admins",
            "CREATE DATABASE test_db",
            "CREATE TABLE IF NOT EXISTS student (id  VARCHAR(255) NOT NULL, name VARCHAR(255), age  VARCHAR(255) NOT NULL)",
            "INSERT INTO student (id, name, age)  VALUES ('1','user1','20')",
            "INSERT INTO student (id, name, age)  VALUES ('2','user2','30')",
            "select * from student ",
            "UPDATE student SET name = 'Jane Doe', age = '30' WHERE id = '2'",
            "select * from student  where name = 'Jane Doe' and id = '2'",
            "delete from student where id='1'",
            "select * from student "
//
    };

    static String[] ErrSqlStatements = {
            "CREATE DATAB1ASE test_db",
            "ALTE TABLE my_table",
            "CREAT DATABASE my_db",
            "SELCT * FROM my_table",
            "INSEERT INTO my_table (column1) VALUES (value1)",
            "UPDAT my_table SET column1 = value1",
            "DELEET FROM my_table",
            "DRO TABLE my_table",
            "SHOW DATABSES",
            "SHOW TABLS",
            "CREATE FUNCTIN my_function() RETURNS INT",
            "SHOW ENGNE INNODB STATUS",
            "SHOW VARIABELS",
            "SELCT * FROM table1 UNION SELCT * FROM table2",
            "SHOW CHRSET",
            "SHOW ERROS",
            "CREATE DATABASE test_db t111",
    };

    public static void DALTest() throws SQLSyntaxErrorException, IOException {
        SQLStatementTest tester = new SQLStatementTest();
        System.out.println("---------------- DAL ----------------");
        for (String sql : DALSqlStatements) {
            tester.testSQLStatement(sql);
            System.out.println("----------");
        }
    }

    public static void DDLTest() throws SQLSyntaxErrorException, IOException {
        SQLStatementTest tester = new SQLStatementTest();
        System.out.println("---------------- DDL ----------------");
        for (String sql : DDLSqlStatements) {
            tester.testSQLStatement(sql);
            System.out.println("----------");
        }
    }

    public static void DMLTest() throws SQLSyntaxErrorException, IOException {
        ALLDatabase database=new ALLDatabase();
        SQLStatementTest tester = new SQLStatementTest();
        System.out.println("---------------- DML ----------------");
        for (String sql : DMLSqlStatements) {
            tester.testSQLStatement(sql);
            System.out.println("----------");
        }

    }

    public static void ErrTest() throws SQLSyntaxErrorException, IOException {
        SQLStatementTest tester = new SQLStatementTest();
        System.out.println("---------------- ERR ----------------");
        for (String sql : ErrSqlStatements) {
            tester.testSQLStatement(sql);
        }
    }

    public String testSQLStatement(String sql) throws SQLSyntaxErrorException, IOException {

        String statement = new SQLStatementIdentifier().identifySQLStatement(sql);

        StringBuilder sb = new StringBuilder();

        Class<?> statementClass = SQL_STATEMENT_MAP.get(statement.toUpperCase());

        if (statementClass != null) {
            Object ast = SQLParserDelegate.parse(sql);

            System.out.println(ast.toString());

            Result result = AnalysisClassUtil.analyzeSQLStatement(ast, ALLDatabase.dataBase);

            if(result.getTableName() != null) {
                System.out.println(result.getIsTrue());
                System.out.println(result.toString());
            }
            else {
                System.out.println(result.getIsTrue());
            }
            sb.append("Result: ").append(result.getIsTrue()).append("\n");

            List<List<String>> records = result.getRecords();
            if(records!= null) {
                sb.append("Records: ").append("\n");
                for (List<String> record : records) {
                    sb.append(record).append("\n");
                }
            }

        } else {
            sb.append("Unsupported MySQLToken: ").append(statement);
            System.err.println("Unsupported MySQLToken: " + statement);
        }
        System.out.println(ALLDatabase.dataBase);
        return sb.toString();
    }

    public static void main(String[] args) throws SQLSyntaxErrorException, IOException {
        DMLTest();
    }
}
