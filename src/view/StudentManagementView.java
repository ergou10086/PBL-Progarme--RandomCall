package view;

import model.Student;
import model.StudentManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class StudentManagementView extends JFrame {
    private JTextField classNameField;    // 班级名的文本框
    private JTextField nameField;         // 学生名的文本框
    private JTextField groupField;        // 小组名的文本框
    private JTextField studentIdField;    // 学生id的文本框
    //private JTextField studentScoreFiled;  // 学生成绩的文本框
    private JButton addButton;            // 添加按钮
    private JButton removeButton;         // 移除按钮
    private JButton editButton;          // 修改学生信息按钮
    private JButton backMainButton;     // 返回主菜单按钮
    private JTextArea displayArea;        // 文本区域

    private StudentManager studentManager;

    private JMenuItem readItem;
    private JMenuItem exportItem;

    // 构造方法
    public StudentManagementView() {
        setTitle("学生信息管理");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 创建菜单栏
        setupMenuBar();

        // 创建输入面板
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            "学生信息输入"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 添加输入字段
        addInputField(inputPanel, "班级名称:", classNameField = new JTextField(), gbc, 0);
        addInputField(inputPanel, "学生姓名:", nameField = new JTextField(), gbc, 1);
        addInputField(inputPanel, "所在小组:", groupField = new JTextField(), gbc, 2);
        addInputField(inputPanel, "学生学号:", studentIdField = new JTextField(), gbc, 3);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("操作区"));

        // 创建按钮，不设置颜色
        addButton = new JButton("添加学生");
        removeButton = new JButton("删除学生");
        editButton = new JButton("编辑信息");
        backMainButton = new JButton("返回主菜单");

        // 统一设置按钮字体和大小
        addButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        removeButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        editButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        backMainButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        // 设置按钮大小
        Dimension buttonSize = new Dimension(120, 35);
        addButton.setPreferredSize(buttonSize);
        removeButton.setPreferredSize(buttonSize);
        editButton.setPreferredSize(buttonSize);
        backMainButton.setPreferredSize(buttonSize);

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);
        buttonPanel.add(backMainButton);

        // 保持原有的返回按钮监听器
        backMainButton.addActionListener(e -> dispose());

        // 创建显示区域
        displayArea = new JTextArea();
        displayArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        displayArea.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("学生信息显示区"));

        // 组装面板
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    // 设置菜单栏
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // 文件菜单
        JMenu fileMenu = new JMenu("文件");
        readItem = new JMenuItem("读取点名册");
        exportItem = new JMenuItem("导出点名册");
        fileMenu.add(readItem);
        fileMenu.add(exportItem);

        // 设置菜单
        JMenu settingsMenu = new JMenu("设置");
        JMenu changeResolutionItem = new JMenu("更改分辨率");
        changeResolutionItem.add(new JMenuItem("原始大小"));
        changeResolutionItem.add(new JMenuItem("600*450"));
        changeResolutionItem.add(new JMenuItem("800*600"));

        JMenuItem changeLanguageItem = new JMenuItem("更改语言");
        changeLanguageItem.setAccelerator(KeyStroke.getKeyStroke('L', ActionEvent.CTRL_MASK));

        JMenuItem changeModeItem = new JMenuItem("更改模式");
        changeModeItem.setAccelerator(KeyStroke.getKeyStroke('M', ActionEvent.CTRL_MASK));

        settingsMenu.add(changeResolutionItem);
        settingsMenu.add(changeLanguageItem);
        settingsMenu.add(changeModeItem);

        // 帮助菜单
        JMenu helpMenu = new JMenu("帮助");
        JMenuItem helpItem = new JMenuItem("介绍文档");
        helpItem.setAccelerator(KeyStroke.getKeyStroke('H', ActionEvent.CTRL_MASK));
        helpMenu.add(helpItem);

        // 添加帮助菜单的事件监听器
        helpItem.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, 
                "这都要Readme?你去玩galgame吧", "那我顺从你", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.OK_OPTION) {
                try {
                    Desktop.getDesktop().browse(new java.net.URI("https://www.touchgal.io/"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        menuBar.add(fileMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    // 辅助方法：添加输入字段
    private void addInputField(JPanel panel, String label, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        field.setPreferredSize(new Dimension(200, 30));
        field.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        panel.add(field, gbc);
    }

    private void loadStudentsFromFile(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            JOptionPane.showMessageDialog(null, "学生信息已成功读取！", "成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "读取学生信息失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveStudentsToFile(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

            JOptionPane.showMessageDialog(null, "学生信息已成功保存！", "成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "保存学生信息失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 返回对应的按钮，以便其他类可以访问这些按钮。
    public JButton getAddButton() {
        return addButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getBackToMainButton() {
        return backMainButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    // 在 JTextArea 中显示传入的学生信息。
    public void displayStudents(String students) {
        displayArea.setText(students);
    }

    // 分别返回各个文本框中的内容
    public String getClassName() {
        return classNameField.getText();
    }

    public String getName() {
        return nameField.getText();
    }

    public String getGroup() {
        return groupField.getText();
    }

    public String getStudentId() {
        return studentIdField.getText();
    }

    // 保存和读取菜单的get
    public JMenuItem getReadItem() {
        return readItem;
    }

    // 获取导出点名册菜单项的方法
    public JMenuItem getExportItem() {
        return exportItem;
    }
}


