package com.st.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ��ʾ���ݿ⣬���������
 */
public class DataBase implements Serializable {
	private String name; // ���ݿ�����
	private List<Table> tableList; // ���б�

	public DataBase(String name) {
		this.name = name;
		this.tableList = new ArrayList<>();
	}
	/**
	 * ��ӱ����ݿ⡣
	 *
	 * @param table ��
	 */
	public void addTable(Table table) {
		this.tableList.add(table);
	}
	/**
	 * ��ȡ���б�
	 *
	 * @return ���б�
	 */
	public List<Table> getTableList() {
		return tableList;
	}
	@Override
	public String toString() {
		return "DataBase{" +
				"name='" + name + '\'' +
				", tableList=" + tableList +
				'}';
	}

	public String getName() {
		return this.name;
	}
}
