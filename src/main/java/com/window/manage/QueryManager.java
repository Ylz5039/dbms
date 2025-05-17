package com.window.manage;

import com.structuer.Filed;
import com.structuer.Table;
import com.test.ALLDatabase;
import com.window.runQuery.QueryResult;
import com.window.runQuery.ResultPanel;
import com.window.ui.RightPanel;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryManager {
    private ResultPanel resultPanel;
    private Map<String, Map<String, String>> databaseQueries;

    public QueryManager(ResultPanel resultPanel) {
        this.resultPanel = resultPanel;
        databaseQueries = new HashMap<>();
    }

    public String runQuery(String queryText) throws SQLSyntaxErrorException, IOException {
        if (queryText == null || queryText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "查询为空！", "运行失败", JOptionPane.ERROR_MESSAGE);
            return "";
        }

        // 获取分割后的语句数组
        String[] selectedTexts = getSelectedText(queryText);

        // 创建一个 StringBuilder 来累积所有的结果
        StringBuilder allResults = new StringBuilder();

        // 检查是否有有效的语句
        for (String selectedText : selectedTexts) {
            if (!selectedText.trim().isEmpty()) {
                System.out.println(selectedText);
                // 将每个结果累积到 allResults 中
                QueryResult queryResult = resultPanel.showSelectedResult(selectedText);
                allResults.append(queryResult.getDisplayText()).append("\n"); // 获取显示文本
            }
        }

        // 将所有结果显示到结果面板
        resultPanel.displayAllResults(allResults.toString());

        // 保存查询内容为.txt文件
        saveQueryToFile(queryText);
        return queryText;
    }



    private String[] getSelectedText(String queryText) {
        // 检查输入是否为空
        if (queryText == null || queryText.trim().isEmpty()) {
            return new String[0]; // 返回空数组
        }

        // 根据分隔符“;”将文本拆分成语句
        String[] parts = queryText.split(";");

        // 去除每个语句的前后空格
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        return parts; // 返回包含所有语句的数组
    }

    private void saveQueryToFile(String queryText) {
        String directoryPath = "D:\\dbmsRun";
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("成功创建目录: " + directoryPath);
            } else {
                System.out.println("创建目录失败: " + directoryPath);
                return;
            }
        }

        String fileName = "query_" + System.currentTimeMillis() + ".txt";
        File file = new File(directoryPath + File.separator + fileName);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(queryText);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "保存查询内容时发生错误！", "保存失败", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public boolean saveQuery(String dbName, String queryName, String queryText, boolean isUpdating) {
        if (isUpdating) {
            if (queryExists(dbName, queryName)) {
                databaseQueries.get(dbName).put(queryName, queryText);
                JOptionPane.showMessageDialog(null, "查询 '" + queryName + "' 的内容已更新到 " + dbName + "！", "保存成功", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "查询 '" + queryName + "' 不存在，无法更新！", "保存失败", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        if (queryExists(dbName, queryName)) {
            JOptionPane.showMessageDialog(null, "查询名 '" + queryName + "' 已存在！", "保存失败", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        databaseQueries.putIfAbsent(dbName, new HashMap<>());
        databaseQueries.get(dbName).put(queryName, queryText);
        JOptionPane.showMessageDialog(null, "查询 '" + queryName + "' 已保存到 " + dbName + "！", "保存成功", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    public String getQuery(String dbName, String queryName) {
        Map<String, String> queries = databaseQueries.get(dbName);
        return queries != null ? queries.get(queryName) : null;
    }

    public boolean queryExists(String dbName, String queryName) {
        return databaseQueries.containsKey(dbName) && databaseQueries.get(dbName).containsKey(queryName);
    }

    public boolean deleteQuery1(String dbName, String queryName) {
        if (databaseQueries.containsKey(dbName)) {
            Map<String, String> queries = databaseQueries.get(dbName);
            if (queries.containsKey(queryName)) {
                queries.remove(queryName);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "查询 '" + queryName + "' 不存在！", "删除失败", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    public boolean renameQuery(String dbName, String oldQueryName, String newQueryName) {
        if (!queryExists(dbName, oldQueryName)) {
            JOptionPane.showMessageDialog(null, "查询 '" + oldQueryName + "' 不存在，无法重命名！", "重命名失败", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (queryExists(dbName, newQueryName)) {
            JOptionPane.showMessageDialog(null, "查询名 '" + newQueryName + "' 已存在，无法重命名！", "重命名失败", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String queryContent = getQuery(dbName, oldQueryName);
        if (queryContent != null) {
            databaseQueries.get(dbName).put(newQueryName, queryContent);
            deleteQuery1(dbName, oldQueryName);
            JOptionPane.showMessageDialog(null, "查询 '" + oldQueryName + "' 已重命名为 '" + newQueryName + "'！", "重命名成功", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

        return false;
    }

    public String getQueryText(String dbName, String queryName) {
        // 检查数据库是否存在
        if (databaseQueries.containsKey(dbName)) {
            Map<String, String> dbQueries = databaseQueries.get(dbName);
            // 检查查询名是否存在
            if (dbQueries.containsKey(queryName)) {
                return dbQueries.get(queryName);
            } else {
                System.out.println("查询名 " + queryName + " 不存在于数据库 " + dbName + " 中。");
            }
        } else {
            System.out.println("数据库 " + dbName + " 不存在。");
        }
        return ""; // 如果找不到，返回空字符串
    }


}