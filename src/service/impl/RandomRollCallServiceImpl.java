package service.impl;

import dao.RandomRollCallDao;
import dao.impl.RandomRollCallDaoImpl;
import java.util.List;
import java.util.stream.Collectors;
import model.Student;
import service.RandomRollCallService;

public class RandomRollCallServiceImpl implements RandomRollCallService {
    private final RandomRollCallDao randomRollCallDao;

    public RandomRollCallServiceImpl() {
        this.randomRollCallDao = new RandomRollCallDaoImpl();
    }

    @Override
    public List<Student> getFilteredStudents(String[] selectedClass) {
        if (selectedClass[0].equals("全局选项")) {
            return randomRollCallDao.getAllStudents();
        } else {
            return randomRollCallDao.getStudentsByClasses(selectedClass);
        }
    }

    @Override
    public List<String> getUniqueGroups(List<Student> selectedStudents) {
        return selectedStudents.stream()
                .map(Student::getGroup)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> getStudentsByGroup(String group) {
        return randomRollCallDao.getStudentsByGroup(group);
    }

    @Override
    public void updateStudentScore(Student student, String newScore) {
        if (isValidScore(newScore)) {
            student.setScore(newScore);
            List<Student> allStudents = randomRollCallDao.getAllStudents();
            for (int i = 0; i < allStudents.size(); i++) {
                if (allStudents.get(i).getStudentId().equals(student.getStudentId())) {
                    allStudents.set(i, student);
                    break;
                }
            }
            randomRollCallDao.saveStudents(allStudents);
        }
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

    @Override
    public Student findStudent(String idOrName) {
        return randomRollCallDao.findStudent(idOrName);
    }

    @Override
    public void saveStudents(List<Student> students) {
        randomRollCallDao.saveStudents(students);
    }

    @Override
    public List<String> getAllClassNames() {
        return randomRollCallDao.getAllClassNames();
    }
}
