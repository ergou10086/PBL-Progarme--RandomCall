package controller;

import view.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import exceptions.RegisterExceptions.*;

// 注册控制器类
public class RegisterController {
    private final RegisterView registerView; // 注册视图对象
    private HashMap<String, String> EuserDatabase; // 用户数据库，按键值对对存用户名和密码

    // 构造函数，初始化注册视图和用户数据库
    public RegisterController(RegisterView registerView) {
        this.registerView = registerView; // 注册视图
        this.EuserDatabase = EuserDatabase;  // 用户数据库
        this.registerView.getRegisterButton().addActionListener(new RegisterListener()); // 为注册按钮添加监听器
        loadUserDatabase(); // 加载用户数据库
    }


    // 加载用户数据库的方法
    private void loadUserDatabase() {
        EuserDatabase = new HashMap<>(); // 初始化用户数据库
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) { // 使用BufferedReader读取用户文件
            String line;
            // 按行读取文件
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":"); // 以冒号分割用户名和密码
                if (parts.length == 2) {  // 确保有两个部分,这样用户名和密码都能被正确读取
                    EuserDatabase.put(parts[0], parts[1]); // 将用户名和密码存入数据库，严格按照两部分
                }
            }
        } catch (IOException e) { // 捕获IO异常
            e.printStackTrace(); // 打印异常信息
        }
    }


    // 保存用户数据库的方法
    private void saveUserDatabase() {
        // 使用BufferedWriter写入注册用户文件的信息到users.txt内
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"))) {
            // 遍历用户数据库
            for (String username : EuserDatabase.keySet()) {
                bw.write(username + ":" + EuserDatabase.get(username)); // 写入用户名和密码
                bw.newLine(); // 换行
            }
        } catch (IOException e) { // 捕获IO异常
            e.printStackTrace(); // 打印异常信息
        }
    }


    // 内部类，处理注册按钮的点击事件
    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { // 重写方法，事件监听
            // 获取输入的用户名、密码和确认密码
            String username = registerView.getUsername();
            String password = registerView.getPassword();
            String confirmPassword = registerView.getConfirmPassword();

            // 检查用户名和密码输入的有效性
            if (!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
                // 检查用户名是否已存在
                if (EuserDatabase.containsKey(username)) {
                    try {
                        throw new UsernameAlreadyExistsException("用户名已存在!");
                    } catch (UsernameAlreadyExistsException ex) {
                        throw new RuntimeException(ex);
                    }finally {
                        JOptionPane.showMessageDialog(registerView, "用户名已存在！"); // 弹出对话框提示
                    }
                }else if(password.length() < 6 || password.length() > 16) {
                    try {
                        throw new InvalidPasswordException("密码长度必须至少为6个字符!并且小于16个字符");
                    } catch (InvalidPasswordException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        JOptionPane.showMessageDialog(registerView, "密码过长过短！");  // 弹出对话框提示
                    }
                }else if(!confirmPassword.equals(password)){
                    try {
                        throw new ConfirmErrorPasswordException("确认密码和密码输入不一致");
                    }catch (ConfirmErrorPasswordException ex) {
                        throw new RuntimeException(ex);
                    }finally {
                        JOptionPane.showMessageDialog(registerView, "两次密码输入不一致！");  // 弹出对话框提示
                    }
                }else{
                    EuserDatabase.put(username, password); // 将新用户添加到数据库
                    saveUserDatabase(); // 保存用户数据库
                    JOptionPane.showMessageDialog(registerView, "注册成功！"); // 弹出成功提示
                    registerView.dispose(); // 关闭注册窗口
                }
            } else {
                // 如果输入无效，提示用户
                JOptionPane.showMessageDialog(registerView, "请正确填写所有字段！");
            }
        }
    }
}
