package controller;

import model.Student;
import model.StudentManager;
import view.StudentManagementView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 学生管理控制器类，实现增删学生的逻辑
public class StudentManagementController {
    // 声明视图和学生管理器的引用
    private StudentManagementView studentManagementView;   // 学生管理视图属性
    private StudentManager studentManager;   // 学生管理器属性

    // 构造函数，初始化视图和管理器，设置按钮的监听器
    public StudentManagementController(StudentManagementView studentManagementView) {
        this.studentManagementView = studentManagementView;
        this.studentManager = new StudentManager();   // 学生管理器实例
        // 添加学生按钮的事件监听器
        this.studentManagementView.getAddButton().addActionListener(new AddStudentListener());
        // 删除学生按钮的事件监听器
        this.studentManagementView.getRemoveButton().addActionListener(new RemoveStudentListener());
        // 更新学生按钮的事件监听器
        this.studentManagementView.getEditButton().addActionListener(new UpdateStudentListener());
        // 更新显示内容
        updateDisplay();
    }

    // 添加学生的监听器类
    class AddStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 从文本框中获取学生信息
            String className = studentManagementView.getClassName();
            String name = studentManagementView.getName();
            String group = studentManagementView.getGroup();
            String studentId = studentManagementView.getStudentId();
            //String score = studentManagementView.getStudentScore();
            // 创建学生对象
            Student student = new Student(className, name, group, studentId, "100");
            // 添加到Student.dat
            studentManager.addStudent(student);
            // 更新显示
            updateDisplay();
        }
    }


    // 内部类，用于移除学生，事件监听器执行StudentManager中的removeLastStudent方法，每次删除最后一个学生
    class RemoveStudentListenerF implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            studentManager.removeLastStudent();
            // 更新
            updateDisplay();
        }
    }


    // 根据索引删除学生，是更好的删除
    public class RemoveStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 带输入框的提示栏
            String input = JOptionPane.showInputDialog(studentManagementView, "请输入要删除的学生的索引（从1开始）：");
            try {
                //  包装用户输入的索引
                int index = Integer.parseInt(input);
                index -= 1;    // 保证从1开始
                // 从学生管理器中删除指定索引的学生
                studentManager.removeStudentAtIndex(index);
                // 更新显示
                updateDisplay();
            } catch (NumberFormatException ex) {
                // 输入不是有效数字时，弹出提示框提示用户
                JOptionPane.showMessageDialog(studentManagementView, "你好好看看你输入了个什么玩意", "瞬间爆炸", JOptionPane.ERROR_MESSAGE);
            } catch (IndexOutOfBoundsException ex) {
                // 输入的索引超出范围时，提示用户
                JOptionPane.showMessageDialog(studentManagementView, "我这里有这么号个人？", "瞬间爆炸", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    // 更改学生信息的监听器类
    public class UpdateStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String editInput = JOptionPane.showInputDialog(studentManagementView, "请输入你要更改的学生索引(1开):");
            try {
                int index = Integer.parseInt(editInput);
                index -= 1; // 转换为从0开始的索引
                studentManager.editStudentAtIndex(index);
                // 更新显示
                updateDisplay();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(studentManagementView, "你好好看看你输入了个什么玩意", "瞬间爆炸", JOptionPane.ERROR_MESSAGE);
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(studentManagementView, "我这里有这么号个人？", "瞬间爆炸", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    // 更新学生显示内容的方法
    private void updateDisplay() {
        StringBuilder sbs = new StringBuilder();
        for (Student student : studentManager.getStudents()) {
            // 遍历学生列表并拼接信息
            sbs.append(student.getClassName()).append(", ")
                    .append(student.getName()).append(", ")
                    .append(student.getGroup()).append(", ")
                    .append(student.getStudentId()).append(", ")
                    .append(student.getScore()).append("\n");
        }
        // 将拼接好的字符串显示在视图中
        studentManagementView.displayStudents(sbs.toString());
    }
}
