package dao.impl;

import dao.RandomRollCallDao;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import model.Student;

public class RandomRollCallDaoImpl implements RandomRollCallDao {
    private static final String FILE_PATH = "students.dat";
    private List<Student> students;

    public RandomRollCallDaoImpl() {
        this.students = new ArrayList<>();
        loadStudents();
    }

    private void loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            students = (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            students = new ArrayList<>();
        }
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    @Override
    public List<Student> getStudentsByClasses(String[] classes) {
        List<String> classList = Arrays.asList(classes);
        return students.stream()
                .filter(student -> classList.contains(student.getClassName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> getStudentsByGroup(String group) {
        return students.stream()
                .filter(student -> student.getGroup().equals(group))
                .collect(Collectors.toList());
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
    public Student findStudent(String idOrName) {
        return students.stream()
                .filter(s -> s.getStudentId().equals(idOrName) || s.getName().equals(idOrName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void saveStudents(List<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getAllClassNames() {
        return students.stream()
                .map(Student::getClassName)
                .distinct()
                .collect(Collectors.toList());
    }
}
