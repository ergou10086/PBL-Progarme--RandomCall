package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*; // 导入事件处理的包
import java.awt.image.*;

import controller.LoginController;
import controller.MainMenuController;

// 登录界面类
public class LoginView extends JFrame {
    private JTextField usernameField;        // 创建一个单行的文本输入框,接受用户名
    private JPasswordField passwordField;    // 用于密码输入的文本字段组件，接受密码
    private JButton loginButton;             // 创建按钮的 Swing 组件变量，用于存放登录按钮
    private JButton registerButton;          // registerButton存放注册按钮
    private JRadioButton rememberMeButton;     // 记住密码单选框
    private JRadioButton nextNotLoginCheckBox; // 下次不登录单选框
    private BufferedImage backgroundImage;   // 背景图片
    private int DarkIntoCount = 0;   // 暗道控件，记录独立暗道点击次数，四次直接进入

    public LoginView() {
        setTitle("Login");                                  // 设置窗口标题为 "Login"
        setSize(400, 250);                      // 窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // 关闭窗口
        setLocationRelativeTo(null);                        // 窗口居中显示

        // 创建一个面板panel，使用网格布局，4行2列。
        JPanel panel = new JPanel(new GridLayout(4, 2)); // 修改为5行2列以适应新增的复选框

        // 添加用户名标签，并创建用户名输入框，把标签加入panel
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        // 添加密码标签，并创建用户名输入框，把标签加入
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // 记住密码的单选框
        rememberMeButton = new JRadioButton("Remember Me");
        panel.add(rememberMeButton);

        // 创建下次不登录的复选框
        nextNotLoginCheckBox = new JRadioButton("Next time don't login");
        panel.add(nextNotLoginCheckBox);

        // 创建登录按钮并添加到面板
        loginButton = new JButton("Login");
        panel.add(loginButton);

        // 创建注册按钮并添加到面板
        registerButton = new JButton("Register");
        panel.add(registerButton);

        // 将面板添加到框架中
        add(panel);

        // 为下次无需输入密码的添加监听器
        nextNotLoginCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nextNotLoginCheckBox.isSelected()) {
                    // 弹出提示窗口
                    JOptionPane.showMessageDialog(LoginView.this, "别，你要是懒了，我就不会写了", "你先别急", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // 暗道的监听器
        rememberMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DarkIntoCount += 1;
                // 四次后暗道开放
                if (DarkIntoCount == 4) {
                    // 传递当前视图，进行自动登录
                    LoginController loginController = new LoginController(LoginView.this);
                    loginController.autoLogin(); // 触发自动登录
                }
            }
        });

    }

    // getter方法
    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

}
