package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class StudentManagementView extends JFrame {
    private JTextField classNameField;    // 班级名的文本框
    private JTextField nameField;         // 学生名的文本框
    private JTextField groupField;        // 小组名的文本框
    private JTextField studentIdField;    // 学生id的文本框
    private JTextField scoreField;        // 学生分数的文本框
    private JButton addButton;            // 添加按钮
    private JButton removeButton;         // 移除按钮
    private JButton editButton;           // 修改学生信息按钮
    private JButton backMainButton;       // 返回主菜单按钮
    private JTextArea displayArea;        // 文本区域

    // 构造方法
    public StudentManagementView() {
        setTitle("Student Management");        // 窗口标题
        setSize(400, 300);        // 窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   // 只关闭本页面退出
        setLocationRelativeTo(null);            // 居中

        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();

        // 创建文件菜单
        JMenu fileMenu = new JMenu("文件");
        JMenuItem readItem = new JMenuItem("读取点名册");
        JMenuItem exportItem = new JMenuItem("导出点名册");
        fileMenu.add(readItem);
        fileMenu.add(exportItem);

        // 创建设置菜单
        JMenu settingsMenu = new JMenu("设置");
        JMenu changeResolutionItem = new JMenu("更改分辨率");
        JMenuItem px1 = new JMenuItem("原始大小");
        JMenuItem px2 = new JMenuItem("600*450");
        JMenuItem px3 = new JMenuItem("800*600");
        changeResolutionItem.add(px1);
        changeResolutionItem.add(px2);
        changeResolutionItem.add(px3);

        JMenuItem changeLanguageItem = new JMenuItem("更改语言");
        changeLanguageItem.setAccelerator(KeyStroke.getKeyStroke('L', ActionEvent.CTRL_MASK));

        JMenuItem changeModeItem = new JMenuItem("更改模式");
        changeModeItem.setAccelerator(KeyStroke.getKeyStroke('M', ActionEvent.CTRL_MASK));

        settingsMenu.add(changeResolutionItem);
        settingsMenu.add(changeLanguageItem);
        settingsMenu.add(changeModeItem);

        // 创建帮助菜单
        JMenu helpMenu = new JMenu("帮助");
        JMenuItem helpItem = new JMenuItem("介绍文档");
        helpItem.setAccelerator(KeyStroke.getKeyStroke('H', ActionEvent.CTRL_MASK));
        helpMenu.add(helpItem);

        // 将菜单添加到菜单栏
        menuBar.add(fileMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);


        // 为读取和导出选项添加事件监听
        readItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // TODO: 添加读取文件的逻辑
                }
                System.out.println("读取学生信息的逻辑未实现");
            }
        });

        exportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser saveFileChooser = new JFileChooser();
                int returnValue = saveFileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = saveFileChooser.getSelectedFile();
                    // TODO: 添加保存文件的逻辑
                }
                System.out.println("保存学生信息的逻辑未实现");
            }
        });

        // 帮助菜单的事件监听器
        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "这都要Readme?你去玩galgame吧", "那我顺从你", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.OK_OPTION) {
                    try {
                        Desktop.getDesktop().browse(new java.net.URI("https://www.touchgal.io/"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // 将菜单栏设置为窗口的菜单栏
        setJMenuBar(menuBar);

        // 创建一个6行2列的网格布局面板
        JPanel panel = new JPanel(new GridLayout(6, 2));

        // 班级名 标签
        panel.add(new JLabel("Class Name:"));    // 添加标签
        classNameField = new JTextField();
        panel.add(classNameField);               // 把文本字段添加到 panel 上

        //学生名 标签
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        // 学生组名 标签
        panel.add(new JLabel("Group:"));
        groupField = new JTextField();
        panel.add(groupField);

        // 学生id 标签
        panel.add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        panel.add(studentIdField);

        //学生分数 标签
        /*panel.add(new JLabel("Score:"));
        scoreField = new JTextField();
        panel.add(scoreField);*/

        // 添加学生按钮
        addButton = new JButton("Add Student");
        panel.add(addButton);

        // 移除学生按钮
        removeButton = new JButton("Remove Student");
        panel.add(removeButton);

        //编辑学生信息按钮
        editButton = new JButton("Edit Student");
        panel.add(editButton);

        // 回到主菜单按钮
        backMainButton = new JButton("Back to Main Menu");
        panel.add(backMainButton);
        backMainButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // 显示学生信息
        displayArea = new JTextArea();   // 多行文本区域对象
        JScrollPane scrollPane = new JScrollPane(displayArea);   // 添加到滚动面板，以免溢出

        // 布局管理器，将panel 组件添加到容器的顶部，将滚动面板添加到容器的中心
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
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

    /*public String getScore(){
        return scoreField.getText();
    }*/
}
