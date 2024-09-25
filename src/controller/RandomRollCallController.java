package controller;

import java.io.IOException;
import model.Student;
import model.StudentManager;
import view.RandomRollCallView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class RandomRollCallController {
    private RandomRollCallView randomRollCallView;
    private StudentManager studentManager;

    public RandomRollCallController(RandomRollCallView randomRollCallView) {
        this.randomRollCallView = randomRollCallView;
        this.studentManager = new StudentManager();
        this.randomRollCallView.getStartButton().addActionListener(new StartRollCallListener());
    }

    class StartRollCallListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedClass = randomRollCallView.getSelectedClass();
            boolean isGroupSelected = randomRollCallView.isGroupSelected();
            boolean isStudentSelected = randomRollCallView.isStudentSelected();

            List<Student> students = studentManager.getStudents();
            Random random = new Random();

            if (isGroupSelected) {
                // Implement group selection logic
                /*
                List<String> groups = studentManager.getGroups(selectedClass); // Assuming this method exists
                if (!groups.isEmpty()) {
                    String selectedGroup = groups.get(random.nextInt(groups.size()));
                    JOptionPane.showMessageDialog(randomRollCallView, "Selected Group: " + selectedGroup);
                } else {
                    JOptionPane.showMessageDialog(randomRollCallView, "No groups available");
                }
                */
            } else if (isStudentSelected) {
                // Randomly select a student
                if (!students.isEmpty()) {
                    Student selectedStudent = students.get(random.nextInt(students.size()));
                    JOptionPane.showMessageDialog(randomRollCallView, "Selected Student: " + selectedStudent.getName());
                } else {
                    JOptionPane.showMessageDialog(randomRollCallView, "No students available");
                }
            }
        }
    }
}

