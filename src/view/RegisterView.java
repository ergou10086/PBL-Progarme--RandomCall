package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame {
    private JTextField usernameField;              // 用于用户名输入的文本域
    private JPasswordField passwordField;          // 用于密码输入的文本域
    private JPasswordField confirmPasswordField;   // 用于确认密码的文本域
    private JButton registerButton;                // 注册按钮
    private JButton BackToMainButton;              // 回退到主页面按钮

    public RegisterView() {
        setTitle("学生管理系统 - 注册");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // 创建标题
        JLabel titleLabel = new JLabel("用户注册", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 创建表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // 用户名输入
        addFormField(formPanel, "用户名:", usernameField = new JTextField(), gbc, 0);

        // 密码输入
        addFormField(formPanel, "密码:", passwordField = new JPasswordField(), gbc, 1);

        // 确认密码
        addFormField(formPanel, "确认密码:", confirmPasswordField = new JPasswordField(), gbc, 2);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        
        registerButton = new JButton("注册");
        registerButton.setPreferredSize(new Dimension(100, 35));
        registerButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        
        BackToMainButton = new JButton("返回");
        BackToMainButton.setPreferredSize(new Dimension(100, 35));
        BackToMainButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        BackToMainButton.addActionListener(e -> dispose());

        buttonPanel.add(registerButton);
        buttonPanel.add(BackToMainButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void addFormField(JPanel panel, String labelText, JTextField field, 
            GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        panel.add(label, gbc);

        gbc.gridx = 1;
        field.setPreferredSize(new Dimension(200, 30));
        field.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        panel.add(field, gbc);
    }

    // getter
    public JButton getRegisterButton() {
        return registerButton;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }


    //setter
    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public void setConfirmPasswordField(JPasswordField confirmPasswordField) {
        this.confirmPasswordField = confirmPasswordField;
    }

    public void setRegisterButton(JButton registerButton) {
        this.registerButton = registerButton;
    }
}
