package com.structuer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 表示数据库中的表，包含字段和记录。
 */
public class Table implements Serializable {
    private String tableName; // 表名
    private List<Filed> filedList; // 字段列表
    private List<List<String>> records; // 记录数据，i 行 j 列
    private RecordAllocationTable recordAllocationTable; // 使用 RecordAllocationTable

    /**
     * 构造函数
     *
     * @param tableName       表名
     * @param filedList       字段列表
     * @param initialCapacity 初始容量
     */
    public Table(String tableName, List<Filed> filedList, int initialCapacity) {
        this.tableName = tableName;
        this.filedList = filedList;
        this.records = new ArrayList<>(initialCapacity);
        this.recordAllocationTable = new RecordAllocationTable(initialCapacity); // 初始化记录分配表
    }

    /**
     * 插入记录并更新分配表。
     *
     * @param record 记录
     */
    public void insertRecord(List<String> record) {
        // 检查记录的列数是否与表的字段数匹配
        if (record.size() != filedList.size()) {
            throw new IllegalArgumentException("记录的列数与表的字段数不匹配。");
        }

        // 查找空闲位置
        int position = recordAllocationTable.findEmptyRecordPosition();
        if (position == -1) {
            // 如果没有空位，则扩展记录容量
            recordAllocationTable.expandCapacity();
            position = recordAllocationTable.findEmptyRecordPosition(); // 找到新的空闲位置
        }

        // 初始化空记录并插入
        while (records.size() <= position) {
            records.add(new ArrayList<>()); // 初始化为空记录
        }
        records.set(position, record); // 插入记录
        recordAllocationTable.markRecordPositionUsed(position); // 更新分配表
    }

    /**
     * 删除记录并更新分配表。
     *
     * @param recordIndex 记录索引
     */
    public void deleteRecord(int recordIndex) {
        // 检查记录索引是否合法
        if (recordIndex >= 0 && recordIndex < records.size()) {
            // 移除记录
            records.remove(recordIndex); // 直接移除记录
            // 更新分配表，释放该位置
            recordAllocationTable.releaseRecordPosition(recordIndex);
            //可以选择缩小记录列表的容量
            if (records.size() < recordAllocationTable.getCapacity() / 2) {
                recordAllocationTable.shrinkCapacity();
            }
        } else {
            throw new IndexOutOfBoundsException("记录索引超出范围: " + recordIndex);
        }
    }
    /**
     * 更新特定索引的记录。
     *
     * @param recordIndex 记录索引
     * @param newRecord   新记录
     */
    public void updateRecord(int recordIndex, List<String> newRecord) {
        // 检查记录索引是否合法
        if (recordIndex >= 0 && recordIndex < records.size()) {
            // 检查新记录的列数是否与表的字段数匹配
            if (newRecord.size() != filedList.size()) {
                throw new IllegalArgumentException("新记录的列数与表的字段数不匹配。");
            }
            records.set(recordIndex, newRecord); // 更新记录
        } else {
            throw new IndexOutOfBoundsException("记录索引超出范围: " + recordIndex);
        }
    }

    /**
     * 获取特定索引的记录。
     *
     * @param recordIndex 记录索引
     * @return 对应索引的记录
     */
    public List<String> getRecord(int recordIndex) {
        // 检查记录索引是否合法
        if (recordIndex >= 0 && recordIndex < records.size()) {
            return records.get(recordIndex); // 返回记录
        }
        throw new IndexOutOfBoundsException("记录索引超出范围: " + recordIndex);
    }

    /**
     * 返回所有记录。
     *
     * @return 所有记录的列表
     */
    public List<List<String>> getRecords() {
        return records;
    }

    /**
     * 获取表名。
     *
     * @return 表名
     */
    public String getTableName() {

        return tableName;
    }

    /**
     * 获取字段列表。
     *
     * @return 字段列表
     */
    public List<Filed> getFiledList() {
        return filedList;
    }

    public int getFiledIndex(String filedName) {
        for (int i = 0; i < filedList.size(); i++) {
            if (filedList.get(i).getFiledName().equals(filedName)) {
                return i; // 返回字段的索引
            }
        }
        return -1; // 字段不存在，返回 -1
    }

    @Override
    public String toString() {
        // 以字符串格式返回表的基本信息和记录
        StringBuilder sb = new StringBuilder("Table{" +
                "tableName='" + tableName + '\'' +
                ", filedList=" + filedList +
                ", records=[");
        for (List<String> record : records) {
            sb.append(record).append(", ");
        }
        sb.append("], recordAllocationTable=")
                .append(recordAllocationTable.getBitMapString()).append('}');
        return sb.toString();
    }
}
