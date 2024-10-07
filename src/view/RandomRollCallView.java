package view;

import javax.swing.*;
import java.awt.*;

public class RandomRollCallView extends JFrame {
    // 页面组件
    private JComboBox<String> classComboBox;      // 用于选择班级的下拉列表。
    private JRadioButton groupRadioButton;        // 选择“随机小组”的单选标签
    private JRadioButton studentRadioButton;      // 选择“随机学生"的单选标签
    private JButton startButton;                  // 开始点名操作按钮
    private JButton backMainButton;               // 回到主页面按钮
    private JTextArea resultTextArea;            // 显示随机点名结果的多行文本域

    public RandomRollCallView() {
        setTitle("Random Roll Call");       // 标题
        setSize(300, 300);     // 增加大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   // 只关闭本页面退出
        setLocationRelativeTo(null);       // 居中

        // 使用 BorderLayout 来允许多行文本域占据更多空间
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 创建一个面板用于存放控件
        JPanel controlPanel = new JPanel(new GridLayout(3, 1)); // 3‘ 行 1 列布局

        // 添加标签和 JComboBox 到控制面板
        classComboBox = new JComboBox<>();     // 下拉列表组件
        controlPanel.add(new JLabel("Select Class:"));    // 添加标签
        controlPanel.add(classComboBox);      // 下拉列表添加到 控件面板中

        // 将两个 单选按钮 组装到一个 ButtonGroup 中，实现多选框单选，并添加到控制面板
        groupRadioButton = new JRadioButton("Random Group");
        studentRadioButton = new JRadioButton("Random Student");
        ButtonGroup buttonGroup = new ButtonGroup();      // 按钮组
        buttonGroup.add(groupRadioButton);
        buttonGroup.add(studentRadioButton);
        controlPanel.add(groupRadioButton);
        controlPanel.add(studentRadioButton);

        // 添加开始按钮到控制面板
        startButton = new JButton("Start");
        controlPanel.add(startButton);

        // 添加回到主菜单按钮
        backMainButton = new JButton("Back to Main Menu");
        controlPanel.add(backMainButton);

        // 将控制面板添加到主面板的北部
        panel.add(controlPanel, BorderLayout.NORTH);

        // 创建多行文本域，设置为不可编辑，并将其添加到面板
        resultTextArea = new JTextArea(); // 默认行列
        resultTextArea.setEditable(false); // 设置为只读
        JScrollPane scrollPane = new JScrollPane(resultTextArea); // 添加滚动条
        panel.add(scrollPane, BorderLayout.CENTER); // 将滚动面板添加到主面板的中央

        // 将面板添加到 JFrame
        add(panel);
    }

    // getter
    public JButton getStartButton() {
        return startButton;
    }

    public JButton getBackMainButton() {
        return backMainButton;
    }

    // 获取当前选择的班级。
    public String getSelectedClass() {
        return (String) classComboBox.getSelectedItem();
    }

    // 将一个班级名称添加到下拉列表中
    public void addClass(String className) {
        classComboBox.addItem(className);
    }

    // 检查哪个单选按钮被选中。
    public boolean isGroupSelected() {
        return groupRadioButton.isSelected();
    }

    public boolean isStudentSelected() {
        return studentRadioButton.isSelected();
    }

    // 在结果文本域中添加文本
    public void appendResult(String result) {
        resultTextArea.append(result + "\n");
    }
}
