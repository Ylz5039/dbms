package com.st.util;

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
        this.recordAllocationTable = new RecordAllocationTable(initialCapacity); // 使用 RecordAllocationTable 初始化分配表
    }

    /**
     * 插入记录并更新分配表。
     *
     * @param record 记录
     */
    public void insertRecord(List<String> record) {
        if (record.size() != filedList.size()) {
            throw new IllegalArgumentException("记录的列数与表的字段数不匹配。");
        }

        // 查找空闲位置
        int position = recordAllocationTable.findEmptyRecordPosition();
        if (position == -1) {
            // 如果没有空位，则扩展记录分配表和记录
            expandCapacity();
            position = recordAllocationTable.findEmptyRecordPosition(); // 找到新的空闲位置
        }

        // 插入记录到指定位置
        while (records.size() <= position) {
            records.add(null); // 初始化为空记录
        }
        records.set(position, record);
        recordAllocationTable.markRecordPositionUsed(position); // 更新分配表
    }

    /**
     * 删除记录并更新分配表。
     *
     * @param recordIndex 记录索引
     */
    public void deleteRecord(int recordIndex) {
        if (recordIndex >= 0 && recordIndex < records.size()) {
            records.set(recordIndex, null);  // 设置该位置为空
            recordAllocationTable.releaseRecordPosition(recordIndex); // 更新分配表
        }
    }

    /**
     * 扩展记录容量和分配表。
     */
    private void expandCapacity() {
        recordAllocationTable.expandCapacity(); // 扩展位图
    }

    /**
     * 返回所有记录。
     *
     * @return 所有记录的列表
     */
    public List<List<String>> getRecords() {
        return records;
    }

    public String getTableName() {
        return tableName;
    }

    public List<Filed> getFiledList() {
        return filedList;
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableName='" + tableName + '\'' +
                ", filedList=" + filedList +
                ", records=" + records +
                ", recordAllocationTable=" + recordAllocationTable.getBitMapString() + // 输出位图字符串
                '}';
    }
}
