package com.window.ui;

import com.window.test.Window;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLSyntaxErrorException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class RightPanel extends JPanel {
    private JTextArea queryInput;
    private JTabbedPane tabbedPane; // 用于显示多个查询的选项卡面板
    private JPanel tasksPanel; // 显示打开的查询名称的面板
    private Window window;
    private LeftPanel leftPanel;
    private HashMap<String, String> openedQueries;
    private boolean isQueryModified; // 标识查询内容是否被修改
    private Map<String, JScrollPane> openTables = new HashMap<>();


    public RightPanel(Window window, LeftPanel leftPanel) {
        this.window = window;
        this.leftPanel = leftPanel;
        setLayout(new BorderLayout());
        openedQueries = new HashMap<>();

        // 初始化选项卡面板
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        tasksPanel = new JPanel(); // 任务卡面板
        tasksPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(tasksPanel, BorderLayout.NORTH);

        // 添加选项卡切换监听器
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                refreshTabContent(); // 在切换选项卡时刷新内容
            }
        });
    }

    // 刷新当前选项卡内容的方法
    private void refreshTabContent() {
        // 获取当前选项卡面板
        Component selectedComponent = tabbedPane.getSelectedComponent();
        if (selectedComponent instanceof JPanel) {
            JPanel panel = (JPanel) selectedComponent;
            String tabTitle = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());

            // 提取 dbName，如果选项卡标题中包含数据库信息
            String dbName = null;
            if (tabTitle.contains(" - ")) {
                dbName = tabTitle.split(" - ")[0];
            }

            // 检查是否是表内容或查询内容
            if (tabTitle.startsWith("表 - ")) {
                // 表内容刷新逻辑
                String tableName = tabTitle.substring(4); // 获取表名
                JScrollPane tableContent = window.getTableManager().getTableContent(dbName, tableName); // 获取表内容
                panel.removeAll(); // 清空面板
                panel.add(tableContent, BorderLayout.CENTER); // 添加表内容
            } else if (dbName != null) {
                // 查询内容刷新逻辑
                String queryName = tabTitle.split(" - ")[1];
                String queryText = window.getQueryManager().getQueryText(dbName, queryName); // 获取查询内容

                // 检查查询内容并刷新显示
                JTextArea queryArea = (JTextArea) ((JScrollPane) panel.getComponent(0)).getViewport().getView();
                queryArea.setText(queryText); // 更新查询内容
            }

            panel.revalidate(); // 更新面板
            panel.repaint(); // 重绘面板
        }
    }


    public void showNewQueryInput(ActionListener runAction, SaveAction saveAction, String dbName) {
        JPanel panel = createQueryPanel(runAction, e -> {
            if (saveAction.execute(e)) {
                isQueryModified = false; // 保存后重置修改标志
            }
        }, dbName, null, true); // 这里传递 isNewQuery = true

        String tabName = getNewQueryTabName();
        tabbedPane.addTab(tabName, panel); // 添加新建查询选项卡
        tabbedPane.setSelectedComponent(panel); // 设置新建的查询为当前显示的选项卡

        // 设置带有关闭按钮的自定义标题
        setTabComponentWithCloseButton(tabName, panel);

        updateTasksPanel(""); // 更新任务栏，保持空白
    }

    // 打开现有查询
    public void openQuery(String queryName, String queryText, String dbName) {
        String tabTitle = dbName + " - " + queryName;

        // 检查是否已经存在相同名称的选项卡
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getTitleAt(i).equals(tabTitle)) {
                tabbedPane.setSelectedIndex(i); // 如果已存在，直接选中该选项卡
                return; // 结束方法
            }
        }

        // 如果不存在，创建新选项卡
        JPanel panel = createQueryPanel(
                e -> {
                    try {
                        window.getQueryManager().runQuery(queryInput.getText());
                    } catch (SQLSyntaxErrorException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                },
                e -> window.getQueryManager().saveQuery(dbName, queryName, queryInput.getText(), true),
                dbName,
                queryText,
                false); // 这里传递 isNewQuery = false

        tabbedPane.addTab(tabTitle, panel); // 添加打开的查询选项卡
        tabbedPane.setSelectedComponent(panel); // 设置为当前显示的选项卡

        // 设置带有关闭按钮的自定义标题
        setTabComponentWithCloseButton(tabTitle, panel);

        updateTasksPanel(""); // 更新任务栏，保持空白
    }

    // 打开表的方法
    public void openTable(String tableName, JScrollPane tableContent) {
        // 检查是否已经存在同名的表任务卡
        String tabTitle = "表 - " + tableName;
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getTitleAt(i).equals(tabTitle)) {
                tabbedPane.setSelectedIndex(i); // 如果已存在，直接选中该选项卡
                return;
            }
        }

        // 如果不存在，创建新的表内容面板
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(tableContent, BorderLayout.CENTER);

        // 在选项卡面板中添加表的任务卡
        tabbedPane.addTab(tabTitle, panel);
        tabbedPane.setSelectedComponent(panel); // 设置为当前显示的选项卡

        // 设置带有关闭按钮的自定义标题
        setTabComponentWithCloseButton(tabTitle, panel);

        updateTasksPanel(""); // 更新任务栏，保持空白
    }

    // 创建查询面板
    private JPanel createQueryPanel(ActionListener runAction, ActionListener saveAction, String dbName, String queryText, boolean isNewQuery) {
        JPanel panel = new JPanel(new BorderLayout());
        queryInput = new JTextArea(queryText != null ? queryText : "");
        isQueryModified = false; // 初始时未被修改

        // 监听查询内容的变化，更新 isQueryModified
        queryInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isQueryModified = true;
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isQueryModified = true;
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isQueryModified = true;
            }
        });

        panel.add(new JScrollPane(queryInput), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton runButton = new JButton("运行");
        runButton.addActionListener(e -> {
            String selectedText = queryInput.getSelectedText(); // 获取选中的文本
            if (selectedText != null && !selectedText.trim().isEmpty()) {
                // 如果有选中内容，运行选中的文本
                try {
                    window.getQueryManager().runQuery(selectedText);
                } catch (SQLSyntaxErrorException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                // 如果没有选中内容，运行全部内容
                try {
                    window.getQueryManager().runQuery(queryInput.getText());
                } catch (SQLSyntaxErrorException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(e -> {
            String updatedQueryText = queryInput.getText();
            if (isNewQuery) { // 如果是新建查询，进行唯一性检查
                saveAction.actionPerformed(e);
            } else { // 如果是已存在查询，直接保存更改
                String queryName = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).split(" - ")[1]; // 获取当前查询名
                window.getQueryManager().saveQuery(dbName, queryName, updatedQueryText, true); // 保存修改
                isQueryModified = false; // 保存后重置修改标志
            }
        });

        buttonPanel.add(runButton);
        buttonPanel.add(saveButton);
        panel.add(buttonPanel, BorderLayout.NORTH);

        return panel;
    }



    // 获取新建查询的默认名称，避免重复
    private String getNewQueryTabName() {
        int count = 2;
        String tabName = "新建查询01";
        while (isTabNameExists(tabName)) {
            tabName = "新建查询" + String.format("%02d", count++);
        }
        return tabName;
    }

    // 检查是否已经有相同名称的选项卡
    private boolean isTabNameExists(String tabName) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getTitleAt(i).equals(tabName)) {
                return true;
            }
        }
        return false;
    }

    // 设置带有关闭按钮的选项卡标题
    private void setTabComponentWithCloseButton(String tabTitle, JPanel panel) {
        JPanel tabComponent = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tabComponent.setOpaque(false);

        JLabel titleLabel = new JLabel(tabTitle);
        tabComponent.add(titleLabel);

        JButton closeButton = new JButton("x");
        closeButton.setMargin(new Insets(0, 5, 0, 5));
        closeButton.addActionListener(e -> {
            if (isQueryModified) { // 仅在查询被修改时弹出提示
                int result = JOptionPane.showConfirmDialog(this, "是否保存更改?", "关闭查询", JOptionPane.YES_NO_CANCEL_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    String dbName = tabTitle.contains(" - ") ? tabTitle.split(" - ")[0] : "默认数据库";  // 假设数据库名在 tabTitle 的前半部分
                    String queryName = tabTitle.contains(" - ") ? tabTitle.split(" - ")[1] : tabTitle;  // 查询名是 tabTitle 的后半部分

                    // 从当前选项卡获取查询内容并保存
                    JTextArea currentQueryInput = (JTextArea) ((JScrollPane) panel.getComponent(0)).getViewport().getView();
                    window.getQueryManager().saveQuery(dbName, queryName, currentQueryInput.getText(), true); // 保存更改

                    tabbedPane.remove(panel); // 关闭查询选项卡
                } else if (result == JOptionPane.NO_OPTION) {
                    tabbedPane.remove(panel); // 直接关闭选项卡
                }
            } else {
                tabbedPane.remove(panel); // 如果没有修改，直接关闭
            }
        });

        tabComponent.add(closeButton);

        tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(panel), tabComponent);
    }

    public void setQueryText(String text) {
        if (queryInput != null) {
            queryInput.setText(text);
        }
    }

    // 更新任务卡面板
    private void updateTasksPanel(String tabName) {
        tasksPanel.removeAll();

        JLabel taskLabel = new JLabel(tabName); // 任务卡显示表名或查询名
        tasksPanel.add(taskLabel);

        tasksPanel.revalidate();
        tasksPanel.repaint();
    }

    // 获取查询输入的内容，并按分号将其分割为多个语句
    public List<String> getQueryText() {
        if (queryInput != null) {
            String inputText = queryInput.getText();
            return Arrays.stream(inputText.split(";"))  // 按分号分割
                    .map(String::trim)                  // 去掉每个语句前后的空格
                    .filter(s -> !s.isEmpty())         // 过滤掉空字符串
                    .collect(Collectors.toList());     // 收集成列表返回
        }
        return new ArrayList<>();
    }
    public void displayTable(JScrollPane scrollPane) {
        this.removeAll(); // 清空面板
        this.add(scrollPane, BorderLayout.CENTER); // 使用适当的布局管理器
        this.revalidate(); // 更新面板
        this.repaint(); // 重绘面板
    }
    // 关闭指定的表格
    public void closeTable(String tableName) {
        JScrollPane scrollPane = openTables.remove(tableName);
        if (scrollPane != null) {
            remove(scrollPane);
            revalidate();
            repaint();
        }
    }

    // 添加关闭监听器
    /*public void addCloseListener(ActionListener listener) {
        // 假设有个关闭按钮
        JButton closeButton = new JButton("关闭");
        closeButton.addActionListener(e -> {
            // 在这里可以进行相应的逻辑，比如获取当前打开的表格
            String currentTableName = ""; // 获取当前表格名（根据您的逻辑）
            listener.actionPerformed(e); // 调用监听器
        });
        add(closeButton, BorderLayout.SOUTH); // 将关闭按钮添加到面板
    }*/

    // 获取当前打开的表格名（假设实现）
    public String getCurrentTableName() {
        // 逻辑来获取当前打开的表格名
        // 示例返回第一个打开的表格名
        return openTables.keySet().stream().findFirst().orElse(null);
    }

}
