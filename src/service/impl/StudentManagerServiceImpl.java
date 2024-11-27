package service.impl;

import dao.StudentManagerDao;
import dao.impl.StudentManagerDaoImpl;
import java.util.List;
import model.Student;
import service.StudentManagerService;

public class StudentManagerServiceImpl implements StudentManagerService {
    private final StudentManagerDao studentManagerDao;

    public StudentManagerServiceImpl() {
        this.studentManagerDao = new StudentManagerDaoImpl();
    }

    @Override
    public void addStudent(Student student) {
        studentManagerDao.addStudent(student);
    }

    @Override
    public void removeStudentAtIndex(int index) {
        studentManagerDao.removeStudentAtIndex(index);
    }

    @Override
    public void removeLastStudent() {
        studentManagerDao.removeLastStudent();
    }

    @Override
    public Student checkStudent(String idOrName) {
        return studentManagerDao.checkStudent(idOrName);
    }

    @Override
    public List<Student> getStudents() {
        return studentManagerDao.getStudents();
    }

    @Override
    public List<Student> getByStudentsInGroup(String group) {
        return studentManagerDao.getByStudentsInGroup(group);
    }

    @Override
    public List<String> getAllClassNames() {
        return studentManagerDao.getAllClassNames();
    }

    @Override
    public void saveStudents() {
        studentManagerDao.saveStudents();
    }

    @Override
    public void setStudents(List<Student> students) {
        studentManagerDao.setStudents(students);
    }
}
