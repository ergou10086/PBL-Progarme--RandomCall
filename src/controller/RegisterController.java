package controller;

import exceptions.RegisterExceptions.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import service.UserService;
import service.impl.UserServiceImpl;
import view.RegisterView;

public class RegisterController {
    private RegisterView registerView;
    private UserService userService;

    public RegisterController(RegisterView registerView) {
        this.registerView = registerView;
        this.userService = new UserServiceImpl();
        this.registerView.getRegisterButton().addActionListener(new RegisterListener());
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = registerView.getUsername();
            String password = registerView.getPassword();
            String confirmPassword = registerView.getConfirmPassword();

            try {
                // 验证用户输入
                validateInput(username, password, confirmPassword);
                
                // 尝试注册
                if (userService.register(username, password)) {
                    JOptionPane.showMessageDialog(registerView, "注册成功！");
                    registerView.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(registerView, ex.getMessage(), "注册错误", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void validateInput(String username, String password, String confirmPassword) 
                throws InvalidUsernameException, InvalidPasswordException, ConfirmErrorPasswordException {
            // 验证用户名
            if (username == null || username.trim().isEmpty()) {
                throw new InvalidUsernameException("用户名不能为空！");
            }

            // 验证密码
            if (password == null || password.trim().isEmpty()) {
                throw new InvalidPasswordException("密码不能为空！");
            }

            // 验证确认密码
            if (!password.equals(confirmPassword)) {
                throw new ConfirmErrorPasswordException("两次输入的密码不一致！");
            }
        }
    }
}
