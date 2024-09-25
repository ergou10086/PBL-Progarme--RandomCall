package view;

import javax.swing.*;
import java.awt.*;

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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   // 只关闭本页面退出
        setLocationRelativeTo(null);            // 居中

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
        JScrollPane scrollPane = new JScrollPane(displayArea);   // 滚动条
        // 布局管理器，将panel 组件添加到容器的顶部，将 scrollPane 添加到容器的中心
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

