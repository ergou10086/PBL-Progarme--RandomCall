package controller;

import exceptions.LoginExceptions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

import view.LoginView;
import view.MainMenuView;
import service.UserService;
import service.impl.UserServiceImpl;
import view.RegisterView;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.swing.*;

public class LoginController {
    private LoginView loginView;
    private MainMenuView mainMenuView;
    private UserService userService;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.userService = new UserServiceImpl();

        this.loginView.getLoginButton().addActionListener(new LoginListener());
        this.loginView.getRegisterButton().addActionListener(new RegisterListener(loginView));
    }

    private void login(String username, String password) throws LoginExceptions {
        try {
            if (userService.login(username, password)) {
                if (mainMenuView == null) {
                    mainMenuView = new MainMenuView(loginView);
                    new MainMenuController(mainMenuView);
                }
                JOptionPane.showMessageDialog(loginView, "登录成功！");
                mainMenuView.setVisible(true);
                loginView.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(loginView, "用户名或密码错误！");
                throw new LoginExceptions("无效的用户名或密码！");
            }
        } catch (Exception e) {
            throw new LoginExceptions(e.getMessage());
        }
    }

    public class LoginListener implements ActionListener, KeyListener {
        public LoginListener() {
            loginView.getUsernameField().addKeyListener(this);
            loginView.getPasswordField().addKeyListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            try {
                login(username, password);
            } catch (LoginExceptions ex) {
                JOptionPane.showMessageDialog(loginView, ex.getMessage());
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "Enter Key Pressed"));
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyTyped(KeyEvent e) {}
    }

    public void autoLogin() {
        String defaultUsername = "admin";
        String defaultPassword = "123456";
        try {
            login(defaultUsername, defaultPassword);
        } catch (LoginExceptions e) {
            JOptionPane.showMessageDialog(loginView, e.getMessage());
        }
    }

    public static class RegisterListener implements ActionListener {
        private LoginView loginView;

        public RegisterListener(LoginView loginView) {
            this.loginView = loginView;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterView registerView = new RegisterView();
            new RegisterController(registerView);
            registerView.setVisible(true);
        }
    }
}




