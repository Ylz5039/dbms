package com.structuer.func;

import com.parser.ast.expression.BinaryOperatorExpression;
import com.parser.ast.expression.Expression;
import com.parser.ast.expression.PolyadicOperatorExpression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.expression.primary.literal.LiteralString;
import com.parser.ast.fragment.Limit;
import com.parser.ast.fragment.tableref.TableRefFactor;
import com.parser.ast.fragment.tableref.TableReference;
import com.parser.ast.stmt.dml.DMLSelectStatement;
import com.structuer.DataBase;
import com.structuer.Filed;
import com.structuer.Result;
import com.structuer.Table;

import java.util.ArrayList;
import java.util.List;

import static com.parser.ast.expression.Expression.*;

public class QueryUtil {

    /**
     * 执行 SELECT 查询并返回结果。
     *
     * @param selectStatement DMLSelectStatement 对象，包含查询信息
     * @param database        数据库对象
     * @return 查询结果的 Result 对象
     */

    public static Result executeSelect(DMLSelectStatement selectStatement, DataBase database) {
        List<List<String>> results = new ArrayList<>();
        String tableName = getTableName(selectStatement);
        Table table = database.getTable(tableName);
        if (table == null) {
            System.out.println(tableName + "表不存在");
            return new Result(null, null, null, "false");
        }

        // 获取 WHERE 条件
        Expression whereCondition = selectStatement.getWhere();
        // 获取 LIMIT 条件
        Limit limit = selectStatement.getLimit();

        // 遍历表中的记录，检查是否满足条件
        for (List<String> record : table.getRecords()) {
            if (matchesCondition(record, whereCondition, table)) {
                results.add(record); // 如果满足条件，添加记录到结果列表
            }
        }
        // 应用 LIMIT
        if (limit != null) {
            int offset = (int) limit.getOffset(); // 获取偏移量
            int size = (int) limit.getSize(); // 获取大小
            results = applyLimit(results, offset, size);
        }

        List<Filed> convertedFiledList = convertFiledList(table.getFiledList());
        Result result = new Result(tableName, convertedFiledList, convertRecords(results), "true");
        return result;
    }


    /**
     * 判断记录是否满足条件。
     *
     * @param record         要检查的记录
     * @param whereCondition 更新条件
     * @param table          表对象，用于获取字段索引
     * @return 是否满足条件
     */
    private static boolean matchesCondition(List<String> record, Expression whereCondition, Table table) {
        if (whereCondition == null) {
            return true; // 如果没有条件，默认返回 true
        }

        // 处理二元操作符
        if (whereCondition instanceof BinaryOperatorExpression condition) {
            int precedence = condition.getPrecedence();
            if (precedence == Expression.PRECEDENCE_COMPARISION) {
                System.out.println("单条件判断");
                return evaluateComparisonCondition(record, condition, table);
            } else if (precedence == Expression.PRECEDENCE_LOGICAL_AND) {
                System.out.println("是AND");
                return evaluateLogicalCondition(record, condition, table, true);
            } else if (precedence == Expression.PRECEDENCE_LOGICAL_OR) {
                System.out.println("是OR");
                return evaluateLogicalCondition(record, condition, table, false);
            }
        }

        System.out.println("没有读取到条件");
        return false; // 其他情况返回 false
    }

    // 处理比较条件的逻辑
    private static boolean evaluateComparisonCondition(List<String> record, Expression whereCondition, Table table) {
        if (whereCondition instanceof BinaryOperatorExpression) {
            BinaryOperatorExpression condition = (BinaryOperatorExpression) whereCondition;
            String leftFieldName = ((Identifier) condition.getLeftOprand()).getIdText();
            String expectedValue = convertToString(condition.getRightOprand()); // 获取右操作数的值
            int index = table.getFiledIndex(leftFieldName);
            if (index != -1) {
                String leftValue = record.get(index);
                return leftValue.equals(expectedValue); // 只支持 "=" 操作符
            }
        }
        System.out.println();
        return false; // 如果不匹配，返回 false
    }

    // 处理逻辑条件的通用方法
    private static boolean evaluateLogicalCondition(List<String> record, Expression whereCondition, Table table, boolean isAnd) {
        if (whereCondition instanceof BinaryOperatorExpression condition) {
            boolean leftResult = matchesCondition(record, condition.getLeftOprand(), table);
            System.out.println("左条件："+leftResult);
            boolean rightResult = matchesCondition(record, condition.getRightOprand(), table);
            System.out.println("右条件："+rightResult);
            return isAnd ? (leftResult && rightResult) : (leftResult || rightResult);
        }
        System.out.println("没有读取到条件");
        return false;
    }

    private static String convertToString(Expression expression) {
        if (expression instanceof LiteralString) {
            byte[] data = ((LiteralString) expression).getData();
            return new String(data); // 直接使用 String 构造函数
        }
        return ""; // 如果不是 LiteralString，返回空字符串或其他适当的处理
    }

    private static String getTableName(DMLSelectStatement selectStatement) {
        List<TableReference> tableRefs = selectStatement.getTables().getList();
        if (tableRefs != null && !tableRefs.isEmpty()) {
            TableRefFactor tableRef = (TableRefFactor) tableRefs.get(0);
            Identifier tableIdentifier = tableRef.getTable();
            return tableIdentifier.getIdText(); // 返回表名
        }
        throw new IllegalArgumentException("No table found in select statement.");
    }

    private static List<Filed> convertFiledList(List<Filed> filedList) {
        List<Filed> convertedList = new ArrayList<>();
        for (Filed filed : filedList) {
            convertedList.add(new Filed(filed.getFiledName(), filed.getFiledType(), filed.isNotNull())); // 这里可以进行必要的转换
        }
        return convertedList;
    }

    private static List<List<String>> convertRecords(List<List<String>> records) {
        List<List<String>> convertedRecords = new ArrayList<>();
        for (List<String> record : records) {
            List<String> convertedRecord = new ArrayList<>();
            for (String data : record) {
                convertedRecord.add(convertLiteralString(data)); // 转换每个字段
            }
            convertedRecords.add(convertedRecord);
        }
        return convertedRecords;
    }

    private static String convertLiteralString(String data) {
        // 假设 data 是 LiteralString 对象的字符串表示，实际应从 LiteralString 对象获取数据
        // 这里需要将数据转换为实际的字符值
        StringBuilder sb = new StringBuilder();
        for (char ch : data.toCharArray()) {
            sb.append((char) ch); // 进行 ASCII 转换
        }
        return sb.toString();
    }
    private static List<List<String>> applyLimit(List<List<String>> records, int offset, int size) {
        int start = Math.min(offset, records.size()); // 确保不超出范围
        int end = Math.min(start + size, records.size()); // 计算结束位置
        return records.subList(start, end); // 返回指定范围的记录
    }



}
