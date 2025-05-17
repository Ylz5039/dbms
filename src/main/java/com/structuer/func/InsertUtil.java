package com.structuer.func;

import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.expression.primary.RowExpression;
import com.parser.ast.expression.primary.literal.LiteralString;
import com.parser.ast.stmt.dml.DMLInsertStatement;
import com.structuer.DataBase;
import com.structuer.Filed;
import com.structuer.Result;
import com.structuer.Table;
import com.test.ALLDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 插入工具类，用于执行插入操作。
 */
public class InsertUtil {

    /**
     * 执行插入操作，将数据插入到指定的数据库表中。
     *
     * @param insertStatement DML插入语句
     * @param database        数据库对象
     */
    public static Result executeInsert(DMLInsertStatement insertStatement, DataBase database) throws IOException {
        // 获取目标表名
        Identifier tableIdentifier = insertStatement.getTable();
        String tableName = tableIdentifier.getIdText();

        // 根据表名获取目标表
        Table table = findTable(database, tableName);
        if (table == null) {
            System.out.println("表 " + tableName + " 不存在。");
            return new Result(null,null,null,"false");
        }

        // 获取要插入的列名
        List<Identifier> columnNames = insertStatement.getColumnNameList();

        // 处理每一行要插入的值
        for (RowExpression row : insertStatement.getRowList()) {
            List<String> values = new ArrayList<>();

            for (int i = 0; i < columnNames.size(); i++) {
                // 获取列的值并转换为字符串
                String value = convertRowExpressionToString(row.getRowExprList().get(i));
                values.add(value);
            }

            // 校验值与字段定义是否匹配
            validateValues(table.getFiledList(), values);

            // 调用表的插入方法
            table.insertRecord(values);

        }
        IbdFileHandler.saveToIbdFile(ALLDatabase.dataBase);
        return new Result(null,null,null,"true");
    }

    /**
     * 查找数据库中的表。
     *
     * @param database  数据库对象
     * @param tableName 表名
     * @return 找到的表对象，或null
     */
    public static Table findTable(DataBase database, String tableName) {
        for (Table table : database.getTableList()) {
            if (table.getTableName().equalsIgnoreCase(tableName)) {
                return table;
            }
        }
        return null; // 未找到表
    }

    /**
     * 校验插入值是否符合字段定义。
     *
     * @param fields 字段列表
     * @param values 插入值列表
     */
    private static void validateValues(List<Filed> fields, List<String> values) {
        if (fields.size() != values.size()) {
            throw new IllegalArgumentException("插入的值数量与字段数量不匹配。");
        }

        for (int i = 0; i < fields.size(); i++) {
            Filed field = fields.get(i);
            String value = values.get(i);

            // 如果字段不允许为空，且值为空，则抛出异常
            if (field.isNotNull() && (value == null || value.isEmpty())) {
                throw new IllegalArgumentException("字段 " + field.getFiledName() + " 不能为空。");
            }

            // 类型验证逻辑（示例）
            // if (field.getFiledType().equals("INT") && !isInteger(value)) {
            //     throw new IllegalArgumentException("字段 " + field.getFiledName() + " 的值必须是整数。");
            // }
        }
    }

    // 将 RowExpression 转换为字符串
    private static String convertRowExpressionToString(Object rowExpr) {
        if (rowExpr instanceof LiteralString) {
            byte[] data = ((LiteralString) rowExpr).getData();
            return new String(data); // 将字节数组转换为字符串
        }
        return rowExpr.toString(); // 其他情况的处理
    }

    // 辅助方法：检查字符串是否为整数（可选）
    private static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
