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
        setTitle("Register");          // 标题
        setSize(400, 300); // 尺寸
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   // 退出时关闭
        setLocationRelativeTo(null);       // 居中

        // 建立了一个面板panel，四行两列
        JPanel panel = new JPanel(new GridLayout(4, 2));
        // 关于用户名的控件
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        // 关于密码的控件
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // 关于确认密码的控件
        panel.add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField();
        panel.add(confirmPasswordField);

        // 关于注册按钮的控件
        registerButton = new JButton("Register");
        panel.add(registerButton);

        BackToMainButton = new JButton("Back to Main Menu");
        panel.add(BackToMainButton);
        BackToMainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        add(panel);
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
