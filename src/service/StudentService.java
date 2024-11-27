package service;

import model.Student;
import java.util.List;

public interface StudentService {
    void addStudent(Student student);
    void removeStudent(int index);
    void updateStudent(Student student);
    Student findStudent(String idOrName);
    List<Student> getAllStudents();
    List<Student> getStudentsByClass(String className);
    List<Student> getStudentsByGroup(String group);
    List<String> getAllClassNames();
    void saveAllStudents(List<Student> students);
    boolean isValidScore(String score);
}