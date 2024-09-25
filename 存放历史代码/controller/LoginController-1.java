package controller;

import view.LoginView;
import view.MainMenuView;
import view.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 处理登录逻辑的类
public class LoginController {

    private LoginView loginView;   // 登录视图的属性，访问视图中的组件


    // 构造函数
    public LoginController(LoginView loginView) {
        // 初始化 loginView 属性
        this.loginView = loginView;
        // 为登录按钮和注册按钮添加事件监听器
        this.loginView.getLoginButton().addActionListener(new LoginListener());
        this.loginView.getRegisterButton().addActionListener(new RegisterListener());
    }


    // 内部类，处理登录按钮点击事件。
    class LoginListener implements ActionListener {
        @Override
        // 当登录按钮被点击时执行
        public void actionPerformed(ActionEvent e) {
            //获取用户输入的用户名和密码。
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            // 调用 isValidUser 方法验证登录。
            if (isValidUser(username, password)) {
                // 登陆成功提示
                JOptionPane.showMessageDialog(loginView, "Login successful!");
                // 打开主页面视图，并控制其逻辑
                MainMenuView mainMenuView = new MainMenuView();
                MainMenuController mainMenuController = new MainMenuController(mainMenuView);
                // 显示主页面视图
                mainMenuView.setVisible(true);
                // 关闭登陆页面，避免重开
                loginView.dispose();
            } else {
                JOptionPane.showMessageDialog(loginView, "Invalid username or password!");
            }
        }

        private boolean isValidUser(String username, String password) {
            // 这里实现验证用户登录的验证逻辑
            return true;
        }
    }


    // 内部类，用于处理注册按钮点击事件
    class RegisterListener implements ActionListener {
        @Override
        // 当注册按钮被点击时执行
        public void actionPerformed(ActionEvent e) {
            // 创建一个新的注册视图
            RegisterView registerView = new RegisterView();
            // 创建 RegisterController 的实例，负责处理注册逻辑
            RegisterController registerController = new RegisterController(registerView);
            // 打开注册视图
            registerView.setVisible(true);
        }
    }
}



