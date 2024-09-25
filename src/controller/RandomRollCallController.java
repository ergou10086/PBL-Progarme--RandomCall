package controller;

import java.io.IOException;
import model.Student;
import model.StudentManager;
import view.RandomRollCallView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

// 随机点名控制器类，实现随机点名的逻辑
public class RandomRollCallController {

    private RandomRollCallView randomRollCallView; // 随机点名视图对象
    private StudentManager studentManager; // 学生管理器对象

    // 控制器构造函数，初始化视图和学生管理器，并设置按钮事件监听
    public RandomRollCallController(RandomRollCallView randomRollCallView) {
        this.randomRollCallView = randomRollCallView;
        this.studentManager = new StudentManager();
        // 点名按钮的事件监听器
        this.randomRollCallView.getStartButton().addActionListener(new StartRollCallListener());
    }

    // 处理开始点名的事件的内部类
    class StartRollCallListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取视图中选中的班级
            String selectedClass = randomRollCallView.getSelectedClass();
            // 获取选择的是小组随机还是学生随机
            boolean isGroupSelected = randomRollCallView.isGroupSelected();
            boolean isStudentSelected = randomRollCallView.isStudentSelected();

            // 从学生管理器类获取学生列表
            List<Student> students = studentManager.getStudents();
            // 实例化随机数生成器
            Random random = new Random();

            // 选择小组
            if (isGroupSelected) {
                /*
                List<String> groups = studentManager.getGroups(selectedClass); // Assuming this method exists
                if (!groups.isEmpty()) {
                    String selectedGroup = groups.get(random.nextInt(groups.size()));
                    JOptionPane.showMessageDialog(randomRollCallView, "Selected Group: " + selectedGroup);
                } else {
                    JOptionPane.showMessageDialog(randomRollCallView, "No groups available");
                }
                */
            // 选择学生
            } else if (isStudentSelected) {

                if (!students.isEmpty()) {
                    // 集合容器中随机选择
                    Student selectedStudent = students.get(random.nextInt(students.size()));
                    // 并弹出对话框告知结果
                    JOptionPane.showMessageDialog(randomRollCallView, "Selected Student: " + selectedStudent.getName() + selectedStudent.getStudentId());
                // 空提示
                } else {
                    JOptionPane.showMessageDialog(randomRollCallView, "No students available");
                }
            }
        }
    }
}

