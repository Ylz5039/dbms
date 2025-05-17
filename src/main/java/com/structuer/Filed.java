package com.structuer;

import java.io.Serializable;

public class Filed implements Serializable {

    private String filedName;  // 字段名称
    private String filedType;   // 字段类型
    private boolean isNotNull;  // 字段是否可以为空

    // 无参构造函数
    public Filed() {}

    // 全参数构造函数
    public Filed(String filedName, String filedType, boolean isNotNull) {
        this.filedName = filedName;
        this.filedType = filedType;
        this.isNotNull = isNotNull;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getFiledType() {
        return filedType;
    }

    public void setFiledType(String filedType) {
        this.filedType = filedType;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public void setNotNull(boolean notNull) {
        isNotNull = notNull;
    }

    @Override
    public String toString() {
        return "Filed{" +
                "filedName='" + filedName + '\'' +
                ", filedType='" + filedType + '\'' +
                ", isNotNull=" + isNotNull +
                '}';
    }
}