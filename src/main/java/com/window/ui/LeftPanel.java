package com.window.ui;

import com.structuer.Table;
import com.structuer.func.IbdFileHandler;
import com.test.ALLDatabase;
import com.window.test.Window;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeftPanel extends JPanel {
    private JTree dbTree; // 用于展示数据库和表的树
    private JPopupMenu popupMenu; // 右键菜单
    private Window window;
    // 存储数据库名的列表
    private List<String> databaseNames;

    public LeftPanel(Window window) throws IOException{
        this.window = window;
        setLayout(new BorderLayout());
        databaseNames = new ArrayList<>();

        // 创建数据库和表的根节点
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("数据库");
        dbTree = new JTree(new DefaultTreeModel(root));
        dbTree.setRootVisible(false); // 隐藏根节点
        JScrollPane scrollPane = new JScrollPane(dbTree);
        add(scrollPane, BorderLayout.CENTER);

        // 添加鼠标事件监听器（包括双击和右键菜单）
        dbTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TreePath selectedPath = dbTree.getSelectionPath();
                List<String> tableNames = new ArrayList<>();
                // 双击查询名或表名时，显示内容
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    if (selectedPath != null && selectedPath.getPathCount() == 2) { // 确保路径深度为2，表示点击的是数据库名
                        String dbName = selectedPath.getLastPathComponent().toString();
                        System.out.println(dbName);
                        try {
                            ALLDatabase.setDataBase(IbdFileHandler.loadFromIbdFile(dbName));
                            System.out.println(ALLDatabase.dataBase);
                            System.out.println(ALLDatabase.getTable());
                            tableNames= ALLDatabase.getTablesName();
                            System.out.println(tableNames);

                            System.out.println(ALLDatabase.dataBase);
                            System.out.println(ALLDatabase.getTablesName());
                            for (int i = 0; i < tableNames.size(); i++) {
                                System.out.println(i+1);
                                addTableToDatabase(dbName, tableNames.get(i));
                            }
                        } catch (IOException ex) {
                            System.out.println("NO");
                            throw new RuntimeException(ex);
                        }
                    }
                    if (selectedPath != null && selectedPath.getPathCount() == 3){
                        String dbName = selectedPath.getPathComponent(1).toString();
                    }
                    if (selectedPath != null && selectedPath.getPathCount() == 4) { // 确保路径深度为4
                        String dbName = selectedPath.getPathComponent(1).toString();
                        String nodeName = selectedPath.getLastPathComponent().toString();

                        // 检查是否为表名或查询名
                        if (isTableNode(selectedPath)) {
                            window.openTable(dbName, nodeName); // 处理表名
                        } else if (isQueryNode(selectedPath)) {
                            window.openQuery(dbName, nodeName); // 处理查询名
                        }
                    }
                }

                // 右键单击时，显示右键菜单
                if (SwingUtilities.isRightMouseButton(e)) {
                    if (selectedPath != null) {
                        dbTree.setSelectionPath(selectedPath);
                        String nodeName = selectedPath.getLastPathComponent().toString();

                        // 判断右键点击的是数据库、表名还是查询名
                        if (selectedPath.getPathCount() == 2) { // 点击数据库名
                            String dbName = selectedPath.getPathComponent(1).toString();
                            showDatabasePopupMenu(e, dbName);
                        } else if (selectedPath.getPathCount() == 4) { // 点击表名或查询名
                            String dbName = selectedPath.getPathComponent(1).toString();
                            if (isQueryNode(selectedPath)) { // 判断是否为查询名
                                showQueryPopupMenu(e, dbName, nodeName);
                            } else {
                                showTablePopupMenu(e, dbName, nodeName);
                            }
                        }
                    }
                }
            }
        });
        add(new JScrollPane(dbTree), BorderLayout.CENTER);
    }

    // 判断选中的节点是否为表名
    private boolean isTableNode(TreePath path) {
        return path.getPathCount() == 4 && path.getParentPath().getLastPathComponent().toString().equals("表");
    }
    // 判断选中的节点是否为查询名
    private boolean isQueryNode(TreePath path) {
        return path.getPathCount() == 4 && path.getParentPath().getLastPathComponent().toString().equals("查询");
    }


    // 显示数据库右键菜单
    private void showDatabasePopupMenu(MouseEvent e, String dbName) {
        popupMenu = new JPopupMenu(); // 每次右键点击时重新创建菜单
        JMenuItem openMenuItem = new JMenuItem("打开");
        openMenuItem.addActionListener(event -> expandDatabase(dbName));
        JMenuItem closeMenuItem = new JMenuItem("关闭");
        closeMenuItem.addActionListener(event -> collapseDatabase(dbName));
        JMenuItem newMenuItem = new JMenuItem("新建");
        newMenuItem.addActionListener(event -> window.showNewQueryInput(dbName));
        JMenuItem refreshMenuItem = new JMenuItem("刷新");
        refreshMenuItem.addActionListener(event -> refreshDatabase(dbName));
        popupMenu.add(openMenuItem);
        popupMenu.add(closeMenuItem);
        popupMenu.add(newMenuItem);
        popupMenu.add(refreshMenuItem);
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    // 显示查询名右键菜单
    private void showQueryPopupMenu(MouseEvent e, String dbName, String queryName) {
        popupMenu = new JPopupMenu();
        JMenuItem openMenuItem = new JMenuItem("打开");
        openMenuItem.addActionListener(event -> window.openQuery(dbName, queryName));
        JMenuItem deleteMenuItem = new JMenuItem("删除");
        deleteMenuItem.addActionListener(event -> deleteQuery(dbName, queryName));
        JMenuItem renameMenuItem = new JMenuItem("重命名");
        renameMenuItem.addActionListener(event -> renameQuery(dbName, queryName));
        popupMenu.add(openMenuItem);
        popupMenu.add(deleteMenuItem);
        popupMenu.add(renameMenuItem);
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    // 显示表名右键菜单
    private void showTablePopupMenu(MouseEvent e, String dbName, String tableName) {
        popupMenu = new JPopupMenu();
        JMenuItem openMenuItem = new JMenuItem("打开");
        openMenuItem.addActionListener(event -> window.openTable(dbName, tableName));
        JMenuItem deleteMenuItem = new JMenuItem("删除");
        deleteMenuItem.addActionListener(event -> deleteTable(dbName, tableName));
        JMenuItem renameMenuItem = new JMenuItem("重命名");
        renameMenuItem.addActionListener(event -> renameTable(dbName, tableName));
        popupMenu.add(openMenuItem);
        popupMenu.add(deleteMenuItem);
        popupMenu.add(renameMenuItem);
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    // 刷新数据库的方法
    private void refreshDatabase(String dbName) {
        try {
            // 清空原有的表和查询数据
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) dbTree.getModel().getRoot();
            DefaultMutableTreeNode dbNode = findDatabaseNode(dbName, root);
            if (dbNode != null) {
                // 清除现有节点
                //dbNode.removeAllChildren();
                // 重新加载数据库信息
                ALLDatabase.setDataBase(IbdFileHandler.loadFromIbdFile(dbName));
                List<String> tableNames = ALLDatabase.getTablesName();

                // 重新添加表节点
                for (String tableName : tableNames) {
                    addTableToDatabase(dbName, tableName);
                }
                ((DefaultTreeModel) dbTree.getModel()).reload(dbNode); // 重新加载数据库节点
                //JOptionPane.showMessageDialog(this, "数据库 '" + dbName + "' 已刷新。");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "刷新失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 删除查询
    private void deleteQuery(String dbName, String queryName) {
        int confirm = JOptionPane.showConfirmDialog(this, "确认删除查询 '" + queryName + "' 吗？", "确认", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean isDeleted = window.getQueryManager().deleteQuery1(dbName, queryName);

            if (isDeleted) {
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) dbTree.getModel().getRoot();
                DefaultMutableTreeNode dbNode = null;

                // 找到对应的数据库节点
                for (int i = 0; i < root.getChildCount(); i++) {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
                    if (child.toString().equals(dbName)) {
                        dbNode = child;
                        break;
                    }
                }

                // 找到并删除查询节点
                if (dbNode != null) {
                    DefaultMutableTreeNode queriesNode = null;

                    // 找到"查询"子节点
                    for (int i = 0; i < dbNode.getChildCount(); i++) {
                        DefaultMutableTreeNode child = (DefaultMutableTreeNode) dbNode.getChildAt(i);
                        if (child.toString().equals("查询")) {
                            queriesNode = child;
                            break;
                        }
                    }

                    // 在"查询"子节点下查找并删除查询节点
                    if (queriesNode != null) {
                        DefaultMutableTreeNode queryNode = findChildNode(queriesNode, queryName);
                        if (queryNode != null) {
                            queriesNode.remove(queryNode);
                            ((DefaultTreeModel) dbTree.getModel()).reload(queriesNode); // 只重载查询节点
                            JOptionPane.showMessageDialog(this, "查询 '" + queryName + "' 已删除。");
                        }
                    }
                }
            }
        }
    }






    // 重命名查询
    private void renameQuery(String dbName, String queryName) {
        String newName = JOptionPane.showInputDialog(this, "请输入新的查询名：", queryName);

        if (newName != null && !newName.trim().isEmpty()) {
            boolean success = window.getQueryManager().renameQuery(dbName, queryName, newName);

            if (success) {
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) dbTree.getModel().getRoot();
                DefaultMutableTreeNode dbNode = findDatabaseNode(dbName, root);

                if (dbNode != null) {
                    DefaultMutableTreeNode queriesNode = findChildNode(dbNode, "查询");
                    if (queriesNode != null) {
                        DefaultMutableTreeNode queryNode = findChildNode(queriesNode, queryName);
                        if (queryNode != null) {
                            queryNode.setUserObject(newName);
                            ((DefaultTreeModel) dbTree.getModel()).nodeChanged(queryNode);
                            ((DefaultTreeModel) dbTree.getModel()).reload();

                            // 重新展开数据库和查询节点
                            dbTree.expandPath(new TreePath(dbNode.getPath()));
                            dbTree.expandPath(new TreePath(new Object[]{dbNode, queriesNode}));

                            JOptionPane.showMessageDialog(this, "查询名已更改为 '" + newName + "'。");
                        }
                    }
                }
            } else {
                //JOptionPane.showMessageDialog(this, "重命名失败，请确保查询名唯一。");
            }
        }
    }




    // 删除表
    private void deleteTable(String dbName, String tableName) {
        int confirm = JOptionPane.showConfirmDialog(this, "确认删除表 '" + tableName + "' 吗？", "确认", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // TODO: 从数据管理层删除表逻辑

            DefaultMutableTreeNode root = (DefaultMutableTreeNode) dbTree.getModel().getRoot();
            DefaultMutableTreeNode dbNode = findDatabaseNode(dbName, root);
            DefaultMutableTreeNode tablesNode = findChildNode(dbNode, "表");

            if (tablesNode != null) {
                DefaultMutableTreeNode tableNode = findChildNode(tablesNode, tableName);
                if (tableNode != null) {
                    tablesNode.remove(tableNode);
                    ((DefaultTreeModel) dbTree.getModel()).reload(tablesNode);
                    JOptionPane.showMessageDialog(this, "表 '" + tableName + "' 已删除。");
                }
            }
        }
    }

    // 重命名表
    private void renameTable(String dbName, String tableName) {
        String newName = JOptionPane.showInputDialog(this, "请输入新的表名：", tableName);
        if (newName != null && !newName.trim().isEmpty()) {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) dbTree.getModel().getRoot();
            DefaultMutableTreeNode dbNode = findDatabaseNode(dbName, root);
            DefaultMutableTreeNode tableNode = findChildNode(dbNode, tableName);

            if (tableNode != null) {
                tableNode.setUserObject(newName);
                ((DefaultTreeModel) dbTree.getModel()).nodeChanged(tableNode);
                ((DefaultTreeModel) dbTree.getModel()).reload();

                // 重新展开数据库和表节点
                dbTree.expandPath(new TreePath(dbNode.getPath()));
                dbTree.expandPath(new TreePath(new Object[]{dbNode, tableNode}));

                JOptionPane.showMessageDialog(this, "表名已更改为 '" + newName + "'。");
            }
        }
    }
    // 辅助方法：查找数据库节点
    private DefaultMutableTreeNode findDatabaseNode(String dbName, DefaultMutableTreeNode root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
            System.out.println("查找子节点: " + child.getUserObject()); // 添加输出
            if (child.getUserObject().equals(dbName)) {
                return child;
            }
        }
        return null;
    }


    // 辅助方法：检查名称是否存在
    private boolean isNameExists(String name, DefaultMutableTreeNode queriesNode) {
        for (int i = 0; i < queriesNode.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) queriesNode.getChildAt(i);
            if (child.getUserObject().equals(name)) {
                return true;
            }
        }
        return false;
    }


    // 辅助方法：查找子节点
    private DefaultMutableTreeNode findChildNode(DefaultMutableTreeNode parent, String childName) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);
            if (child.getUserObject().equals(childName)) {
                return child;
            }
        }
        return null;
    }
    // 展开数据库名
    private void expandDatabase(String dbName) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) dbTree.getModel().getRoot();
        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode dbNode = (DefaultMutableTreeNode) root.getChildAt(i);
            if (dbNode.toString().equals(dbName)) {
                dbTree.expandPath(new TreePath(dbNode.getPath()));
                break;
            }
        }
    }
    // 收起数据库名
    private void collapseDatabase(String dbName) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) dbTree.getModel().getRoot();
        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode dbNode = (DefaultMutableTreeNode) root.getChildAt(i);
            if (dbNode.toString().equals(dbName)) {
                dbTree.collapsePath(new TreePath(dbNode.getPath()));
                break;
            }
        }
    }
    // 向数据库添加表和查询
    public void addDatabase(String dbName) {
        databaseNames.add(dbName);
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) dbTree.getModel().getRoot();
        DefaultMutableTreeNode dbNode = new DefaultMutableTreeNode(dbName);

        // 创建“表”和“查询”节点
        DefaultMutableTreeNode tablesNode = new DefaultMutableTreeNode("表");
        DefaultMutableTreeNode queriesNode = new DefaultMutableTreeNode("查询");

        // 将“表”和“查询”节点添加到数据库节点
        dbNode.add(tablesNode);
        dbNode.add(queriesNode);

        // 将数据库节点添加到根节点
        root.add(dbNode);

        // 更新树模型以反映更改
        ((DefaultTreeModel) dbTree.getModel()).reload();
    }

    // 返回当前所有数据库名称
    public List<String> getDatabaseNames() {
        return IbdFileHandler.listAllIbdFiles();
    }

    public List<String> getDatabaseNames1() {
        return databaseNames;
    }
    //TODO
    //TODO
    private void addTableToDatabase(String dbName, String tableName) {
        // 获取根节点
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) dbTree.getModel().getRoot();
        DefaultMutableTreeNode dbNode = findDatabaseNode(dbName, root);

        if (dbNode != null) {
            // 查找"表"子节点
            DefaultMutableTreeNode tablesNode = findChildNode(dbNode, "表");

            // 如果"表"节点不存在，则创建它
            if (tablesNode == null) {
                tablesNode = new DefaultMutableTreeNode("表");
                dbNode.add(tablesNode);
            }

            // 检查表名是否已存在
            if (!isNameExists(tableName, tablesNode)) {
                // 添加新表节点
                DefaultMutableTreeNode newTableNode = new DefaultMutableTreeNode(tableName);
                tablesNode.add(newTableNode);

                // 更新树模型
                ((DefaultTreeModel) dbTree.getModel()).reload(tablesNode);
                //JOptionPane.showMessageDialog(this, "表 '" + tableName + "' 已添加到数据库 '" + dbName + "'。");
            } else {
                //JOptionPane.showMessageDialog(this, "表名 '" + tableName + "' 已存在，请使用其他名称。");
            }
        } else {
            JOptionPane.showMessageDialog(this, "数据库 '" + dbName + "' 不存在。");
        }
    }


    // 向数据库添加查询
    public void addQueryToDatabase(String dbName, String queryName) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) dbTree.getModel().getRoot();
        DefaultMutableTreeNode dbNode = null;

        // 找到对应的数据库节点
        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
            if (child.toString().equals(dbName)) {
                dbNode = child;
                break;
            }
        }

        // 如果数据库节点存在，添加查询节点到查询子节点
        if (dbNode != null) {
            DefaultMutableTreeNode queriesNode = null;
            for (int i = 0; i < dbNode.getChildCount(); i++) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) dbNode.getChildAt(i);
                if (child.toString().equals("查询")) {
                    queriesNode = child;
                    break;
                }
            }

            if (queriesNode != null) {
                queriesNode.add(new DefaultMutableTreeNode(queryName));
                ((DefaultTreeModel) dbTree.getModel()).reload();

                // 确保数据库节点仍然保持展开状态
                dbTree.expandPath(new TreePath(dbNode.getPath()));
            }
        }
    }

    // 获取选中的数据库名称
    public String getSelectedDatabaseName() {
        TreePath selectedPath = dbTree.getSelectionPath();
        if (selectedPath != null && selectedPath.getPathCount() >= 2) {
            return selectedPath.getPathComponent(1).toString();
        }
        return null;
    }
}