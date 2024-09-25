package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// 学生列表管理类，维护学生数据
public class StudentManager {
    // 用来存储Student对象的集合，增删
    private List<Student> students = new ArrayList<>();
    // IO方法
    private final String FILE_NAME = "students.dat";

    // 构造方法,声明students这个集合
    public StudentManager() {
        this.students = new ArrayList<>();
    }

    // 增
    public void addStudent(Student student) {
        students.add(student);
        saveStudents();
    }

    // 删
    public void removeStudent(Student student) {
        students.remove(student);
        saveStudents();
    }

    // getter
    public List<Student> getStudents() {
        loadStudents();
        return students;
    }

    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
