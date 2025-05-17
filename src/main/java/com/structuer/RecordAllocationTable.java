package com.structuer;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录分配表类，用于管理数据库表中的记录状态。
 * 使用位图表示每个记录的位置是否被占用。
 */
public class RecordAllocationTable {
    private int[] bitMap; // 使用整数数组作为位图，每个 int 包含 32 个记录的状态
    private int usedCount; // 记录已使用的记录数量

    /**
     * 构造函数，初始化分配表，位图大小由 initialCapacity 决定
     *
     * @param initialCapacity 初始容量
     */
    public RecordAllocationTable(int initialCapacity) {
        int numOfInts = (initialCapacity + 31) / 32; // 每个 int 可以存储 32 位记录状态
        this.bitMap = new int[numOfInts];
        this.usedCount = 0; // 初始化已使用数量为0
    }

    /**
     * 查找第一个空闲位置，返回记录位置的索引
     *
     * @return 第一个空闲位置的索引，未找到则返回 -1
     */
    public int findEmptyRecordPosition() {
        for (int i = 0; i < bitMap.length; i++) {
            if (bitMap[i] != -1) { // -1 表示所有 32 位都被占用
                for (int j = 0; j < 32; j++) {
                    if ((bitMap[i] & (1 << j)) == 0) { // 检查第 j 位是否为空
                        return i * 32 + j; // 计算全局的记录索引
                    }
                }
            }
        }
        return -1; // 没有空闲位置
    }

    /**
     * 标记某个记录位置为已使用
     *
     * @param position 要标记的位置
     */
    public void markRecordPositionUsed(int position) {
        if (position < 0 || position >= getSize()) {
            throw new IndexOutOfBoundsException("位置超出范围: " + position);
        }
        int index = position / 32;
        int bit = position % 32;
        bitMap[index] |= (1 << bit); // 使用位或操作标记某位为 1
        usedCount++; // 增加已使用记录数量
    }

    /**
     * 释放某个记录位置
     *
     * @param position 要释放的位置
     */
    public void releaseRecordPosition(int position) {
        if (position < 0 || position >= getSize()) {
            throw new IndexOutOfBoundsException("位置超出范围: " + position);
        }
        int index = position / 32;
        int bit = position % 32;
        bitMap[index] &= ~(1 << bit); // 使用位与操作将某位置为 0
        usedCount--; // 减少已使用记录数量
    }

    /**
     * 扩展位图容量
     * 将位图的大小扩大为原来的两倍，以便存储更多记录
     */
    public void expandCapacity() {
        int[] newBitMap = new int[bitMap.length * 2]; // 扩展位图容量为原来的两倍
        System.arraycopy(bitMap, 0, newBitMap, 0, bitMap.length);
        bitMap = newBitMap; // 更新位图引用
    }

    /**
     * 获取位图的当前大小（记录总数）
     *
     * @return 当前记录的总数
     */
    public int getSize() {
        return bitMap.length * 32; // 每个 int 存储 32 个记录状态
    }

    /**
     * 获取当前已使用记录的数量
     *
     * @return 已使用的记录数量
     */
    public int getUsedCount() {
        return usedCount; // 返回已使用记录数量
    }

    /**
     * 获取当前位图的容量
     *
     * @return 当前位图的容量
     */
    public int getCapacity() {
        return getSize(); // 当前位图的容量等于记录总数
    }

    /**
     * 收缩位图容量
     * 如果使用记录的数量显著低于总容量，缩小位图
     */
    public void shrinkCapacity() {
        // 只有在使用的记录数显著少于总容量时，才考虑收缩
        if (usedCount < bitMap.length * 32 / 4 && bitMap.length > 1) { // 当使用数量低于25%且位图大于1
            int newCapacity = (bitMap.length / 2); // 新容量
            int[] newBitMap = new int[newCapacity]; // 创建新的位图
            System.arraycopy(bitMap, 0, newBitMap, 0, newBitMap.length);
            bitMap = newBitMap; // 更新位图引用
        }
    }

    /**
     * 获取当前位图的二进制字符串表示（调试用）
     *
     * @return 位图的字符串表示
     */
    public String getBitMapString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bitMap.length; i++) {
            sb.append(String.format("%32s", Integer.toBinaryString(bitMap[i])).replace(' ', '0')).append(" ");
        }
        return sb.toString();
    }

    /**
     * 查找所有标记为1的记录位置
     *
     * @return 包含所有已使用记录位置的列表
     */
    public List<Integer> findUsedRecordPositions() {
        List<Integer> usedPositions = new ArrayList<>();
        for (int i = 0; i < bitMap.length; i++) {
            for (int j = 0; j < 32; j++) {
                if ((bitMap[i] & (1 << j)) != 0) { // 检查第 j 位是否为1
                    usedPositions.add(i * 32 + j); // 计算全局的记录索引
                }
            }
        }
        return usedPositions; // 返回所有已使用位置的列表
    }
}
