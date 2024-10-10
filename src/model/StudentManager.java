package model;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// 学生列表管理类，维护学生数据
public class StudentManager {
    // 用来存储Student对象的集合
    private List<Student> students = new ArrayList<>();
    // IO方法
    private final String FILE_NAME = "students.dat";

    // 构造方法改为了每次读取学生列表
    public StudentManager() {
        loadStudents();
    }


    // 保存学生信息的方法，使用IO
    private void saveStudents() {
        try (ObjectOutputStream oosS = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            // 向输出流中写入学生信息
            oosS.writeObject(students);
            // 异常捕获
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取学生信息的方法，使用IO
    private void loadStudents() {
        try (ObjectInputStream oisS = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            // 使用输入流从List<Stduent>集合中读取学生的信息
            students = (List<Student>) oisS.readObject();
            // 异常捕获
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    // 获取所有不重复的班级名称
    public List<String> getAllClassNames() {
        List<String> classNames = new ArrayList<>();
        for (Student student : students) {
            if (!classNames.contains(student.getClassName())) {
                classNames.add(student.getClassName());
            }
        }
        return classNames;
    }


    // 根据小组获取学生列表
    public List<Student> getByStudentsInGroup(String group) {
        List<Student> groupStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getGroup().equals(group)) {
                groupStudents.add(student);
            }
        }
        return groupStudents;
    }


    // 之前的普通删除，每次删除最后一个
    public void removeLastStudent() {
        if (!students.isEmpty()) {
            students.remove(students.size() - 1);
            saveStudents();
        }
    }

    // 增
    public void addStudent(Student student) {
        students.add(student);
        saveStudents();
    }

    // 更好的删除，根据索引，从1开始
    public void removeStudentAtIndex(int index) {
        if (index < 0 || index >= students.size()) {
            throw new IndexOutOfBoundsException("输入的数据不合理");
        }
        if(!students.isEmpty()) {
            students.remove(index);
            saveStudents();
        }
    }

    // 改
    public void editStudentAtIndex(int index) {
        if (index < 0 || index >= students.size()) {
            throw new IndexOutOfBoundsException("输入的数据不合理");
        }

        Student student = students.get(index);

        // 使用对话框获取新信息
        String newClassName = JOptionPane.showInputDialog("请输入新的班级名:", student.getClassName());
        String newName = JOptionPane.showInputDialog("请输入新的姓名:", student.getName());
        String newGroup = JOptionPane.showInputDialog("请输入新的小组:", student.getGroup());
        String newStudentId = JOptionPane.showInputDialog("请输入新的学生ID:", student.getStudentId());

        // 不空则替换
        if (newClassName != null && newName != null && newGroup != null && newStudentId != null) {
            student.setClassName(newClassName);
            student.setName(newName);
            student.setGroup(newGroup);
            student.setStudentId(newStudentId);
        }
        // 保存更新
        saveStudents();
    }


    // getter每次传递加载好的内容，就不用刷新了
    public List<Student> getStudents() {
        loadStudents();
        return students;
    }

    // setter返回时候要保存学生
    public void setStudents(List<Student> students) {
        this.students = students;
        saveStudents();
    }
}
