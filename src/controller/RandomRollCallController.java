package controller;

import model.Student;
import model.StudentManager;
import view.RandomRollCallView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.util.List;

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

        // 添加编辑成绩按钮的事件监听器
        this.randomRollCallView.getEditScoreButton().addActionListener(new EditScoreListener());

        // 添加查询成绩按钮的事件监听器
        this.randomRollCallView.getCheckStudentScoreButton().addActionListener(new CheckStudentScoreListener());

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
            if(selectedClass[0].equals("全局选项")){
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

    // 处理编辑成绩的事件的内部类
    public class EditScoreListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String studentId = JOptionPane.showInputDialog(randomRollCallView, "输入学生学号:");

            if(studentId != null ) {
                try {
                    // 获取学生列表
                    List<Student> students = studentManager.getStudents();
                    Student foundStudent = studentManager.checkStudentByID(studentId);
                    // 如果没有找到学生，提示用户
                    if (foundStudent == null) {
                        JOptionPane.showMessageDialog(randomRollCallView, "没有找到该学号的学生，请检查后重试");
                        return;
                    }

                    // 获取新输入的成绩
                    String newScoreStr = JOptionPane.showInputDialog(randomRollCallView, "来吧他有多少分呢");
                    if (newScoreStr != null && !newScoreStr.trim().isEmpty()) {
                        try {
                            // 成绩为数字，有效
                            if (isValidScore(newScoreStr)) {
                                foundStudent.setScore(newScoreStr); // 直接设置成绩
                                JOptionPane.showMessageDialog(randomRollCallView, "对的，是这样，成绩更新完成");
                                studentManager.saveStudents(); // 及时保存修改
                            } else {
                                JOptionPane.showMessageDialog(randomRollCallView, "成绩输入无效，请输入有效的成绩");
                            }
                        } catch (Exception ex) { // 捕获其他可能的异常
                            JOptionPane.showMessageDialog(randomRollCallView, "更新成绩时发生错误，请重试");
                        }
                    } else {
                        JOptionPane.showMessageDialog(randomRollCallView, "成绩输入不能为空，请输入有效的成绩");
                    }
                } catch (HeadlessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    // 处理查询学生成绩的事件的内部类
    public class CheckStudentScoreListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Student> students = studentManager.getStudents();
            StringBuilder messageBuilder = new StringBuilder("Students and their scores:\n");

            for(int i = 1; i <= students.size(); i++) {
                Student student = students.get(i-1);
                messageBuilder.append(i).append(": ").append(student.getName())
                        .append(" (ID: ").append(student.getStudentId())
                        .append("): ").append(student.getScore()).append("\n");
            }

            JTextArea textArea = new JTextArea(messageBuilder.toString());
            textArea.setEditable(false);
            JScrollPane ScoreScrollPane = new JScrollPane(textArea);
            ScoreScrollPane.setPreferredSize(new Dimension(400, 300)); // 设置滚动窗口大小

            JOptionPane.showMessageDialog(randomRollCallView, ScoreScrollPane, "Student Scores", JOptionPane.INFORMATION_MESSAGE);
        }
    }



    // 检查成绩是否为有效数字
    private boolean isValidScore(String score) {
        if (score == null || score.trim().isEmpty()) {
            return false; // 空输入无效
        }
        try {
            Double.parseDouble(score); // 使用包装类，转换为数字
            return true;
        } catch (NumberFormatException e) {
            return false; // 捕获格式错误
        }
    }
}

