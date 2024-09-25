package view;

import javax.swing.*;
import java.awt.*;

// 主菜单界面类
public class MainMenuView extends JFrame {
    // 这两个成员变量在主菜单上显示“随机点名”和“管理学生”
    private JButton randomRollCallButton;
    private JButton manageStudentsButton;

    // 构造方法，用于初始化窗口及其组件
    public MainMenuView() {

        setTitle("Main Menu");  //设置窗口的标题为“Main Menu”
        setSize(300, 200);   // 窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // 窗口关闭
        setLocationRelativeTo(null);     // 居中显示

        // 创建一个网格布局面板，该管理器将面板分为 2 行 1 列的布局。
        JPanel panel = new JPanel(new GridLayout(2, 1));

        //把两个按钮打上标签并且添加到面板中
        randomRollCallButton = new JButton("Random Roll Call");
        panel.add(randomRollCallButton);
        manageStudentsButton = new JButton("Manage Students");
        panel.add(manageStudentsButton);

        // 把面板添加到窗口
        add(panel);
    }

    // get方法
    public JButton getRandomRollCallButton() {
        return randomRollCallButton;
    }

    public JButton getManageStudentsButton() {
        return manageStudentsButton;
    }
}

