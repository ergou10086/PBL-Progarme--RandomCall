package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 主菜单界面类
public class MainMenuView extends JFrame {
    private JButton randomRollCallButton;
    private JButton manageStudentsButton;
    private JButton switchAccounts;
    private LoginView loginView;

    // 构造方法，用于初始化窗口及其组件，传入注册页面作为参数保证控制的始终是一个注册页面
    public MainMenuView(LoginView loginView) {
        setTitle("Main Menu");  // 设置窗口的标题为Main Menu
        setSize(300, 200);      // 窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 窗口关闭
        setLocationRelativeTo(null); // 居中显示

        this.loginView = loginView; // 将传入的 loginView 赋值给实例变量

        // 创建一个网格布局面板
        JPanel panel = new JPanel(new GridLayout(3, 1));

        // 创建并添加按钮
        randomRollCallButton = new JButton("Random Roll Call");
        panel.add(randomRollCallButton);
        manageStudentsButton = new JButton("Manage Students");
        panel.add(manageStudentsButton);
        switchAccounts = new JButton("Switch Accounts");
        panel.add(switchAccounts);

        // 设置SwitchAccounts按钮的事件处理
        switchAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginView.setVisible(true); // 显示登录页面
                dispose(); // 关闭主菜单窗口
            }
        });

        // 将面板添加到窗口
        add(panel);
    }

    // get，set方法
    public JButton getRandomRollCallButton() {
        return randomRollCallButton;
    }

    public JButton getManageStudentsButton() {
        return manageStudentsButton;
    }

    public JButton getSwitchAccounts() {
        return switchAccounts;
    }

    public void setRandomRollCallButton(JButton randomRollCallButton) {
        this.randomRollCallButton = randomRollCallButton;
    }

    public void setManageStudentsButton(JButton manageStudentsButton) {
        this.manageStudentsButton = manageStudentsButton;
    }

    public void setSwitchAccounts(JButton switchAccounts) {
        this.switchAccounts = switchAccounts;
    }
}
