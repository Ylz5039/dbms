package com.st.util;

import java.util.ArrayList;
import java.util.List;

/**
 * ���ݿ⹤���࣬�ṩ������ӺͲ�ѯ������
 */
public class DBUtils {

	/**
	 * ���������������б�����Ӳ���
	 *
	 * @param table1 ��Ҫ���ӵĵ�һ�ű�
	 * @param table2 ��Ҫ���ӵĵڶ��ű�
	 * @param conditions ���������б�
	 * @return ���Ӻ�Ľ����
	 */
	public static List<List<String>> join(Table table1, Table table2, List<JoinCondition> conditions) {
		List<List<String>> result = new ArrayList<>();

		// ������һ�ű��ÿһ����¼
		for (List<String> currentRecord : table1.getRecords()) {
			// �����ڶ��ű��ÿһ����¼
			for (List<String> otherRecord : table2.getRecords()) {
				boolean allConditionsMatched = true;

				// ���������������
				for (JoinCondition condition : conditions) {
					int thisFieldIndex = getFieldIndex(table1, condition.getThisField());
					int otherFieldIndex = getFieldIndex(table2, condition.getOtherField());

					if (thisFieldIndex == -1 || otherFieldIndex == -1) {
						throw new IllegalArgumentException("Join field not found");
					}

					String thisValue = currentRecord.get(thisFieldIndex);
					String otherValue = otherRecord.get(otherFieldIndex);

					// �������һ������ƥ�䣬������Լ�¼
					if (!thisValue.equals(otherValue)) {
						allConditionsMatched = false;
						break;
					}
				}

				// �������������ƥ�䣬�ϲ����ű�ļ�¼
				if (allConditionsMatched) {
					List<String> combinedRecord = new ArrayList<>(currentRecord);
					combinedRecord.addAll(otherRecord);
					result.add(combinedRecord);
				}
			}
		}

		return result; // �������ӽ��
	}

	/**
	 * ��ȡ�ֶε�����
	 *
	 * @param table    �����
	 * @param fieldName �ֶ���
	 * @return �ֶ�������-1��ʾ�ֶ�δ�ҵ�
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
	 * �������
	 *
	 * @param baseTable ������
	 * @param tables    ������Ҫ���ӵı�
	 * @param conditions ���������б�
	 * @return ���Ӻ�Ľ����
	 */
	public static List<List<String>> multiJoin(Table baseTable, List<Table> tables, List<List<JoinCondition>> conditions) {
		// ��ʼ�������Ϊ������
		List<List<String>> result = baseTable.getRecords();

		// �������Ӷ����
		for (int i = 0; i < tables.size(); i++) {
			Table otherTable = tables.get(i);
			List<JoinCondition> currentConditions = conditions.get(i);

			// ���ӵ�ǰ�������������
			result = joinTables(result, otherTable, currentConditions, baseTable.getFiledList());
		}

		return result;
	}

	/**
	 * ���ӽ������������
	 *
	 * @param result      ��ǰ�����
	 * @param otherTable  ��Ҫ���ӵ�������
	 * @param conditions  ��������
	 * @param baseFiledList �������ֶ��б�
	 * @return ���Ӻ���½����
	 */
	public static List<List<String>> joinTables(List<List<String>> result, Table otherTable, List<JoinCondition> conditions, List<Filed> baseFiledList) {
		List<List<String>> newResult = new ArrayList<>();

		// ������ǰ�������ÿһ����¼
		for (List<String> currentRecord : result) {
			// ������Ҫ���ӵı��ÿһ����¼
			for (List<String> otherRecord : otherTable.getRecords()) {
				boolean allConditionsMatched = true;

				// ���������������
				for (JoinCondition condition : conditions) {
					int currentFieldIndex = getFieldIndex(otherTable, condition.getOtherField());
					int resultFieldIndex = getFieldIndex(otherTable, condition.getThisField());

					if (resultFieldIndex == -1 || currentFieldIndex == -1) {
						throw new IllegalArgumentException("Join field not found");
					}

					String currentValue = currentRecord.get(resultFieldIndex);
					String otherValue = otherRecord.get(currentFieldIndex);

					// �������һ������ƥ�䣬������Լ�¼
					if (!currentValue.equals(otherValue)) {
						allConditionsMatched = false;
						break;
					}
				}

				// �������������ƥ�䣬�ϲ����ű�ļ�¼
				if (allConditionsMatched) {
					List<String> combinedRecord = new ArrayList<>(currentRecord);
					combinedRecord.addAll(otherRecord);
					newResult.add(combinedRecord);
				}
			}
		}

		return newResult; // �������ӽ��
	}

}
