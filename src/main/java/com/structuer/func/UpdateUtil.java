package com.structuer.func;

import com.parser.ast.expression.BinaryOperatorExpression;
import com.parser.ast.expression.Expression;
import com.parser.ast.expression.primary.Identifier;
import com.parser.ast.expression.primary.literal.LiteralString;
import com.parser.ast.fragment.tableref.TableRefFactor;
import com.parser.ast.fragment.tableref.TableReference;
import com.parser.ast.stmt.dml.DMLUpdateStatement;
import com.parser.util.Pair;
import com.structuer.DataBase;
import com.structuer.Result;
import com.structuer.Table;

import java.util.List;

import static com.parser.ast.expression.Expression.*;

public class UpdateUtil {

    public Result updateTable(DMLUpdateStatement updateStatement, DataBase dataBase) {
        String tableName =getTableName(updateStatement);
        List<Pair<Identifier, Expression>> values = updateStatement.getValues();
        Expression whereCondition = updateStatement.getWhere();

        Table table = dataBase.getTable(tableName);
        if (table == null) {
            System.out.println("Table not found: " + tableName);
            return new Result(null,null,null,"false");

        }

        for (List<String> record : table.getRecords()) {
            if (matchesCondition(record, whereCondition, table)) {
                for (Pair<Identifier, Expression> value : values) {
                    String fieldName = value.getKey().getIdText();
                    String newValue = convertToString(value.getValue());
                    int index = table.getFiledIndex(fieldName);
                    if (index != -1) {
                        record.set(index, newValue);
                    }
                }
            }
        }
        return new Result(null,null,null,"true");

    }

    private static boolean matchesCondition(List<String> record, Expression whereCondition, Table table) {
        if (whereCondition == null) {
            return true;
        }

        int precedence = whereCondition.getPrecedence();

        if (precedence == PRECEDENCE_COMPARISION) {
            return evaluateComparisonCondition(record, whereCondition, table);
        } else if (precedence == PRECEDENCE_LOGICAL_AND) {
            return evaluateAndCondition(record, whereCondition, table);
        } else if (precedence == PRECEDENCE_LOGICAL_OR) {
            return evaluateOrCondition(record, whereCondition, table);
        }

        return false;
    }

    private static boolean evaluateAndCondition(List<String> record, Expression whereCondition, Table table) {
        if (whereCondition instanceof BinaryOperatorExpression) {
            BinaryOperatorExpression andCondition = (BinaryOperatorExpression) whereCondition;
            boolean leftMatches = matchesCondition(record, andCondition.getLeftOprand(), table);
            boolean rightMatches = matchesCondition(record, andCondition.getRightOprand(), table);
            return leftMatches && rightMatches;
        }
        return false;
    }

    private static boolean evaluateOrCondition(List<String> record, Expression whereCondition, Table table) {
        if (whereCondition instanceof BinaryOperatorExpression) {
            BinaryOperatorExpression orCondition = (BinaryOperatorExpression) whereCondition;
            boolean leftMatches = matchesCondition(record, orCondition.getLeftOprand(), table);
            boolean rightMatches = matchesCondition(record, orCondition.getRightOprand(), table);
            return leftMatches || rightMatches;
        }
        return false;
    }

    private static boolean evaluateComparisonCondition(List<String> record, Expression whereCondition, Table table) {
        if (whereCondition instanceof BinaryOperatorExpression) {
            BinaryOperatorExpression condition = (BinaryOperatorExpression) whereCondition;
            String leftFieldName = ((Identifier) condition.getLeftOprand()).getIdText();
            String expectedValue = convertToString(condition.getRightOprand());
            int index = table.getFiledIndex(leftFieldName);
            if (index != -1) {
                String leftValue = record.get(index);
                return leftValue.equals(expectedValue);
            }
        }
        return false;
    }

    private static String convertToString(Expression expression) {
        if (expression instanceof LiteralString) {
            byte[] data = ((LiteralString) expression).getData();
            StringBuilder sb = new StringBuilder();
            for (byte b : data) {
                sb.append((char) b);
            }
            return sb.toString();
        }
        return "";
    }
    private String getTableName(DMLUpdateStatement updateStatement) {
        // 获取表引用列表
        List<TableReference> tableRefs = updateStatement.getTableRefs().getList();
        if (tableRefs != null && !tableRefs.isEmpty()) {
            // 假设你只处理第一个表
            TableRefFactor tableRef = (TableRefFactor) tableRefs.get(0);
            Identifier tableIdentifier = tableRef.getTable();
            return tableIdentifier.getIdText(); // 返回表名
        }
        throw new IllegalArgumentException("No table found in update statement.");
    }

}
