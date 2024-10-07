package controller;

import java.io.*;
import java.util.*;

import view.LoginView;
import view.MainMenuView;
import view.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 处理登录逻辑的类
public class LoginController {

    private LoginView loginView;   // 登录视图的属性，访问视图中的组件
    private MainMenuView mainMenuView; // 引用主菜单视图
    private HashMap<String, String> userDatabase; // 用户的数据库属性

    // 构造函数，在这里连接注册视图
    public LoginController(LoginView loginView) {
        // 初始化 loginView 属性和主菜单视图
        this.loginView = loginView;
        this.mainMenuView = mainMenuView;

        // 初始化用户数据库
        this.userDatabase = new HashMap<>();
        loadUserDatabase(); // 加载用户数据库

        // 为登录按钮和注册按钮添加事件监听器
        this.loginView.getLoginButton().addActionListener(new LoginListener());
        this.loginView.getRegisterButton().addActionListener(new RegisterListener());
    }

    // 加载用户数据库
    private void loadUserDatabase() {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;    //按行读入
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    userDatabase.put(parts[0], parts[1]); // 前用户名后密码
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 内部类，处理登录按钮点击事件。
    class LoginListener implements ActionListener {
        @Override
        // 登录按钮的事件监听器
        public void actionPerformed(ActionEvent e) {
            //获取用户输入的用户名和密码。
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            // 调用 isValidUser 方法验证登录。
            if (isValidUser(username, password)) {
                // 登陆成功弹出提示框
                JOptionPane.showMessageDialog(loginView, "Login successful!");
                // 打开主页面视图，并控制其逻辑
                MainMenuView mainMenuView = new MainMenuView(loginView);
                MainMenuController mainMenuController = new MainMenuController(mainMenuView);
                // 显示主页面视图
                mainMenuView.setVisible(true);
                // 关闭登陆页面，避免重开
                loginView.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(loginView, "Invalid username or password!");
            }
        }

        // 用户登录的验证逻辑实现
        private boolean isValidUser(String username, String password) {
            return userDatabase.containsKey(username) && userDatabase.get(username).equals(password);
        }
    }


    // 内部类，用于处理注册按钮点击事件
    class RegisterListener implements ActionListener {
        @Override
        // 事件监听器，当注册按钮被点击时执行
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




