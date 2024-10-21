package controller;

import view.MainMenuView;
import view.StudentManagementView;
import view.RandomRollCallView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 主菜单控制器类，负责主菜单页面进入两个功能的逻辑
public class MainMenuController {
    // 主菜单视图的引用
    private MainMenuView mainMenuView;


    // 构造函数，接收主菜单视图
    public MainMenuController(MainMenuView mainMenuView) {
        // 实例化
        this.mainMenuView = mainMenuView;
        // 为随机点名按钮添加监听器
        this.mainMenuView.getRandomRollCallButton().addActionListener(new RandomRollCallListener());
        // 为学生管理按钮添加监听器
        this.mainMenuView.getManageStudentsButton().addActionListener(new ManageStudentsListener());
    }

    // 随机点名按钮的监听器类，按下按钮触发
    class RandomRollCallListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 显示主菜单视图，省下了回退按钮
            mainMenuView.setVisible(true);
            // 随机点名视图实例化，控制器实例化
            RandomRollCallView randomRollCallView = new RandomRollCallView();
            RandomRollCallController randomRollCallController = new RandomRollCallController(randomRollCallView);
            // 随机点名菜单显示
            randomRollCallView.setVisible(true);
        }
    }

    // 学生管理按钮的监听器类，按下按钮触发
    class ManageStudentsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 显示主菜单视图，省下了回退按钮
            mainMenuView.setVisible(true);
            // 学生管理视图实例化
            StudentManagementView studentManagementView = new StudentManagementView();
            StudentManagementController studentManagementController = new StudentManagementController(studentManagementView);
            // 可视化页面
            studentManagementView.setVisible(true);
        }
    }
}
