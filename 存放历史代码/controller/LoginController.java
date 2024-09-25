package controller;

import view.LoginView;
import view.MainMenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 负责登录逻辑
public class LoginController {

    // 声明一个LoginView对象，用于与登录界面交互
    private LoginView loginView;

    // 构造函数，接收一个LoginView对象
    public LoginController(LoginView loginView) {
        // 赋值给成员变量
        this.loginView = loginView;
        // 为登录按钮添加事件监听器，被点击时触发LoginListener
        this.loginView.getLoginButton().addActionListener(new LoginListener());
    }

    // 内部类，处理登录按钮的点击事件
    class LoginListener implements ActionListener {
        // 当登录按钮被点击时调用
        @Override
        public void actionPerformed(ActionEvent r) {
            // 获取用户输入的用户名和密码
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            // 简单的验证逻辑
            if ("admin".equals(username) && "password".equals(password)) {
                // 登录成功，隐藏登录界面
                loginView.setVisible(false);
                // 打开主菜单视图并控制其行为
                MainMenuView mainMenuView = new MainMenuView();
                MainMenuController mainMenuController = new MainMenuController(mainMenuView);
                // 显示主菜单界面
                mainMenuView.setVisible(true);
            } else {
                // 如果验证失败，弹出错误提示框
                JOptionPane.showMessageDialog(loginView, "Invalid username or password");
            }
        }
    }
}

