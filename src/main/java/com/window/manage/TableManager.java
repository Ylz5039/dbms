package com.window.manage;

import com.structuer.Filed;
import com.structuer.Table;
import com.test.ALLDatabase;
import com.window.ui.RightPanel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.ArrayList;
import java.util.List;

class EditableTable extends JTable {
    private boolean modified;

    public EditableTable(String[][] data, String[] columnNames) {
        super(data, columnNames);
        this.modified = false;

        // 监听表格模型的变化，更新修改状态
        getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                modified = true; // 当表格内容变化时设置为已修改
            }
        });
    }

    public boolean isModified() {
        return modified;
    }

    public void resetModifiedFlag() {
        modified = false; // 在保存后重置标志
    }
}

public class TableManager {
    private RightPanel rightPanel;

    public TableManager(RightPanel rightPanel) {
        this.rightPanel = rightPanel;
    }

    public JScrollPane getTableContent(String dbName, String tableName) {
        // 获取表对象
        Table table = ALLDatabase.dataBase.getTable(tableName);
        if (table == null) {
            JOptionPane.showMessageDialog(null, "表 '" + tableName + "' 不存在！", "错误", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // 获取表的字段名
        List<String> fileNames = new ArrayList<>();
        for (Filed f : table.getFiledList()) {
            fileNames.add(f.getFiledName());
        }

        // 获取表中的记录
        List<List<String>> records = table.getRecords();

        // 创建表格数据
        String[][] data = new String[records.size()][fileNames.size()];
        for (int i = 0; i < records.size(); i++) {
            for (int j = 0; j < fileNames.size(); j++) {
                data[i][j] = records.get(i).get(j); // 假设每个记录的每个字段都有值
            }
        }

        // 创建自定义 JTable
        EditableTable tableComponent = new EditableTable(data, fileNames.toArray(new String[0]));
        JScrollPane scrollPane = new JScrollPane(tableComponent);

        // 设置表格的列宽、行高等属性
        tableComponent.setFillsViewportHeight(true);
        tableComponent.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // 在右侧面板中显示表格
        rightPanel.openTable(tableName, scrollPane); // 假设 rightPanel 有 openTable 方法来显示 JScrollPane

        // 关闭操作的示例（假设在 rightPanel 中处理关闭逻辑）
        /*rightPanel.addCloseListener(e -> {
            if (tableComponent.isModified()) {
                int result = JOptionPane.showConfirmDialog(rightPanel, "是否保存更改?", "关闭表格", JOptionPane.YES_NO_CANCEL_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    saveTableData(tableName, data);
                    tableComponent.resetModifiedFlag();
                }
            }
            rightPanel.closeTable(tableName); // 关闭当前表格
        });*/

        return scrollPane;
    }


    private void saveTableData(String tableName, String[][] data) {
        // 实现保存表格数据的逻辑
        // 例如，将数据保存到数据库中
    }
}
