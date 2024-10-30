package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import controller.LoginController;

// 登录界面类
public class LoginView extends JFrame {
    private JTextField usernameField;        // 创建一个单行的文本输入框,接受用户名
    private JPasswordField passwordField;    // 用于密码输入的文本字段组件，接受密码
    private JButton loginButton;             // 用于存放登录按钮
    private JButton registerButton;          // 存放注册按钮
    private JRadioButton rememberMeButton;     // 记住密码单选框
    private JRadioButton nextNotLoginCheckBox; // 下次不登录单选框
    private BufferedImage backgroundImage;   // 背景图片
    private int DarkIntoCount = 0;           // 暗道控件，记录独立暗道点击次数，四次直接进入

    public LoginView() {
        setTitle("登录窗口");                                  // 窗口标题
        setSize(590, 380);                      // 窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // 关闭窗口
        setLocationRelativeTo(null);                        // 窗口居中显示

        // 加载背景图片
        try {
            backgroundImage = ImageIO.read(new File("图片素材/1-gigapixel-scale-4x.jpg")); // 设置背景图片路径
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // 使用 BoxLayout 垂直排列

        // 绘制背景的面板作为底面板
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        backgroundPanel.setLayout(new GridBagLayout()); // 使用 GridBagLayout 控件布局

        // 创建 GridBagConstraints 对象用于设置控件位置
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 4, 10, 4); // 控件之间的间距

        // 添加用户名标签和输入框
        gbc.gridx = 0; // 第一列
        gbc.gridy = 0; // 第一行
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16)); // 设置字体和大小
        backgroundPanel.add(usernameLabel, gbc);

        gbc.gridx = 1; // 第二列
        usernameField = new JTextField(17); // 输入框长度
        usernameField.setPreferredSize(new Dimension(45, 20));   // 输入框大小
        backgroundPanel.add(usernameField, gbc);

        // 添加密码标签和输入框
        gbc.gridx = 0; // 第一列
        gbc.gridy = 1; // 第二行
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16)); // 设置字体和大小
        backgroundPanel.add(passwordLabel, gbc);

        gbc.gridx = 1; // 第二列
        passwordField = new JPasswordField(17); // 密码输入框长度
        passwordField.setPreferredSize(new Dimension(45, 20));  // 输入框大小
        backgroundPanel.add(passwordField, gbc);

        // 记住密码的单选框
        gbc.gridx = 0; // 第一列
        gbc.gridy = 2; // 第三行
        rememberMeButton = new JRadioButton("Remember Me");
        backgroundPanel.add(rememberMeButton, gbc);

        // 创建下次不登录的复选框
        gbc.gridx = 0; // 第一列
        gbc.gridy = 3; // 第四行
        nextNotLoginCheckBox = new JRadioButton("Next time don't login");
        backgroundPanel.add(nextNotLoginCheckBox, gbc);

        // 创建登录按钮
        gbc.gridx = 0; // 第一列
        gbc.gridy = 4; // 第五行
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(80, 40)); // 设置登录按钮大小
        backgroundPanel.add(loginButton, gbc);

        // 创建注册按钮
        gbc.gridx = 1; // 第二列
        registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(80, 40)); // 设置注册按钮大小
        backgroundPanel.add(registerButton, gbc);

        // 将背景面板添加到主面板
        mainPanel.add(backgroundPanel);

        add(mainPanel);

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
                if (DarkIntoCount % 4 == 0) {
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
