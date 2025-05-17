package com.window.test;

import com.structuer.func.IbdFileHandler;
import com.window.manage.QueryManager;
import com.window.manage.TableManager;
import com.window.runQuery.ResultPanel;
import com.window.ui.LeftPanel;
import com.window.ui.MenuBarBuilder;
import com.window.ui.RightPanel;

import java.io.IOException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;

import javax.swing.*;
import java.awt.*;


public class Window extends JFrame {
    RightPanel rightPanel;
    private ResultPanel resultPanel;
    private LeftPanel leftPanel;
    QueryManager queryManager;
    TableManager tableManager;

    public Window() throws IOException {
        initWindow();
    }

    private void initWindow() throws IOException {
        setTitle("DBMS");
        setSize(700, 730);
        setLocation(300, 200);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//确定关闭窗口时退出程序

        MenuBarBuilder menuBarBuilder = new MenuBarBuilder();
        JMenuBar menuBar = menuBarBuilder.createMenuBar(this);
        setJMenuBar(menuBar);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(90);

        leftPanel = new LeftPanel(this);
        splitPane.setLeftComponent(leftPanel);

        rightPanel = new RightPanel(this, leftPanel); // 将 leftPanel 传递给 rightPanel
        splitPane.setRightComponent(rightPanel);

        // 创建一个新的 JSplitPane 来包裹 rightPanel 和 resultPanel
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        rightPanel = new RightPanel(this, leftPanel);
        mainSplitPane.setTopComponent(rightPanel);
        resultPanel = new ResultPanel();
        mainSplitPane.setBottomComponent(resultPanel);
        mainSplitPane.setDividerLocation(630); // 初始位置可以调整

        splitPane.setRightComponent(mainSplitPane);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        queryManager = new QueryManager(resultPanel);
        tableManager = new TableManager(rightPanel);

        List<String> databases = IbdFileHandler.listAllIbdFiles();
        for(String database : databases) {
            leftPanel.addDatabase(database);
        }

        // TODO
    }

    public void showNewQueryInput(String dbName) {
        rightPanel.showNewQueryInput(
                e -> {
                    // 运行多个查询
                    List<String> queryStatements = rightPanel.getQueryText();
                    if (queryStatements.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "查询内容为空，请输入查询内容后再运行！", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    StringBuilder allResults = new StringBuilder(); // 用于汇总所有查询结果
                    for (String query : queryStatements) {
                        String result = null;  // 假设 runQuery 返回查询结果

                        try {
                            result = queryManager.runQuery(query);
                        } catch (SQLSyntaxErrorException | IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        allResults.append(result).append("; "); // 汇总结果并加上分隔符
                    }

                    // 显示所有结果
                    try {
                        resultPanel.showSelectedResult(allResults.toString());
                    } catch (SQLSyntaxErrorException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                },
                e -> {
                    // 获取查询内容
                    List<String> queryStatements = rightPanel.getQueryText();
                    if (queryStatements.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "查询内容为空，请输入查询内容后再保存！", "错误", JOptionPane.ERROR_MESSAGE);
                        return false; // 返回 false，表示未成功保存
                    }

                    // 保存每个查询
                    for (String queryText : queryStatements) {
                        String queryName = JOptionPane.showInputDialog(this, "请输入查询名：", "保存查询", JOptionPane.PLAIN_MESSAGE);
                        if (queryName == null) {
                            return false; // 用户点击了取消，直接返回
                        }
                        if (!queryName.trim().isEmpty()) {
                            if (queryManager.saveQuery(dbName, queryName, queryText, false)) {
                                leftPanel.addQueryToDatabase(dbName, queryName);  // 将查询名添加到左侧面板
                            } else {
                                JOptionPane.showMessageDialog(this, "查询名已存在或保存失败！", "错误", JOptionPane.ERROR_MESSAGE);
                                return false;  // 保存失败，返回 false
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "查询名不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
                            return false;  // 保存失败，返回 false
                        }
                    }
                    return true; // 返回 true，表示所有查询成功保存
                },
                dbName
        );
    }

    public void executeSelectedQuery(String query) throws SQLSyntaxErrorException, IOException {
        if (query != null && !query.trim().isEmpty()) {
            queryManager.runQuery(query);
        } else {
            JOptionPane.showMessageDialog(this, "查询为空！", "运行失败", JOptionPane.ERROR_MESSAGE);
        }
    }


    public String getSelectedDatabaseName() {
        return leftPanel.getSelectedDatabaseName();
    }
    public List<String> getDatabaseNames1() {
        return leftPanel.getDatabaseNames1(); // 假设 LeftPanel 有这个方法
    }


    public void openQuery(String dbName, String queryName) {
        String queryText = queryManager.getQuery(dbName, queryName);
        if (queryText != null) {
            rightPanel.openQuery(queryName, queryText, dbName);
        } else {
            JOptionPane.showMessageDialog(this, "查询不存在！", "错误", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("打开查询: " + dbName + " - " + queryName);
    }

    public void openTable(String dbName, String tableName) {
        System.out.println("打开表 " + dbName + " - " + tableName);
        try {
            // 调用 QueryManager 的 getTableContent 方法
            tableManager.getTableContent(dbName, tableName); // 此方法现在直接在 ResultPanel 中显示表内容
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "无法打开表 " + tableName + ": " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }


    public TableManager getTableManager(){
        return this.tableManager;
    }

    public QueryManager getQueryManager() {
        return this.queryManager;
    }

    public RightPanel getRightPanel() {
        return this.rightPanel;
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(Exception e) {
            System.out.println(e);
        }
        SwingUtilities.invokeLater(() -> {
            try {
                new Window().setVisible(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}