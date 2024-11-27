package model;

import service.StudentManagerService;
import service.impl.StudentManagerServiceImpl;
import java.util.List;

import javax.swing.*;

public class StudentManager {
    private final StudentManagerService studentManagerService;

    public StudentManager() {
        this.studentManagerService = new StudentManagerServiceImpl();
    }

    public void addStudent(Student student) {
        studentManagerService.addStudent(student);
    }

    public void removeStudentAtIndex(int index) {
        studentManagerService.removeStudentAtIndex(index);
    }

    public void removeLastStudent() {
        studentManagerService.removeLastStudent();
    }

    public Student checkStudent(String idOrName) {
        return studentManagerService.checkStudent(idOrName);
    }

    public List<Student> getStudents() {
        return studentManagerService.getStudents();
    }

    public List<Student> getByStudentsInGroup(String group) {
        return studentManagerService.getByStudentsInGroup(group);
    }

    public List<String> getAllClassNames() {
        return studentManagerService.getAllClassNames();
    }

    public void saveStudents() {
        studentManagerService.saveStudents();
    }

    public void setStudents(List<Student> students) {
        studentManagerService.setStudents(students);
    }
}

