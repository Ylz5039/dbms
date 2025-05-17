package test;

import com.st.util.DataBase;
import com.st.util.Filed;
import com.st.util.Table;
import com.st.server.IbdFileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 创建测试数据库
        DataBase testDatabase = new DataBase("testDB1");

        // 创建字段
        List<Filed> fieldList = new ArrayList<>();  // 更新为 Field
        fieldList.add(new Filed("id", "int", true));
        fieldList.add(new Filed("name", "varchar(255)", false));

        // 创建表
        Table testTable = new Table("testTable", fieldList, 10);
        testDatabase.addTable(testTable);

        // 添加一些记录
        List<String> record1 = new ArrayList<>();
        record1.add("1");
        record1.add("Alice");
        testTable.insertRecord(record1);

        List<String> record2 = new ArrayList<>();
        record2.add("2");
        record2.add("Bob");
        testTable.insertRecord(record2);

        for (Table table :testDatabase.getTableList()) {
            System.out.println("表名: " + table.getTableName());
            System.out.println("记录: " + table.getRecords());
        }


        // 保存数据库到 .ibd 文件
        try {
            IbdFileHandler.saveToIbdFile(testDatabase);
            System.out.println("数据库已保存到文件");
        } catch (IOException e) {
            System.err.println("保存数据库时出错: " + e.getMessage());
        }

        // 从 .ibd 文件加载数据库
        try {
            String filePath = IbdFileHandler.findIbdFileByDatabaseName(testDatabase.getName());
            DataBase loadedDatabase = IbdFileHandler.loadFromIbdFile(filePath);
            System.out.println("成功加载数据库: " + loadedDatabase.getName());

            // 输出加载的表和记录
            for (Table table : loadedDatabase.getTableList()) {
                System.out.println("表名: " + table.getTableName());
                System.out.println("记录: " + table.getRecords());
            }
        } catch (IOException e) {
            System.err.println("加载数据库时出错: " + e.getMessage());
        }

        // 查找 .ibd 文件
        String foundFilePath = IbdFileHandler.findIbdFileByDatabaseName("testDB");
        if (foundFilePath != null) {
            System.out.println("找到的文件路径: " + foundFilePath);
        } else {
            System.out.println("未找到文件");
        }
    }
}
