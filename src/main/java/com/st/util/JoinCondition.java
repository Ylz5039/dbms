package com.st.util;

/**
 * 表示一个连接条件：当前表的字段和另一个表的字段。
 */
public class JoinCondition {
    private String thisField;  // 当前表的字段名
    private String otherField; // 另一个表的字段名

    public JoinCondition(String thisField, String otherField) {
        this.thisField = thisField;
        this.otherField = otherField;
    }

    public String getThisField() {
        return thisField;
    }

    public String getOtherField() {
        return otherField;
    }
}
