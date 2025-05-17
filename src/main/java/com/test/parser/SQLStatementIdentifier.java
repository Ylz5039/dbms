package com.test.parser;

import java.sql.SQLSyntaxErrorException;

public class SQLStatementIdentifier {
    public String identifySQLStatement(String sql) throws SQLSyntaxErrorException {
        StringBuilder sb = new StringBuilder();

        String orginalSQL = sql;
        sql = sql.trim().toUpperCase();

        String[] words = sql.split("\\s+");

        int index = 0;
        int indexch = 0; // TODO

        switch (words[index]) {
            case "ALTER":
                index++;
                switch (words[index]) {
                    case "EVENT":
                        return "ALTER EVENT";
                    case "TABLE":
                        return "ALTER TABLE";
                    case "VIEW":
                        return "ALTER VIEW";
                    default:
                        sb.append(index + 1).append(" ").append(words[index]);
                        return sb.toString();
                }
            case "CALL":
                return "CALL";
            case "CREATE":
                index++;
                switch (words[index]) {
                    case "DATABASE":
                        return "CREATE DATABASE";
                    case "EVENT":
                        return "CREATE EVENT";
                    case "FUNCTION":
                        return "CREATE FUNCTION";
                    case "INDEX":
                        return "CREATE INDEX";
                    case "PROCEDURE":
                        return "CREATE PROCEDURE";
                    case "TABLE":
                        return "CREATE TABLE";
                    case "TRIGGER":
                        return "CREATE TRIGGER";
                    case "VIEW":
                        return "CREATE VIEW";
                    default:
                        sb.append(index + 1).append(" ").append(words[index]);
                        return sb.toString();
                }
            case "DELETE":
                return "DELETE";
            case "DROP":
                index++;
                switch (words[index]) {
                    case "INDEX":
                        return "DROP INDEX";
                    case "TABLE":
                        return "DROP TABLE";
                    case "TRIGGER":
                        return "DROP TRIGGER";
                    default:
                        sb.append(index + 1).append(" ").append(words[index]);
                        return sb.toString();
                }
            case "INSERT":
                return "INSERT";
            case "RENAME":
                index++;
                if(words[index].equals("TABLE")) {
                    return "RENAME TABLE";
                } else {
                    sb.append(index + 1).append(" ").append(words[index]);
                    return sb.toString();
                }
            case "REPLACE":
                return "REPLACE";
            case "SELECT":
                if (orginalSQL.contains("UNION")) {
                    return "SELECT UNION";
                } else {
                    return "SELECT";
                }
            case "SET":
                index++;
                switch (words[index]) {
                    case "CHARACTER":
                        index++;
                        if("SET".equals(words[index])) {
                            return "SET CHARACTER SET";
                        }
                        else {
                            sb.append(index + 1).append(" ").append(words[index]);
                            return sb.toString();
                        }
                    case "NAMES":
                        return "SET NAMES";
                    default:
                        return "SET";
                }
            case "SHOW":
                index++;
                switch (words[index]) {
                    case "BINLOG":
                        index++;
                        if ("EVENTS".equals(words[index])) {
                            return "SHOW BINLOG EVENTS";
                        }
                        else {
                            sb.append(index + 1).append(" ").append(words[index]);
                            return sb.toString();
                        }
                    case "CHARACTER":
                        index++;
                        if ("SET".equals(words[index])) {
                            return "SHOW CHARACTER SET";
                        }
                        else {
                            sb.append(index + 1).append(" ").append(words[index]);
                            return sb.toString();
                        }
                    case "CHARSET":
                        return "SHOW CHARSET";
                    case "COLLATION":
                        return "SHOW COLLATION";
                    case "COLUMNS":
                        return "SHOW COLUMNS";
                    case "CREATE":
                        return "SHOW CREATE";
                    case "DATABASES":
                        return "SHOW DATABASES";
                    case "ENGINE":
                        return "SHOW ENGINE";
                    case "ERRORS":
                        return "SHOW ERRORS";
                    case "EVENTS":
                        return "SHOW EVENTS";
                    case "FIELDS":
                        return "SHOW FIELDS";
                    case "FUNCTION":
                        index++;
                        if ("CODE".equals(words[index])) {
                            return "SHOW FUNCTION CODE";
                        } else if ("STATUS".equals(words[index])) {
                            return "SHOW FUNCTION STATUS";
                        } else {
                            sb.append(index + 1).append(" ").append(words[index]);
                            return sb.toString();
                        }
                    case "GRANTS":
                        return "SHOW GRANTS";
                    case "INDEX":
                        return "SHOW INDEX";
                    case "OPEN":
                        index++;
                        if ("TABLES".equals(words[index])) {
                            return "SHOW OPEN TABLES";
                        } else {
                            sb.append(index + 1).append(" ").append(words[index]);
                            return sb.toString();
                        }
                    case "PROCEDURE":
                        index++;
                        if ("CODE".equals(words[index])) {
                            return "SHOW PROCEDURE CODE";
                        } else if ("STATUS".equals(words[index])) {
                            return "SHOW PROCEDURE STATUS";
                        } else {
                            sb.append(index + 1).append(" ").append(words[index]);
                            return sb.toString();
                        }
                    case "PROCESSLIST":
                        return "SHOW PROCESSLIST";
                    case "PROFILE":
                        return "SHOW PROFILE";
                    case "STATUS":
                        return "SHOW STATUS";
                    case "TABLES":
                        return "SHOW TABLES";
                    case "TABLE":
                        index++;
                        if ("STATUS".equals(words[index])) {
                            return "SHOW TABLE STATUS";
                        } else {
                            sb.append(index + 1).append(" ").append(words[index]);
                            return sb.toString();
                        }
                    case "TRIGGERS":
                        return "SHOW TRIGGERS";
                    case "VARIABLES":
                        return "SHOW VARIABLES";
                    case "WARNINGS":
                        return "SHOW WARNINGS";
                    default:
                        sb.append(index + 1).append(" ").append(words[index]);
                        return sb.toString();
                }
            case "TRUNCATE":
                index++;
                if("TABLE".equals(words[index])) {
                    return "TRUNCATE TABLE";
                } else {
                    sb.append(index + 1).append(" ").append(words[index]);
                    return sb.toString();
                }
            case "UPDATE":
                return "UPDATE";
            case "USE":
                return "USE";
            default:
                sb.append(index + 1).append(" ").append(words[index]);
                return sb.toString();
        }
    }
}
