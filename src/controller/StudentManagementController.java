package controller;

import model.Student;
import model.StudentManager;
import view.StudentManagementView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentManagementController {
    private StudentManagementView studentManagementView;
    private StudentManager studentManager;

    public StudentManagementController(StudentManagementView studentManagementView) {
        this.studentManagementView = studentManagementView;
        this.studentManager = new StudentManager();
        this.studentManagementView.getAddButton().addActionListener(new AddStudentListener());
        this.studentManagementView.getRemoveButton().addActionListener(new RemoveStudentListener());
        updateDisplay();
    }

    class AddStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String className = studentManagementView.getClassName();
            String name = studentManagementView.getName();
            String group = studentManagementView.getGroup();
            String studentId = studentManagementView.getStudentId();
            Student student = new Student(className, name, group, studentId);
            studentManager.addStudent(student);
            updateDisplay();
        }
    }

    class RemoveStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String className = studentManagementView.getClassName();
            String name = studentManagementView.getName();
            String group = studentManagementView.getGroup();
            String studentId = studentManagementView.getStudentId();
            Student student = new Student(className, name, group, studentId);
            studentManager.removeStudent(student);
            updateDisplay();
        }
    }

    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();
        for (Student student : studentManager.getStudents()) {
            sb.append(student.getClassName()).append(", ")
                    .append(student.getName()).append(", ")
                    .append(student.getGroup()).append(", ")
                    .append(student.getStudentId()).append("\\n");
        }
        studentManagementView.displayStudents(sb.toString());
    }
}
