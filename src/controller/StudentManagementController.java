package controller;

import exceptions.StudentManageExceptions;
import model.Student;
import model.StudentManager;
import view.StudentManagementView;
import view.RandomRollCallView;

import javax.swing.*;
import java.io.*;
import java.util.*;
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

        // 为读取和导出选项添加事件监听
        this.studentManagementView.getReadItem().addActionListener(new ReadStudentsListener(studentManager));
        this.studentManagementView.getExportItem().addActionListener(new ExportStudentsListener(studentManager));

        // 更新显示内容
        updateDisplay();
    }

    // 读取学生信息的监听器类
    class ReadStudentsListener implements ActionListener {
        // 用于管理学生信息的对象引用，通过构造函数传入，以便在该监听器中操作学生信息
        private StudentManager studentManager;
        // 接收一个StudentManager实例
        public ReadStudentsListener(StudentManager studentManager) {
            this.studentManager = studentManager;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // 创建一个JFileChooser对象，用于让用户选择要读取的文件
            JFileChooser fileChooser = new JFileChooser();
            // 显示文件选择对话框，并等待用户操作，null用于阻塞程序
            int returnValue = fileChooser.showOpenDialog(null);
            // 在文件选择对话框中点击了“打开”按钮才继续
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // 获取用户选择的文件对象
                File selectedFile = fileChooser.getSelectedFile();
                // 判断所选文件的文件名是否以".dat"结尾
                if (selectedFile.getName().toLowerCase().endsWith(".dat")) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile))) {
                        // 从文件中读取学生信息列表
                        List<Student> studentsFromFile = (List<Student>) ois.readObject();
                        // 将读取到的学生信息设置到学生管理器中
                        studentManager.setStudents(studentsFromFile);
                        JOptionPane.showMessageDialog(studentManagementView, "学生信息已经成功被读取" + selectedFile.getName());
                    } catch (IOException | ClassNotFoundException ex) {
                        // 只弹出错误提示框给用户，不打印堆栈跟踪信息
                        JOptionPane.showMessageDialog(null, "读取学生信息失败：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请选择后缀为.dat的文件");
                }
            }
        }
    }

    // 保存学生信息的监听器类
    class ExportStudentsListener implements ActionListener {
        // 用于管理学生信息的对象引用，通过构造函数传入
        private StudentManager studentManager;
        // 构造函数，接收一个StudentManager实例
        public ExportStudentsListener(StudentManager studentManager) {
            this.studentManager = studentManager;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // 创建一个JFileChooser对象，用于让用户选择要保存文件的位置
            JFileChooser saveFileChooser = new JFileChooser();
            // 显示文件保存对话框，并等待用户操作，并进行阻塞
            int returnValue = saveFileChooser.showSaveDialog(null);
            // 用户在文件保存对话框中点击了“保存”按钮，才继续执行
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // 获取用户选择的要保存文件的对象
                File fileToSave = saveFileChooser.getSelectedFile();
                // 判断所选文件的文件名是否不以".dat"结尾，如果不是则添加".dat"后缀
                if (!fileToSave.getName().toLowerCase().endsWith(".dat")) {
                    fileToSave = new File(fileToSave.getAbsolutePath() + ".dat");
                }
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                    // 获取当前学生管理器中的学生信息列表
                    List<Student> studentsToSave = studentManager.getStudents();
                    // 将学生信息列表写入到选定的文件中
                    oos.writeObject(studentsToSave);
                    JOptionPane.showMessageDialog(null, "学生信息已成功保存到 " + fileToSave.getAbsolutePath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "保存学生信息失败：" + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
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
            } catch (StudentManageExceptions.InvalidNumberOfStudentException ex) {
                // 输入不是有效数字时，弹出提示框提示用户
                JOptionPane.showMessageDialog(studentManagementView, "你好好看看你输入了个什么玩意", "瞬间爆炸", JOptionPane.ERROR_MESSAGE);
                throw new StudentManageExceptions.InvalidNumberOfStudentException(ex.getMessage(), ex);
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
            } catch (StudentManageExceptions.InvalidNumberOfStudentException ex) {
                JOptionPane.showMessageDialog(studentManagementView, "你好好看看你输入了个什么玩意", "瞬间爆炸", JOptionPane.ERROR_MESSAGE);
                throw new StudentManageExceptions.InvalidNumberOfStudentException(ex.getMessage(), ex);
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
