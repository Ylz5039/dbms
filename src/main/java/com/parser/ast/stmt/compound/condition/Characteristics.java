package com.parser.ast.stmt.compound.condition;

import com.parser.ast.expression.Expression;

public class Characteristics {
    public enum Characteristic {
        COMMENT, LANGUAGE_SQL, DETERMINISTIC, NOT_DETERMINISTIC, CONTAINS_SQL, NO_SQL, READS_SQL_DATA, MODIFIES_SQL_DATA, SQL_SECURITY_DEFINER, SQL_SECURITY_INVOKER
    }

    private Expression comment;
    private Characteristic languageSql;
    private Characteristic deterministic;
    private Characteristic sqlCharacteristic;
    private Characteristic sqlSecurity;

    public boolean isEmpty() {
        return comment == null && languageSql == null && deterministic == null
                && sqlCharacteristic == null && sqlSecurity == null;
    }

    public Expression getComment() {
        return comment;
    }

    public void setComment(Expression comment) {
        this.comment = comment;
    }

    public Characteristic getLanguageSql() {
        return languageSql;
    }

    public void setLanguageSql(Characteristic languageSql) {
        this.languageSql = languageSql;
    }

    public Characteristic getDeterministic() {
        return deterministic;
    }

    public void setDeterministic(Characteristic deterministic) {
        this.deterministic = deterministic;
    }

    public Characteristic getSqlCharacteristic() {
        return sqlCharacteristic;
    }

    public void setSqlCharacteristic(Characteristic sqlCharacteristic) {
        this.sqlCharacteristic = sqlCharacteristic;
    }

    public Characteristic getSqlSecurity() {
        return sqlSecurity;
    }

    public void setSqlSecurity(Characteristic sqlSecurity) {
        this.sqlSecurity = sqlSecurity;
    }

    public String toString() {
        return "Characteristics{" +
                "comment=" + comment +
                ", languageSql=" + languageSql +
                ", deterministic=" + deterministic +
                ", sqlCharacteristic=" + sqlCharacteristic +
                ", sqlSecurity=" + sqlSecurity +
                '}';
    }
}
