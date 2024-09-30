package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

// 管理学生信息的页面类
public class StudentManagementView extends JFrame {
    private JTextField classNameField;    // 班级名的文本框
    private JTextField nameField;         // 学生名的文本框
    private JTextField groupField;        // 小组名的文本框
    private JTextField studentIdField;    // 学生id的文本框
    private JButton addButton;            // 添加按钮
    private JButton removeButton;         // 移除按钮
    private JButton BackToMainButton;     // 返回主菜单按钮
    private JTextArea displayArea;        // 文本区域

    // 构造方法
    public StudentManagementView() {
        setTitle("Student Management");        // 窗口标题
        setSize(400, 300);        // 窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   // 只关闭本页面退出，被空置
        setLocationRelativeTo(null);            // 居中

        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();

        // 文件管理的菜单选项
        JMenu fileMenu = new JMenu("文件管理");
        JMenuItem readItem = new JMenuItem("读取");
        JMenuItem exportItem = new JMenuItem("导出");
        fileMenu.add(readItem);
        fileMenu.add(exportItem);

        // 创建设置菜单
        JMenu settingsMenu = new JMenu("设置");

        // 创建帮助菜单
        JMenu helpMenu = new JMenu("帮助");
        JMenuItem helpItem = new JMenuItem("帮助文档");
        helpMenu.add(helpItem);

        // 将选项添加到菜单栏
        menuBar.add(fileMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);

        // 读取和导出的事件监听器
        readItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "你先别急，我还没写，我不会");
                // 创建IO文件选择器
                JFileChooser fileChooser = new JFileChooser();
                // 显示打开文件的对话框，并阻塞
                int returnValue = fileChooser.showOpenDialog(null);
                // 如果用户点击了打开按钮，APPROVE_OPTION监听
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                        // 逻辑待实现
                    } catch (IOException ep) {
                        ep.printStackTrace();
                    }
                }
            }
        });
        exportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "你先别急，我还没写，我不会");
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                }
            }
        });
        // 帮助菜单的事件监听器
        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 我觉得你需要百度
                int response = JOptionPane.showConfirmDialog(null, "这还要写Readme？","有点抽象了哥们", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                // 检查用户点击的按钮
                if (response == JOptionPane.OK_OPTION || response == JOptionPane.QUESTION_MESSAGE) {
                    try {
                        Desktop.getDesktop().browse(new java.net.URI("https://www.baidu.com"));
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

        // 添加学生按钮
        addButton = new JButton("Add Student");
        panel.add(addButton);

        // 移除学生按钮
        removeButton = new JButton("Remove Student");
        panel.add(removeButton);

        // 回到主菜单按钮
        BackToMainButton = new JButton("Back to Main Menu");
        panel.add(BackToMainButton);

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
        return BackToMainButton;
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
}

