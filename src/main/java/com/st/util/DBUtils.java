package com.st.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库工具类，提供表的连接和查询操作。
 */
public class DBUtils {

	/**
	 * 根据连接条件进行表的连接操作
	 *
	 * @param table1 需要连接的第一张表
	 * @param table2 需要连接的第二张表
	 * @param conditions 连接条件列表
	 * @return 连接后的结果集
	 */
	public static List<List<String>> join(Table table1, Table table2, List<JoinCondition> conditions) {
		List<List<String>> result = new ArrayList<>();

		// 遍历第一张表的每一条记录
		for (List<String> currentRecord : table1.getRecords()) {
			// 遍历第二张表的每一条记录
			for (List<String> otherRecord : table2.getRecords()) {
				boolean allConditionsMatched = true;

				// 检查所有连接条件
				for (JoinCondition condition : conditions) {
					int thisFieldIndex = getFieldIndex(table1, condition.getThisField());
					int otherFieldIndex = getFieldIndex(table2, condition.getOtherField());

					if (thisFieldIndex == -1 || otherFieldIndex == -1) {
						throw new IllegalArgumentException("Join field not found");
					}

					String thisValue = currentRecord.get(thisFieldIndex);
					String otherValue = otherRecord.get(otherFieldIndex);

					// 如果有任一条件不匹配，跳过这对记录
					if (!thisValue.equals(otherValue)) {
						allConditionsMatched = false;
						break;
					}
				}

				// 如果所有条件都匹配，合并两张表的记录
				if (allConditionsMatched) {
					List<String> combinedRecord = new ArrayList<>(currentRecord);
					combinedRecord.addAll(otherRecord);
					result.add(combinedRecord);
				}
			}
		}

		return result; // 返回连接结果
	}

	/**
	 * 获取字段的索引
	 *
	 * @param table    表对象
	 * @param fieldName 字段名
	 * @return 字段索引，-1表示字段未找到
	 */
	public static int getFieldIndex(Table table, String fieldName) {
		List<Filed> filedList = table.getFiledList();
		for (int i = 0; i < filedList.size(); i++) {
			if (filedList.get(i).getFiledName().equals(fieldName)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 多表连接
	 *
	 * @param baseTable 基础表
	 * @param tables    其他需要连接的表
	 * @param conditions 连接条件列表
	 * @return 连接后的结果集
	 */
	public static List<List<String>> multiJoin(Table baseTable, List<Table> tables, List<List<JoinCondition>> conditions) {
		// 初始化结果集为基础表
		List<List<String>> result = baseTable.getRecords();

		// 依次连接多个表
		for (int i = 0; i < tables.size(); i++) {
			Table otherTable = tables.get(i);
			List<JoinCondition> currentConditions = conditions.get(i);

			// 连接当前结果集与其他表
			result = joinTables(result, otherTable, currentConditions, baseTable.getFiledList());
		}

		return result;
	}

	/**
	 * 连接结果集与其他表
	 *
	 * @param result      当前结果集
	 * @param otherTable  需要连接的其他表
	 * @param conditions  连接条件
	 * @param baseFiledList 基础表字段列表
	 * @return 连接后的新结果集
	 */
	public static List<List<String>> joinTables(List<List<String>> result, Table otherTable, List<JoinCondition> conditions, List<Filed> baseFiledList) {
		List<List<String>> newResult = new ArrayList<>();

		// 遍历当前结果集的每一条记录
		for (List<String> currentRecord : result) {
			// 遍历需要连接的表的每一条记录
			for (List<String> otherRecord : otherTable.getRecords()) {
				boolean allConditionsMatched = true;

				// 检查所有连接条件
				for (JoinCondition condition : conditions) {
					int currentFieldIndex = getFieldIndex(otherTable, condition.getOtherField());
					int resultFieldIndex = getFieldIndex(otherTable, condition.getThisField());

					if (resultFieldIndex == -1 || currentFieldIndex == -1) {
						throw new IllegalArgumentException("Join field not found");
					}

					String currentValue = currentRecord.get(resultFieldIndex);
					String otherValue = otherRecord.get(currentFieldIndex);

					// 如果有任一条件不匹配，跳过这对记录
					if (!currentValue.equals(otherValue)) {
						allConditionsMatched = false;
						break;
					}
				}

				// 如果所有条件都匹配，合并两张表的记录
				if (allConditionsMatched) {
					List<String> combinedRecord = new ArrayList<>(currentRecord);
					combinedRecord.addAll(otherRecord);
					newResult.add(combinedRecord);
				}
			}
		}

		return newResult; // 返回连接结果
	}

}
