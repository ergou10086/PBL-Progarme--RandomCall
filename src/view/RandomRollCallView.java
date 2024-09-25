package view;

import javax.swing.*;
import java.awt.*;

public class RandomRollCallView extends JFrame {
    // 页面组件
    private JComboBox<String> classComboBox;      // 用于选择班级的下拉列表。
    private JRadioButton groupRadioButton;        // 选择“随机小组”的单选标签
    private JRadioButton studentRadioButton;      // 选择“随机学生"的单选标签
    private JButton startButton;                  // 开始点名操作按钮
    private JButton BackMainButton;               // 回到主页面按钮

    public RandomRollCallView() {
        setTitle("Random Roll Call");       // 标题
        setSize(300, 200);     // 大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   // 只关闭本页面退出
        setLocationRelativeTo(null);       // 居中

        // 创建一个网格布局面板，该管理器将面板分为 4 行 1 列的布局。
        JPanel panel = new JPanel(new GridLayout(4, 1));

        // 添加标签和 JComboBox 到面板
        classComboBox = new JComboBox<>();     // 下拉列表组件
        panel.add(new JLabel("Select Class:"));    // 添加标签
        panel.add(classComboBox);      // 下拉列表添加到 面板panel 中

        // 将两个 单选按钮 组装到一个 ButtonGroup 中，确保同时只能选择一个。
        groupRadioButton = new JRadioButton("Random Group");
        studentRadioButton = new JRadioButton("Random Student");
        ButtonGroup buttonGroup = new ButtonGroup();      // 按钮组
        buttonGroup.add(groupRadioButton);
        buttonGroup.add(studentRadioButton);
        panel.add(groupRadioButton);
        panel.add(studentRadioButton);

        // 添加开始按钮到面板
        startButton = new JButton("Start");
        panel.add(startButton);

        // 添加回到主菜单按钮的面板
        BackMainButton = new JButton("Back to Main Menu");
        panel.add(BackMainButton);

        // 将面板添加到 JFrame
        add(panel);
    }


    // getter
    public JButton getStartButton() {
        return startButton;
    }

    public JButton getBackMainButton() {
        return BackMainButton;
    }

    // 获取当前选择的班级。
    public String getSelectedClass() {
        return (String) classComboBox.getSelectedItem();
    }


    // 检查哪个单选按钮被选中。
    public boolean isGroupSelected() {
        return groupRadioButton.isSelected();
    }

    public boolean isStudentSelected() {
        return studentRadioButton.isSelected();
    }

    // 将一个班级名称添加到下拉列表中
    public void addClass(String className) {
        classComboBox.addItem(className);
    }
}

