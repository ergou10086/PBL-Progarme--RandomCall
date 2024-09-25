package model;

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

    // 新删
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

    /*
    // 删
    public void removeStudent(Student student) {
        students.remove(student);
        saveStudents();
    }
    */


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
