package com.structuer.func;

import com.parser.ast.expression.BinaryOperatorExpression;
import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.expression.primary.literal.LiteralString;
import com.parser.ast.stmt.dml.DMLDeleteStatement;
import com.structuer.DataBase;
import com.structuer.Result;
import com.structuer.Table;

import java.util.List;

public class DeleteUtil {

    /**
     * 执行 DELETE 删除操作。
     *
     * @param deleteStatement DMLDeleteStatement 对象，包含删除信息
     * @param database        数据库对象
     * @return 删除成功的记录数量
     */
    public static Result executeDelete(DMLDeleteStatement deleteStatement, DataBase database) {
        // 获取表名
        String tableName = getTableName(deleteStatement);
        Table table = database.getTable(tableName);

        if (table == null) {
            System.out.println(tableName + " 表不存在");
            return new Result(null, null, null, "false");
        }

        // 获取 WHERE 条件
        Expression whereCondition = deleteStatement.getWhereCondition();
        int deletedCount = 0;

        // 遍历表中的记录，检查是否满足条件
        for (int i = 0; i < table.getRecords().size(); i++) {
            List<String> record = table.getRecord(i);
            if (matchesCondition(record, whereCondition, table)) {
                table.deleteRecord(i); // 删除记录
                deletedCount++;
                i--; // 因为删除了记录，索引需要向前调整
            }
        }
        System.out.println(table.getRecords());
        return new Result(null, null, null, String.valueOf(deletedCount));
    }

    /**
     * 判断记录是否满足条件。
     *
     * @param record         要检查的记录
     * @param whereCondition 删除条件
     * @param table          表对象，用于获取字段索引
     * @return 是否满足条件
     */
    private static boolean matchesCondition(List<String> record, Expression whereCondition, Table table) {
        if (whereCondition == null) {
            return true; // 如果没有条件，默认返回 true
        }

        int precedence = whereCondition.getPrecedence();

        if (precedence == Expression.PRECEDENCE_COMPARISION) {
            return evaluateComparisonCondition(record, whereCondition, table);
        } else if (precedence == Expression.PRECEDENCE_LOGICAL_AND) {
            return evaluateLogicalCondition(record, whereCondition, table, true);
        } else if (precedence == Expression.PRECEDENCE_LOGICAL_OR) {
            return evaluateLogicalCondition(record, whereCondition, table, false);
        }

        return false; // 其他情况返回 false
    }

    private static boolean evaluateComparisonCondition(List<String> record, Expression whereCondition, Table table) {
        if (whereCondition instanceof BinaryOperatorExpression) {
            BinaryOperatorExpression condition = (BinaryOperatorExpression) whereCondition;
            String leftFieldName = ((Identifier) condition.getLeftOprand()).getIdText();
            String expectedValue = convertToString(condition.getRightOprand());
            int index = table.getFiledIndex(leftFieldName);
            if (index != -1 && record != null) { // 检查 record 是否为 null
                String leftValue = record.get(index);
                return leftValue.equals(expectedValue); // 只支持 "=" 操作符
            }
        }
        return false; // 如果不匹配，返回 false
    }

    private static boolean evaluateLogicalCondition(List<String> record, Expression whereCondition, Table table, boolean isAnd) {
        if (whereCondition instanceof BinaryOperatorExpression) {
            BinaryOperatorExpression condition = (BinaryOperatorExpression) whereCondition;
            boolean leftResult = matchesCondition(record, condition.getLeftOprand(), table);
            boolean rightResult = matchesCondition(record, condition.getRightOprand(), table);
            return isAnd ? (leftResult && rightResult) : (leftResult || rightResult);
        }
        return false; // 如果不匹配，返回 false
    }

    private static String getTableName(DMLDeleteStatement deleteStatement) {
        List<Identifier> tableNames = deleteStatement.getTableNames();
        if (tableNames.isEmpty()) {
            throw new IllegalArgumentException("No table names found in delete statement.");
        }
        return tableNames.get(0).getIdText(); // 返回第一个表名
    }

    private static String convertToString(Expression expression) {
        if (expression instanceof LiteralString) {
            byte[] data = ((LiteralString) expression).getData();
            return new String(data); // 直接使用 String 构造函数
        }
        return ""; // 如果不是 LiteralString，返回空字符串或其他适当的处理
    }
}
