package com.window.ui;

import com.window.test.Window;

import javax.swing.*;
import java.io.*;
import java.util.List;

public class MenuBarBuilder {
    public JMenuBar createMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = createFileMenu(frame);
        menuBar.add(fileMenu);
        return menuBar;
    }

    private JMenu createFileMenu(JFrame frame) {
        JMenu fileMenu = new JMenu("文件");
        fileMenu.add(createNewMenuItem(frame));
        fileMenu.add(createOpenMenuItem(frame));
        fileMenu.add(createSaveMenuItem(frame));
        fileMenu.addSeparator();
        fileMenu.add(createExitMenuItem());
        return fileMenu;
    }

    private JMenuItem createNewMenuItem(JFrame frame) {
        JMenuItem newItem = new JMenuItem("新建");
        newItem.addActionListener(e -> {
            if (frame instanceof Window) {
                // 直接使用第一个数据库名，假设至少有一个数据库
                List<String> databaseNames = ((Window) frame).getDatabaseNames1(); // 获取数据库名的列表
                if (!databaseNames.isEmpty()) {
                    String dbName = databaseNames.get(0); // 使用第一个数据库名
                    ((Window) frame).showNewQueryInput(dbName);
                } else {
                    JOptionPane.showMessageDialog(frame, "没有可用的数据库。", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return newItem;
    }


    private JMenuItem createOpenMenuItem(JFrame frame) {
        JMenuItem openItem = new JMenuItem("打开");
        openItem.addActionListener(e -> openFile(frame));
        return openItem;
    }

    private void openFile(JFrame frame) {
        if (frame instanceof Window) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("SQL files", "sql"));
            int returnVal = fileChooser.showOpenDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                readFileContent(frame, file);
            }
        }
    }

    private void readFileContent(JFrame frame, File file) {
        StringBuilder queryContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                queryContent.append(line).append("\n");
            }
            // 使用合并后的查询内容
            ((Window) frame).getRightPanel().openQuery(file.getName(), queryContent.toString(), file.getParent());
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(frame, "读取文件时发生错误。", "打开失败", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JMenuItem createSaveMenuItem(JFrame frame) {
        JMenuItem saveItem = new JMenuItem("保存");
        saveItem.addActionListener(e -> saveQuery(frame));
        return saveItem;
    }

    private void saveQuery(JFrame frame) {
        if (frame instanceof Window) {
            RightPanel rightPanel = ((Window) frame).getRightPanel();
            List<String> queryTextList = rightPanel.getQueryText();  // 获取查询内容，类型为 List<String>

            // 将 List<String> 转换为单一的 String，多个查询之间使用换行符分隔
            String queryText = String.join("\n", queryTextList);

            if (queryText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "当前没有查询内容可保存。", "警告", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showSaveDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".sql")) {
                    file = new File(file.getAbsolutePath() + ".sql");
                }
                file = getUniqueFile(file);
                saveFileContent(queryText, file, frame);  // 保存合并后的查询内容
            }
        }
    }

    private void saveFileContent(String queryText, File file, JFrame frame) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(queryText);
            JOptionPane.showMessageDialog(frame, "查询已成功保存到文件。", "保存成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(frame, "保存文件时发生错误。", "保存失败", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JMenuItem createExitMenuItem() {
        JMenuItem exitItem = new JMenuItem("退出");
        exitItem.addActionListener(e -> System.exit(0));
        return exitItem;
    }


    // 辅助方法：检查并返回唯一的文件名
    private File getUniqueFile(File file) {
        String filePath = file.getAbsolutePath();
        String fileName = file.getName();
        String fileDir = file.getParent();

        int count = 1;
        while (file.exists()) {
            String newFileName;
            // 如果文件名中已经有 "(n)" 的编号，移除它，避免重复编号
            if (fileName.contains("(") && fileName.contains(")")) {
                newFileName = fileName.replaceAll("\\(\\d+\\)", "") + "(" + count + ").sql";
            } else {
                newFileName = fileName.replace(".sql", "") + "(" + count + ").sql";
            }
            file = new File(fileDir, newFileName);
            count++;
        }
        return file;
    }
}
