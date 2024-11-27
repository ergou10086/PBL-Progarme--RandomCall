package dao;

import java.util.List;
import model.Student;

public interface RandomRollCallDao {
    List<Student> getAllStudents();
    List<Student> getStudentsByClasses(String[] classes);
    List<Student> getStudentsByGroup(String group);
    void updateStudent(Student student);
    Student findStudent(String idOrName);
    void saveStudents(List<Student> students);
    List<String> getAllClassNames();
}
