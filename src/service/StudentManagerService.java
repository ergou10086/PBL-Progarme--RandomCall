package service;

import java.util.List;
import model.Student;

public interface StudentManagerService {
    void addStudent(Student student);
    void removeStudentAtIndex(int index);
    void removeLastStudent();
    Student checkStudent(String idOrName);
    List<Student> getStudents();
    List<Student> getByStudentsInGroup(String group);
    List<String> getAllClassNames();
    void saveStudents();
    void setStudents(List<Student> students);
}
