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
        setTitle("学生管理系统 - 登录");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 加载背景图片
        try {
            backgroundImage = ImageIO.read(new File("图片素材/1-gigapixel-scale-4x.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建半透明的登录面板
        JPanel loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255, 255, 255, 200));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        loginPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // 添加标题
        JLabel titleLabel = new JLabel("欢迎登录", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);

        // 用户名输入区域
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel usernameLabel = new JLabel("用户名:");
        usernameLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(52, 73, 94));
        loginPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(200, 30));
        usernameField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        loginPanel.add(usernameField, gbc);

        // 密码输入区域
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(52, 73, 94));
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 30));
        loginPanel.add(passwordField, gbc);

        // 选项区域
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        optionsPanel.setOpaque(false);

        rememberMeButton = new JRadioButton("记住密码");
        rememberMeButton.setFont(new Font("微软雅黑", Font.BOLD, 12));
        rememberMeButton.setForeground(new Color(52, 73, 94));
        rememberMeButton.setOpaque(false);
        
        nextNotLoginCheckBox = new JRadioButton("下次自动登录");
        nextNotLoginCheckBox.setFont(new Font("微软雅黑", Font.BOLD, 12));
        nextNotLoginCheckBox.setForeground(new Color(52, 73, 94));
        nextNotLoginCheckBox.setOpaque(false);

        optionsPanel.add(rememberMeButton);
        optionsPanel.add(nextNotLoginCheckBox);
        loginPanel.add(optionsPanel, gbc);

        // 按钮区域
        gbc.gridy = 4;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        loginButton = new JButton("登录");
        loginButton.setPreferredSize(new Dimension(100, 35));
        loginButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        registerButton = new JButton("注册");
        registerButton.setPreferredSize(new Dimension(100, 35));
        registerButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        registerButton.setBackground(new Color(100, 180, 100));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        loginPanel.add(buttonPanel, gbc);

        // 创建背景面板
        JPanel backgroundPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.add(loginPanel);

        // 保持原有的事件监听器
        nextNotLoginCheckBox.addActionListener(e -> {
            if (nextNotLoginCheckBox.isSelected()) {
                JOptionPane.showMessageDialog(LoginView.this, 
                    "别，你要是懒了，我就不会写了", "你先别急", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        rememberMeButton.addActionListener(e -> {
            DarkIntoCount += 1;
            if (DarkIntoCount % 4 == 0) {
                LoginController loginController = new LoginController(LoginView.this);
                loginController.autoLogin();
            }
        });

        setContentPane(backgroundPanel);
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

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }
}
