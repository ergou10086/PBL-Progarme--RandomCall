package service.impl;

import dao.StudentDao;
import dao.impl.StudentDaoImpl;
import model.Student;
import service.StudentService;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;

    public StudentServiceImpl() {
        this.studentDao = new StudentDaoImpl();
    }

    @Override
    public void addStudent(Student student) {
        studentDao.addStudent(student);
    }

    @Override
    public void removeStudent(int index) {
        studentDao.removeStudent(index);
    }

    @Override
    public void updateStudent(Student student) {
        studentDao.updateStudent(student);
    }

    @Override
    public Student findStudent(String idOrName) {
        Student student = studentDao.findStudentById(idOrName);
        if (student == null) {
            student = studentDao.findStudentByName(idOrName);
        }
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.loadStudents();
    }

    @Override
    public List<Student> getStudentsByClass(String className) {
        return studentDao.findStudentsByClass(className);
    }

    @Override
    public List<Student> getStudentsByGroup(String group) {
        return studentDao.findStudentsByGroup(group);
    }

    @Override
    public List<String> getAllClassNames() {
        return studentDao.getAllClassNames();
    }

    @Override
    public void saveAllStudents(List<Student> students) {
        studentDao.saveStudents(students);
    }

    @Override
    public boolean isValidScore(String score) {
        if (score == null || score.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(score);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
} 