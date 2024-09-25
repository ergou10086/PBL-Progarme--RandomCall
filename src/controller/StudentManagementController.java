package controller;

import model.Student;
import model.StudentManager;
import view.StudentManagementView;

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
            // 创建学生对象
            Student student = new Student(className, name, group, studentId);
            // 添加到Student.dat
            studentManager.addStudent(student);
            // 更新显示
            updateDisplay();
        }
    }

    /*
    class RemoveStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String className = studentManagementView.getClassName();
            String name = studentManagementView.getName();
            String group = studentManagementView.getGroup();
            String studentId = studentManagementView.getStudentId();
            Student student = new Student(className, name, group, studentId);
            studentManager.removeStudent(student);
            updateDisplay();
        }
    }
    */

    // 内部类，用于移除学生，事件监听器执行StudentManager中的removeLastStudent方法，每次删除最后一个学生
    class RemoveStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            studentManager.removeLastStudent();
            // 更新
            updateDisplay();
        }
    }


    // // 更新学生显示内容的方法
    private void updateDisplay() {
        StringBuilder sbs = new StringBuilder();
        for (Student student : studentManager.getStudents()) {
            // 遍历学生列表并拼接信息
            sbs.append(student.getClassName()).append(", ")
                    .append(student.getName()).append(", ")
                    .append(student.getGroup()).append(", ")
                    .append(student.getStudentId()).append("\n");
        }
        // 将拼接好的字符串显示在视图中
        studentManagementView.displayStudents(sbs.toString());
    }
}
