package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 随机点名视图类
public class RandomRollCallView extends JFrame {
    // 页面组件
    private JList<String> classList;              // 用于选择班级的列表
    private JRadioButton groupRadioButton;        // 选择“随机小组”的单选标签
    private JRadioButton studentRadioButton;      // 选择“随机学生"的单选标签
    private JButton startButton;                  // 开始点名操作按钮
    private JButton backMainButton;               // 回到主页面按钮
    private JButton editScoreButton;              // 调整学生成绩
    private JButton checkStudentScoreButton;      // 查询学生成绩按钮
    private JTextArea resultTextArea;             // 显示随机点名结果的多行文本域
    private JButton exportButton;

    public RandomRollCallView() {
        setTitle("随机点名系统");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // 主面板使用 BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 创建顶部面板
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 班级选择区域
        JPanel classPanel = new JPanel(new BorderLayout());
        classPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            "班级选择"));
        DefaultListModel<String> classListModel = new DefaultListModel<>();
        classList = new JList<>(classListModel);
        classList.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        JScrollPane classListScrollPane = new JScrollPane(classList);
        classListScrollPane.setPreferredSize(new Dimension(150, 80));
        classListModel.addElement("全局选项");
        classPanel.add(classListScrollPane, BorderLayout.CENTER);

        // 随机选择模式区域
        JPanel modePanel = new JPanel();
        modePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            "选择模式"));
        groupRadioButton = new JRadioButton("随机小组");
        studentRadioButton = new JRadioButton("随机学生");
        groupRadioButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        studentRadioButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(groupRadioButton);
        buttonGroup.add(studentRadioButton);
        modePanel.add(groupRadioButton);
        modePanel.add(studentRadioButton);

        // 操作按钮区域
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            "操作区"));

        // 创建按钮
        startButton = new JButton("开始点名");
        editScoreButton = new JButton("编辑成绩");
        checkStudentScoreButton = new JButton("查看成绩");
        exportButton = new JButton("导出成绩单");

        // 统一设置按钮样式
        Font buttonFont = new Font("微软雅黑", Font.PLAIN, 14);
        Dimension buttonSize = new Dimension(120, 35);
        
        for (JButton button : new JButton[]{startButton, editScoreButton, checkStudentScoreButton, exportButton}) {
            button.setFont(buttonFont);
            button.setPreferredSize(buttonSize);
        }

        buttonPanel.add(startButton);
        buttonPanel.add(editScoreButton);
        buttonPanel.add(checkStudentScoreButton);
        buttonPanel.add(exportButton);

        // 返回按钮
        backMainButton = new JButton("返回主菜单");
        backMainButton.setFont(buttonFont);
        backMainButton.setPreferredSize(buttonSize);
        backMainButton.addActionListener(e -> dispose());

        // 使用 GridBagLayout 添加组件
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        topPanel.add(classPanel, gbc);

        gbc.gridy = 1;
        topPanel.add(modePanel, gbc);

        gbc.gridy = 2;
        topPanel.add(buttonPanel, gbc);

        gbc.gridy = 3;
        topPanel.add(backMainButton, gbc);

        // 结果显示区域
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        resultTextArea.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            "点名结果"));
        scrollPane.setPreferredSize(new Dimension(400, 200)); // 调整显示区域的大小

        // 添加到主面板
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }


    // 获取当前选择的班级数组
    public String[] getSelectedClass() {
        // getSelectedValuesList用于获取 JList 中当前选中的所有值
        return classList.getSelectedValuesList().toArray(new String[0]);
    }

    // 将一个班级名称添加到下拉列表中
    public void addClass(String className) {
        // 从页面中获取班级选择列表的实体
        DefaultListModel<String> model = (DefaultListModel<String>) classList.getModel();
        // 将新的元素 className 添加到 DefaultListModel 中，这个牛逼就是可以无需刷新立刻显示在ui上
        model.addElement(className);
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


    // getter 和 setter
    public JButton getStartButton() {
        return startButton;
    }

    public JButton getBackMainButton() {
        return backMainButton;
    }

    public JRadioButton getGroupRadioButton() {
        return groupRadioButton;
    }

    public void setGroupRadioButton(JRadioButton groupRadioButton) {
        this.groupRadioButton = groupRadioButton;
    }

    public JRadioButton getStudentRadioButton() {
        return studentRadioButton;
    }

    public void setStudentRadioButton(JRadioButton studentRadioButton) {
        this.studentRadioButton = studentRadioButton;
    }

    public void setStartButton(JButton startButton) {
        this.startButton = startButton;
    }

    public void setBackMainButton(JButton backMainButton) {
        this.backMainButton = backMainButton;
    }

    public JButton getEditScoreButton() {
        return editScoreButton;
    }

    public void setEditScoreButton(JButton editScoreButton) {
        this.editScoreButton = editScoreButton;
    }

    public JButton getCheckStudentScoreButton() {
        return checkStudentScoreButton;
    }

    public JButton getExportButton() {
        return exportButton;
    }
}

