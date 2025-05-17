package com.structuer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 表示数据库，包含多个表。
 */
public class DataBase implements Serializable {
	private String name; // 数据库名称
	private List<Table> tableList; // 表列表

	public DataBase(String name) {
		this.name = name;
		this.tableList = new ArrayList<>();
	}
	/**
	 * 添加表到数据库。
	 *
	 * @param table 表
	 */
	public void addTable(Table table) {
		this.tableList.add(table);
	}
	/**
	 * 获取表列表。
	 *
	 * @return 表列表
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

	public List<String> getTablesName(){
		List<String> TablesName = null;
		for (Table table:tableList) {
			TablesName.add(table.getTableName());
		}

		return TablesName;
	}

	public Table getTable(String tableName) {
		List<Table> tables=getTableList();
		for (Table table:tables) {
			if(table.getTableName().equals(tableName))
				return table;
		}
		return null;
	}
}