package dao;

import model.Student;
import java.util.List;

public interface StudentDao {
    void saveStudents(List<Student> students);
    List<Student> loadStudents();
    void addStudent(Student student);
    void removeStudent(int index);
    void updateStudent(Student student);
    Student findStudentById(String studentId);
    Student findStudentByName(String name);
    List<Student> findStudentsByClass(String className);
    List<Student> findStudentsByGroup(String group);
    List<String> getAllClassNames();
} 