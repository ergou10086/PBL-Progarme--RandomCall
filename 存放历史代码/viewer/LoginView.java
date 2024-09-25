package view;

import javax.swing.*;
import java.awt.*;

// 登录界面类
public class LoginView extends JFrame {
    private JTextField usernameField;     // 创建一个单行的文本输入框,接受用户名
    private JPasswordField passwordField; // 用于密码输入的文本字段组件，接受密码
    private JButton loginButton;          // 创建按钮的 Swing 组件变量，用于存放登录按钮

    // 构造函数
    public LoginView() {
        setTitle("Login");    // 设置窗口标题为 "Login"
        setSize(400, 250);     // 窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // 设置窗口关闭
        setLocationRelativeTo(null);       // 窗口居中显示

        // 创建一个面板panel，使用网格布局，3行2列。
        JPanel panel = new JPanel(new GridLayout(3, 2));

        // 添加用户名标签，并创建用户名输入框，把标签加入panel
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        // 添加密码标签，并创建用户名输入框，把标签加入
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // 创建登录按钮并添加到面板
        loginButton = new JButton("Login");
        panel.add(loginButton);

        // 将面板添加到框架中
        add(panel);
    }

    // get方法
    public JButton getLoginButton() {
        return loginButton;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}

