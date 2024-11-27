package controller;

import exceptions.RandomExceptions;
import model.Student;
import service.RandomRollCallService;
import service.impl.RandomRollCallServiceImpl;
import view.RandomRollCallView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class RandomRollCallController {
    private RandomRollCallView randomRollCallView;
    private RandomRollCallService randomRollCallService;

    public RandomRollCallController(RandomRollCallView randomRollCallView) {
        this.randomRollCallView = randomRollCallView;
        this.randomRollCallService = new RandomRollCallServiceImpl();

        // 获取班级名称并添加到视图
        List<String> classNames = randomRollCallService.getAllClassNames();
        for (String className : classNames) {
            randomRollCallView.addClass(className);
        }

        // 添加按钮监听器
        this.randomRollCallView.getStartButton().addActionListener(new StartRollCallListener());
        this.randomRollCallView.getEditScoreButton().addActionListener(new EditScoreListener());
        this.randomRollCallView.getCheckStudentScoreButton().addActionListener(new CheckStudentScoreListener());
    }

    class StartRollCallListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isGroupSelected = randomRollCallView.isGroupSelected();
            boolean isStudentSelected = randomRollCallView.isStudentSelected();

            String[] selectedClass = randomRollCallView.getSelectedClass();
            if (selectedClass.length == 0) {
                try {
                    throw new RandomExceptions("至少要选一个班级吧");
                } catch (RandomExceptions ex) {
                    JOptionPane.showMessageDialog(randomRollCallView, ex.getMessage());
                    return;
                }
            }

            List<Student> selectedStudents = randomRollCallService.getFilteredStudents(selectedClass);
            Random random = new Random();

            if (isGroupSelected) {
                handleGroupSelection(random, selectedStudents);
            } else if (isStudentSelected) {
                handleStudentSelection(random, selectedStudents);
            }
        }

        private void handleGroupSelection(Random random, List<Student> selectedStudents) {
            List<String> groups = randomRollCallService.getUniqueGroups(selectedStudents);

            if (!groups.isEmpty()) {
                String selectedGroup = groups.get(random.nextInt(groups.size()));
                List<Student> groupStudents = randomRollCallService.getStudentsByGroup(selectedGroup);
                displayGroupStudents(selectedGroup, groupStudents);
            } else {
                randomRollCallView.appendResult("没有小组可选");
            }
        }

        private void displayGroupStudents(String selectedGroup, List<Student> groupStudents) {
            if (!groupStudents.isEmpty()) {
                StringBuilder messageBuilder = new StringBuilder();
                messageBuilder.append("Selected Group: ").append(selectedGroup).append("\n");

                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
                mainPanel.add(new JLabel("Group: " + selectedGroup));

                for (int i = 0; i < 2 && i < groupStudents.size(); i++) {
                    Student student = groupStudents.get(i);
                    messageBuilder.append("Student: ").append(student.getClassName()).append(":").append(student.getName())
                            .append(" (ID: ").append(student.getStudentId()).append(")\n");

                    JPanel studentPanel = new JPanel();
                    studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));

                    // 添加学生照片
                    ImageIcon studentImageIcon = student.getStudentImageIcon();
                    if (studentImageIcon != null) {
                        int imageWidth = studentImageIcon.getIconWidth();
                        int imageHeight = studentImageIcon.getIconHeight();
                        if (imageWidth > 300 || imageHeight > 450) {
                            Image image = studentImageIcon.getImage();
                            Image scaledImage = image.getScaledInstance(300, 450, Image.SCALE_SMOOTH);
                            studentImageIcon = new ImageIcon(scaledImage);
                        }
                        JLabel imageLabel = new JLabel(studentImageIcon);
                        studentPanel.add(imageLabel);
                    }

                    JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    infoPanel.add(new JLabel("Student: " + student.getClassName() + ", " + student.getName()));

                    JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    scorePanel.add(new JLabel("Score: "));
                    JTextField scoreField = new JTextField(student.getScore(), 10);
                    JButton updateButton = new JButton("Update Score");

                    final Student finalStudent = student;
                    updateButton.addActionListener(e -> {
                        String newScore = scoreField.getText();
                        if (randomRollCallService.isValidScore(newScore)) {
                            randomRollCallService.updateStudentScore(finalStudent, newScore);
                            JOptionPane.showMessageDialog(mainPanel, "分数更新成功！");
                        } else {
                            JOptionPane.showMessageDialog(mainPanel, "请输入有效的分数！", "错误", JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    scorePanel.add(scoreField);
                    scorePanel.add(updateButton);

                    studentPanel.add(infoPanel);
                    studentPanel.add(scorePanel);
                    mainPanel.add(studentPanel);
                }

                randomRollCallView.appendResult(messageBuilder.toString());

                JDialog dialog = new JDialog(randomRollCallView, "Random Call Result", true);
                dialog.setLayout(new BorderLayout());

                JScrollPane scrollPane = new JScrollPane(mainPanel);
                scrollPane.setPreferredSize(new Dimension(400, 300));
                dialog.add(scrollPane, BorderLayout.CENTER);

                dialog.pack();
                dialog.setLocationRelativeTo(randomRollCallView);
                dialog.setVisible(true);
            } else {
                randomRollCallView.appendResult("No students in group: " + selectedGroup);
            }
        }

        private void handleStudentSelection(Random random, List<Student> selectedStudents) {
            if (!selectedStudents.isEmpty()) {
                Student selectedStudent = selectedStudents.get(random.nextInt(selectedStudents.size()));
                String result = "Selected Student: " + selectedStudent.getClassName() + ", " +
                        selectedStudent.getName() + " (" + selectedStudent.getStudentId() + ")";
                randomRollCallView.appendResult(result);

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                ImageIcon studentImageIcon = selectedStudent.getStudentImageIcon();
                if (studentImageIcon != null) {
                    int imageWidth = studentImageIcon.getIconWidth();
                    int imageHeight = studentImageIcon.getIconHeight();
                    if (imageWidth > 300 || imageHeight > 450) {
                        Image image = studentImageIcon.getImage();
                        Image scaledImage = image.getScaledInstance(300, 450, Image.SCALE_SMOOTH);
                        studentImageIcon = new ImageIcon(scaledImage);
                    }
                    JLabel imageLabel = new JLabel(studentImageIcon);
                    panel.add(imageLabel);
                }

                JLabel infoLabel = new JLabel("Student: " + selectedStudent.getClassName() + ", " +
                        selectedStudent.getName() + " (ID: " + selectedStudent.getStudentId() + ")");
                panel.add(infoLabel);

                JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel scoreLabel = new JLabel("Score: ");
                JTextField scoreField = new JTextField(selectedStudent.getScore(), 10);
                JButton updateButton = new JButton("Update Score");

                scorePanel.add(scoreLabel);
                scorePanel.add(scoreField);
                scorePanel.add(updateButton);
                panel.add(scorePanel);

                JDialog dialog = new JDialog(randomRollCallView, "Random Call Result", true);
                dialog.setLayout(new BorderLayout());
                dialog.add(panel, BorderLayout.CENTER);

                updateButton.addActionListener(e -> {
                    String newScore = scoreField.getText();
                    if (randomRollCallService.isValidScore(newScore)) {
                        randomRollCallService.updateStudentScore(selectedStudent, newScore);
                        JOptionPane.showMessageDialog(dialog, "分数更新成功！");
                    } else {
                        JOptionPane.showMessageDialog(dialog, "请输入有效的分数！", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                });

                dialog.pack();
                dialog.setLocationRelativeTo(randomRollCallView);
                dialog.setVisible(true);
            } else {
                randomRollCallView.appendResult("No students available");
            }
        }
    }

    class EditScoreListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String studentMessage = JOptionPane.showInputDialog(randomRollCallView, "输入学生学号或学生姓名:");
            if (studentMessage != null) {
                Student student = randomRollCallService.findStudent(studentMessage);
                if (student != null) {
                    String newScore = JOptionPane.showInputDialog(randomRollCallView, "输入新成绩:");
                    if (newScore != null && randomRollCallService.isValidScore(newScore)) {
                        randomRollCallService.updateStudentScore(student, newScore);
                        JOptionPane.showMessageDialog(randomRollCallView, "成绩更新成功");
                    } else {
                        JOptionPane.showMessageDialog(randomRollCallView, "请输入有效的成绩");
                    }
                } else {
                    JOptionPane.showMessageDialog(randomRollCallView, "未找到该学生");
                }
            }
        }
    }

    class CheckStudentScoreListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Student> students = randomRollCallService.getFilteredStudents(new String[]{"全局选项"});
            displayScoreInfo(students);
        }

        private void displayScoreInfo(List<Student> students) {
            Map<String, Map<String, List<Student>>> classGroupMap = new HashMap<>();

            for (Student student : students) {
                String className = student.getClassName();
                String groupName = student.getGroup();

                classGroupMap.computeIfAbsent(className, k -> new HashMap<>())
                        .computeIfAbsent(groupName, k -> new ArrayList<>())
                        .add(student);
            }

            StringBuilder messageBuilder = new StringBuilder("学生们的成绩如下:\n\n");

            for (Map.Entry<String, Map<String, List<Student>>> classEntry : classGroupMap.entrySet()) {
                messageBuilder.append("班级: ").append(classEntry.getKey()).append("\n");

                for (Map.Entry<String, List<Student>> groupEntry : classEntry.getValue().entrySet()) {
                    messageBuilder.append("    小组: ").append(groupEntry.getKey()).append("\n");

                    for (Student student : groupEntry.getValue()) {
                        messageBuilder.append("        ")
                                .append(student.getName())
                                .append(" (ID: ")
                                .append(student.getStudentId())
                                .append("): ")
                                .append(student.getScore())
                                .append("\n");
                    }
                }
                messageBuilder.append("\n");
            }

            JTextArea textArea = new JTextArea(messageBuilder.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 500));

            JOptionPane.showMessageDialog(randomRollCallView, scrollPane, "学生成绩名单",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}