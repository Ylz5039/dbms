package com.structuer;



import java.util.ArrayList;
import java.util.List;

public class Result {
    private String tableName; // 表名
    private List<Filed> filedList; // 字段列表
    private List<List<String>> records; // 记录数据，i 行 j 列
    private String isTrue;

    public Result(String tableName, List<Filed> filedList, List<List<String>> records,String isTrue) {
        this.tableName = tableName;
        this.filedList = filedList;
        this.records = records;
        this.isTrue=isTrue;
    }

    public String getTableName() {
        return tableName;
    }

    public List<String> getFiledList() {
        List<String> list=new ArrayList<>();
        for (Filed filed :filedList) {
            list.add(filed.getFiledName());
        }

        return list;
    }

    public List<List<String>> getRecords() {
        return records;
    }

    public String getIsTrue(){
        return isTrue;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Result for table: ").append(tableName).append("\n");
        sb.append("Fields: ").append(getFiledList()).append("\n");
        sb.append("Records:\n");
        for (List<String> record : records) {
            sb.append(record).append("\n");
        }
        return sb.toString();
    }
}
