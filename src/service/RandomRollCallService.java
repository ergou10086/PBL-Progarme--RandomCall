package service;

import java.util.List;
import model.Student;

public interface RandomRollCallService {
    List<Student> getFilteredStudents(String[] selectedClass);
    List<String> getUniqueGroups(List<Student> selectedStudents);
    List<Student> getStudentsByGroup(String group);
    void updateStudentScore(Student student, String newScore);
    boolean isValidScore(String score);
    Student findStudent(String idOrName);
    void saveStudents(List<Student> students);
    List<String> getAllClassNames();
}
