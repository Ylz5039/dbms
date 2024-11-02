package com.st.util;

import java.util.ArrayList;
import java.util.List;

public class RecordAllocationTable {
    private int[] bitMap; // 使用整数数组作为位图，每个 int 包含 32 个记录的状态

    // 构造函数，初始化分配表，位图大小由 initialCapacity 决定
    public RecordAllocationTable(int initialCapacity) {
        int numOfInts = (initialCapacity + 31) / 32; // 每个 int 可以存储 32 位记录状态
        this.bitMap = new int[numOfInts];
    }

    // 查找第一个空闲位置，返回记录位置的索引
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

    // 标记某个记录位置为已使用
    public void markRecordPositionUsed(int position) {
        int index = position / 32;
        int bit = position % 32;
        bitMap[index] |= (1 << bit); // 使用位或操作标记某位为 1
    }

    // 释放某个记录位置
    public void releaseRecordPosition(int position) {
        int index = position / 32;
        int bit = position % 32;
        bitMap[index] &= ~(1 << bit); // 使用位与操作将某位置为 0
    }

    // 扩展位图容量
    public void expandCapacity() {
        int[] newBitMap = new int[bitMap.length * 2]; // 扩展位图容量为原来的两倍
        System.arraycopy(bitMap, 0, newBitMap, 0, bitMap.length);
        bitMap = newBitMap;
    }

    // 获取位图的当前大小（记录总数）
    public int getSize() {
        return bitMap.length * 32; // 每个 int 存储 32 个记录状态
    }

    // 获取当前位图的二进制字符串表示（调试用）
    public String getBitMapString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bitMap.length; i++) {
            sb.append(String.format("%32s", Integer.toBinaryString(bitMap[i])).replace(' ', '0')).append(" ");
        }
        return sb.toString();
    }
    // 查找所有标记为1的记录位置
    public List<Integer> findUsedRecordPositions() {
        List<Integer> usedPositions = new ArrayList<>();
        for (int i = 0; i < bitMap.length; i++) {
            for (int j = 0; j < 32; j++) {
                if ((bitMap[i] & (1 << j)) != 0) { // 检查第 j 位是否为1
                    usedPositions.add(i * 32 + j); // 计算全局的记录索引
                }
            }
        }
        return usedPositions;
    }
}
