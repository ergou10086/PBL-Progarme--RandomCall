package dao.impl;

import dao.StudentDao;
import model.Student;
import java.io.*;
import java.util.*;

public class StudentDaoImpl implements StudentDao {
    private static final String FILE_NAME = "students.dat";
    private List<Student> students;

    public StudentDaoImpl() {
        this.students = new ArrayList<>();
        loadStudents();
    }

    @Override
    public void saveStudents(List<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void addStudent(Student student) {
        students.add(student);
        saveStudents(students);
    }

    @Override
    public void removeStudent(int index) {
        if (index >= 0 && index < students.size()) {
            students.remove(index);
            saveStudents(students);
        }
    }

    @Override
    public void updateStudent(Student student) {
        int index = -1;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(student.getStudentId())) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            students.set(index, student);
            saveStudents(students);
        }
    }

    @Override
    public Student findStudentById(String studentId) {
        return students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Student findStudentByName(String name) {
        return students.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Student> findStudentsByClass(String className) {
        return students.stream()
                .filter(s -> s.getClassName().equals(className))
                .toList();
    }

    @Override
    public List<Student> findStudentsByGroup(String group) {
        return students.stream()
                .filter(s -> s.getGroup().equals(group))
                .toList();
    }

    @Override
    public List<String> getAllClassNames() {
        return students.stream()
                .map(Student::getClassName)
                .distinct()
                .toList();
    }
} 