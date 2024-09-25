package model;

import java.util.ArrayList;
import java.util.List;

// 学生列表管理类，维护学生数据
public class StudentManager {
    // 用来存储Student对象的集合，增删改
    private List<Student> students;

    // 构造方法,声明students这个集合
    public StudentManager() {
        this.students = new ArrayList<>();
    }

    // 增
    public void addStudent(Student student) {
        students.add(student);
    }

    // 删
    public void removeStudent(Student student) {
        students.remove(student);
    }

    // getter
    public List<Student> getStudents() {
        return students;
    }
}
