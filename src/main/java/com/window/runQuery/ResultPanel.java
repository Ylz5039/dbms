package com.window.runQuery;

import com.test.SQLStatementTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLSyntaxErrorException;

public class ResultPanel extends JPanel {
    private JTextArea resultArea;  // 显示结果的文本区域

    public ResultPanel() {
        setLayout(new BorderLayout());
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // 添加鼠标监听器
        resultArea.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // 左键点击
                    int line = resultArea.viewToModel(e.getPoint());
                    String selectedText = getLineText(line);
                    if (!selectedText.isEmpty()) {
                        // 将选择的文本传递给 QueryManager 执行
                        // 这里可以通过一个方法或事件通知外部
                        System.out.println("选中的查询: " + selectedText);
                    }
                }
            }
        });
    }

    // 获取指定行的文本
    private String getLineText(int line) {
        try {
            int start = resultArea.getLineStartOffset(line);
            int end = resultArea.getLineEndOffset(line);
            return resultArea.getText(start, end - start).trim();
        } catch (Exception e) {
            return "";
        }
    }

    // 显示查询结果
    public QueryResult showSelectedResult(String selectedText) throws SQLSyntaxErrorException, IOException {
        // 清空现有内容
        resultArea.setText(""); // 清空文本区域

        // 将结果按“;”分隔并逐行显示
        String[] results = selectedText.split(";");
        StringBuilder displayText = new StringBuilder();

        SQLStatementTest sqlStatementTest = new SQLStatementTest();

        for (String res : results) {
            displayText.append(res.trim()).append("\n");  // 添加换行符
            String testResult = sqlStatementTest.testSQLStatement(res);
            displayText.append(testResult).append("\n");
        }

        // TODO 设置新的结果到文本区域
        resultArea.setText(displayText.toString()); // 设置新的结果

        return new QueryResult(selectedText, displayText.toString()); // 返回封装的结果
    }


    public void displayAllResults(String results) {
        resultArea.setText(results); // 或者使用 append 来添加到现有内容
    }

}
