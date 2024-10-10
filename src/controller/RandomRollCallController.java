package controller;

import model.Student;
import model.StudentManager;
import view.RandomRollCallView;

import javax.swing.*;
import java.awt.event.*;

import java.util.*;

// 随机点名控制器类，实现随机点名的逻辑
public class RandomRollCallController {

    private RandomRollCallView randomRollCallView; // 随机点名视图对象
    private StudentManager studentManager; // 学生管理器对象

    // 构造函数，初始化视图和学生管理器，并设置按钮事件监听
    public RandomRollCallController(){}
    public RandomRollCallController(RandomRollCallView randomRollCallView) {
        // 各种实例化
        this.randomRollCallView = randomRollCallView;
        this.studentManager = new StudentManager();
        this.randomRollCallView = randomRollCallView;
        this.studentManager = new StudentManager();

        // 获取班级名称并添加到视图
        List<String> classNames = studentManager.getAllClassNames();
        for (String className : classNames) {
            randomRollCallView.addClass(className);
        }

        // 点名按钮的事件监听器
        this.randomRollCallView.getStartButton().addActionListener(new StartRollCallListener());
    }

    // 处理开始点名的事件的内部类
    public class StartRollCallListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            // 获取选择的是小组随机还是学生随机
            boolean isGroupSelected = randomRollCallView.isGroupSelected();
            boolean isStudentSelected = randomRollCallView.isStudentSelected();

            // 获取选择的班级
            String[] selectedClass = randomRollCallView.getSelectedClass();
            if(selectedClass.length == 0){
                JOptionPane.showMessageDialog(randomRollCallView, "Please select at least one class.");
                return;
            }

            // 从学生管理器类获取学生列表
            List<Student> students = studentManager.getStudents();
            // 获取过滤后的学生列表
            List<Student> selectedStudents = new ArrayList<>();

            // 选择了全局选项则全部添加
            if(selectedClass[0].equals("All Classes")){
                selectedStudents.addAll(students);
            // 要不然根据选中的班级过滤学生
            }else {
                for (Student student : students) {
                    for (String className : selectedClass) {
                        if (student.getClassName().equals(className)) {
                            selectedStudents.add(student);
                        }
                    }
                }
            }
            // 实例化随机数生成器
            Random random = new Random();

            // 选择小组
            if (isGroupSelected) {
                // 这里获取所有的小组名，小组名是唯一的
                List<String> groups = new ArrayList<>();
                // 遍历所有学生，获取其所在的小组名
                for (Student s : selectedStudents) {   // 注意使用过滤后的列表
                    String group = s.getGroup();
                    // 如果列表中没有该小组名，则添加
                    if(!groups.contains(group)){
                        groups.add(group);
                    }
                }
                // 这里实现随机选择一个小组
                if (!groups.isEmpty()) {
                    // 随机选择一个小组
                    String selectedGroup = groups.get(random.nextInt(groups.size()));
                    // 获取该小组内的所有学生
                    List<Student> groupStudents = studentManager.getByStudentsInGroup(selectedGroup);
                    // 小组判空
                    if(!groupStudents.isEmpty()){
                        // 构建消息字符串，存储该小组内所有学生的信息
                        StringBuilder messageBuilder = new StringBuilder();
                        messageBuilder.append("Selected Group: ").append(selectedGroup).append("\n");

                        // 遍历小组内的所有学生，添加其信息到消息字符串中,规定一个组里面只显示两个人
                        for (int i = 0; i < 2 && i < groupStudents.size(); i++) {
                            Student student = groupStudents.get(i);
                            messageBuilder.append("Student: ").append(student.getName()).append(" (ID: ").append(student.getStudentId()).append(")\n");
                        }

                        // 将完整的消息添加到结果文本域中
                        randomRollCallView.appendResult(messageBuilder.toString());
                        // 显示对话框，展示选定的小组和所有学生信息
                        JOptionPane.showMessageDialog(randomRollCallView, messageBuilder.toString());
                    }else{
                        // 空提示
                        randomRollCallView.appendResult("No students in group: " + selectedGroup);
                    }
                }else{
                    // 空提示
                    randomRollCallView.appendResult("No groups available.");
                }
            } else if (isStudentSelected) {
                if (!selectedStudents.isEmpty()) {    // 注意使用过滤后的学生列表
                    // 集合容器实现随机选择
                    Student selectedStudent = selectedStudents.get(random.nextInt(selectedStudents.size()));  // 注意使用过滤后的学生列表
                    // 在结果文本域中显示选中的学生
                    randomRollCallView.appendResult("Selected Student: " + selectedStudent.getClassName() + "," + selectedStudent.getName() + " (" + selectedStudent.getStudentId() + ")");
                    // 提示框
                    JOptionPane.showMessageDialog(randomRollCallView, "Student selected: " + selectedStudent.getName() + " (" + selectedStudent.getStudentId() + ")");

                } else {
                    // 空提示
                    randomRollCallView.appendResult("No students available");
                }
            }
        }
    }
}

