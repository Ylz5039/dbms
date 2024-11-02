package com.st.server;

import com.st.util.DataBase;
import com.st.util.Filed;
import com.st.util.Table;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IbdFileHandler {

    // 保存数据库到 .ibd 文件
    public static void saveToIbdFile(DataBase dataBase) throws IOException {
        String filePath = "E:/" + dataBase.getName() + ".ibd";
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(filePath))) {
            // 写入数据库元数据
            out.writeUTF(dataBase.getName()); // 数据库名称
            out.writeInt(dataBase.getTableList().size()); // 表数量

            // 写入每个表的信息
            for (Table table : dataBase.getTableList()) {
                writeTableToStream(out, table);
            }
        }
    }

    // 从 .ibd 文件加载数据库
    public static DataBase loadFromIbdFile(String filePath) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(filePath))) {
            String dbName = in.readUTF();
            DataBase db = new DataBase(dbName);
            int tableCount = in.readInt();

            for (int i = 0; i < tableCount; i++) {
                Table table = readTableFromStream(in);
                db.addTable(table);
            }
            return db;
        }
    }

    // 将表写入输出流
    private static void writeTableToStream(DataOutputStream out, Table table) throws IOException {
        out.writeUTF(table.getTableName()); // 写入表名
        List<Filed> filedList = table.getFiledList();
        out.writeInt(filedList.size()); // 写入字段数量

        // 写入字段信息
        for (Filed filed : filedList) {
            out.writeUTF(filed.getFiledName());
            out.writeUTF(filed.getFiledType());
            out.writeBoolean(filed.isNotNull());
        }

        // 写入记录数据
        List<List<String>> records = table.getRecords();
        out.writeInt(records.size()); // 写入记录数量
        for (List<String> record : records) {
            if (record != null) { // 确保记录不是空的
                for (String field : record) {
                    out.writeUTF(field); // 写入字段的值
                }
            }
        }
    }


    // 从输入流读取表信息
    private static Table readTableFromStream(DataInputStream in) throws IOException {
        String tableName = in.readUTF(); // 读取表名
        int filedCount = in.readInt(); // 读取字段数量
        List<Filed> filedList = new ArrayList<>();

        for (int i = 0; i < filedCount; i++) {
            String filedName = in.readUTF();
            String filedType = in.readUTF();
            boolean isNotNull = in.readBoolean();
            filedList.add(new Filed(filedName, filedType, isNotNull));
        }

        int recordCount = in.readInt(); // 读取记录数量
        List<List<String>> records = new ArrayList<>();

        for (int i = 0; i < recordCount; i++) {
            List<String> record = new ArrayList<>();
            for (int j = 0; j < filedCount; j++) {
                record.add(in.readUTF()); // 读取每个字段的值
            }
            records.add(record);
        }

        Table table = new Table(tableName, filedList, records.size());
        for (List<String> record : records) {
            table.insertRecord(record); // 插入加载的记录
        }

        return table;
    }

    // 根据数据库名称查找 .ibd 文件
    public static String findIbdFileByDatabaseName(String dbName) {
        String filePath = "E:/" + dbName + ".ibd";
        File file = new File(filePath);
        return file.exists() ? file.getAbsolutePath() : null;
    }
}
