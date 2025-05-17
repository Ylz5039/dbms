package com.structuer.func;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.fragment.ddl.ColumnDefinition;
import com.parser.ast.stmt.ddl.DDLCreateTableStatement;
import com.parser.util.Pair;
import com.structuer.DataBase;
import com.structuer.Filed;
import com.structuer.Result;
import com.structuer.Table;
import com.test.ALLDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 工具类，根据DDLCreateTableStatement创建新的Table。
 */
public class TableCreationUtil {

    /**
     * 根据DDLCreateTableStatement创建新的Table并添加到DataBase。
     *
     * @param ddlStatement  DDL语句
     * @param database      目标数据库
     * @param initialCapacity 表的初始容量
     * @return 如果表成功创建则返回true，如果表已存在则返回false
     */
    public static Result createTableFromDDL(DDLCreateTableStatement ddlStatement, DataBase database, int initialCapacity) throws IOException {
        String tableName = ddlStatement.getTable().getIdText();  // 获取表名
        // 检查是否已经有同名的表
        for (Table table : database.getTableList()) {
            if (table.getTableName().equalsIgnoreCase(tableName)) {
                if(ddlStatement.isIfNotExists()){
                    System.out.println("表 " + tableName + " 已经存在，无法重复创建。");
                    return new Result(null,null,null,"true"); // 表已存在，返回false
                }
                else {
                    System.out.println("表 " + tableName + " 已经存在，无法重复创建。");
                    return new Result(null,null,null,"false"); // 表已存在，返回false
                }

            }
        }


        // 创建字段列表
        List<Filed> filedList = new ArrayList<>();
        for (Pair<Identifier, ColumnDefinition> colDefPair : ddlStatement.getColDefs()) {
            Identifier colName = colDefPair.getKey();
            ColumnDefinition colDef = colDefPair.getValue();

            // 创建Filed对象
            Filed filed = new Filed(colName.getIdText(), colDef.getDataType().toString(), colDef.isNotNull());
            filedList.add(filed);  // 添加到字段列表
        }

        // 创建新表对象
        Table newTable = new Table(tableName, filedList, initialCapacity);

        // 将新表添加到数据库
        ALLDatabase.dataBase.addTable(newTable);
        IbdFileHandler.saveToIbdFile(ALLDatabase.dataBase);
        System.out.println("表 " + tableName + " 已成功创建并添加到数据库 " + database.getName());
        return new Result(null,null,null,"true");  // 表成功创建
    }
}
