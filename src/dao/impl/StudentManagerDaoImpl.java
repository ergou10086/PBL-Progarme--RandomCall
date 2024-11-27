package dao.impl;

import dao.StudentManagerDao;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.Student;

public class StudentManagerDaoImpl implements StudentManagerDao {
    private static final String FILE_PATH = "students.dat";
    private List<Student> students;

    public StudentManagerDaoImpl() {
        this.students = new ArrayList<>();
        loadStudentsFromFile();
    }

    @Override
    public void addStudent(Student student) {
        students.add(student);
        saveStudents();
    }

    @Override
    public void removeStudentAtIndex(int index) {
        if (index >= 0 && index < students.size()) {
            students.remove(index);
            saveStudents();
        }
    }

    @Override
    public void removeLastStudent() {
        if (!students.isEmpty()) {
            students.remove(students.size() - 1);
            saveStudents();
        }
    }

    @Override
    public Student checkStudent(String idOrName) {
        return students.stream()
                .filter(s -> s.getStudentId().equals(idOrName) || s.getName().equals(idOrName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    @Override
    public List<Student> getByStudentsInGroup(String group) {
        return students.stream()
                .filter(s -> s.getGroup().equals(group))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllClassNames() {
        return students.stream()
                .map(Student::getClassName)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setStudents(List<Student> students) {
        this.students = new ArrayList<>(students);
        saveStudents();
    }

    private void loadStudentsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            students = (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            students = new ArrayList<>();
        }
    }
}
