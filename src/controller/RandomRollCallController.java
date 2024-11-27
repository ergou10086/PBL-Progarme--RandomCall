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
        this.randomRollCallView.getExportButton().addActionListener(new ExportScoreListener());
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

                // 创建主面板，使用 BorderLayout
                JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
                mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // 创建标题面板
                JPanel titlePanel = new JPanel();
                JLabel titleLabel = new JLabel("小组: " + selectedGroup);
                titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
                titlePanel.add(titleLabel);
                mainPanel.add(titlePanel, BorderLayout.NORTH);

                // 创建学生信息面板
                JPanel studentsPanel = new JPanel();
                studentsPanel.setLayout(new BoxLayout(studentsPanel, BoxLayout.Y_AXIS));
                
                for (int i = 0; i < 2 && i < groupStudents.size(); i++) {
                    Student student = groupStudents.get(i);
                    messageBuilder.append("Student: ").append(student.getClassName())
                            .append(":").append(student.getName())
                            .append(" (ID: ").append(student.getStudentId()).append(")\n");

                    // 创建单个学生面板
                    JPanel studentPanel = new JPanel();
                    studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
                    studentPanel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
                        "学生信息"));

                    // 添加学生照片
                    if (student.getStudentImageIcon() != null) {
                        ImageIcon studentImageIcon = student.getStudentImageIcon();
                        int imageWidth = studentImageIcon.getIconWidth();
                        int imageHeight = studentImageIcon.getIconHeight();
                        if (imageWidth > 300 || imageHeight > 450) {
                            Image image = studentImageIcon.getImage();
                            Image scaledImage = image.getScaledInstance(300, 450, Image.SCALE_SMOOTH);
                            studentImageIcon = new ImageIcon(scaledImage);
                        }
                        JLabel imageLabel = new JLabel(studentImageIcon);
                        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        studentPanel.add(imageLabel);
                        studentPanel.add(Box.createVerticalStrut(10));
                    }

                    // 学生基本信息面板
                    JPanel infoPanel = new JPanel();
                    infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                    JLabel infoLabel = new JLabel(student.getClassName() + " - " + 
                            student.getName() + " (学号: " + student.getStudentId() + ")");
                    infoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                    infoPanel.add(infoLabel);
                    studentPanel.add(infoPanel);
                    studentPanel.add(Box.createVerticalStrut(5));

                    // 成绩编辑面板
                    JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    scorePanel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
                        "成绩信息"));
                    JLabel scoreLabel = new JLabel("成绩: ");
                    scoreLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                    JTextField scoreField = new JTextField(student.getScore(), 10);
                    scoreField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                    JButton updateButton = new JButton("更新成绩");
                    updateButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                    updateButton.setBackground(new Color(100, 180, 100));
                    updateButton.setForeground(Color.WHITE);
                    updateButton.setFocusPainted(false);

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

                    scorePanel.add(scoreLabel);
                    scorePanel.add(scoreField);
                    scorePanel.add(updateButton);
                    studentPanel.add(scorePanel);

                    studentsPanel.add(studentPanel);
                    studentsPanel.add(Box.createVerticalStrut(10));
                }

                mainPanel.add(studentsPanel, BorderLayout.CENTER);
                randomRollCallView.appendResult(messageBuilder.toString());

                // 创建对话框
                JDialog dialog = new JDialog(randomRollCallView, "随机点名结果", true);
                dialog.setLayout(new BorderLayout());
                
                // 添加滚动面板
                JScrollPane scrollPane = new JScrollPane(mainPanel);
                scrollPane.setPreferredSize(new Dimension(400, 600));
                dialog.add(scrollPane, BorderLayout.CENTER);

                dialog.pack();
                dialog.setLocationRelativeTo(randomRollCallView);
                dialog.setVisible(true);
            } else {
                randomRollCallView.appendResult("该小组没有学生: " + selectedGroup);
            }
        }

        private void handleStudentSelection(Random random, List<Student> selectedStudents) {
            if (!selectedStudents.isEmpty()) {
                Student selectedStudent = selectedStudents.get(random.nextInt(selectedStudents.size()));
                String result = "Selected Student: " + selectedStudent.getClassName() + ", " +
                        selectedStudent.getName() + " (" + selectedStudent.getStudentId() + ")";
                randomRollCallView.appendResult(result);

                // 创建主面板，使用 BorderLayout
                JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
                mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // 创建学生信息面板
                JPanel studentPanel = new JPanel();
                studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
                studentPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
                    "学生信息"));

                // 添加学生照片
                if (selectedStudent.getStudentImageIcon() != null) {
                    ImageIcon studentImageIcon = selectedStudent.getStudentImageIcon();
                    int imageWidth = studentImageIcon.getIconWidth();
                    int imageHeight = studentImageIcon.getIconHeight();
                    if (imageWidth > 300 || imageHeight > 450) {
                        Image image = studentImageIcon.getImage();
                        Image scaledImage = image.getScaledInstance(300, 450, Image.SCALE_SMOOTH);
                        studentImageIcon = new ImageIcon(scaledImage);
                    }
                    JLabel imageLabel = new JLabel(studentImageIcon);
                    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    studentPanel.add(imageLabel);
                    studentPanel.add(Box.createVerticalStrut(10));
                }

                // 学生基本信息面板
                JPanel infoPanel = new JPanel();
                infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                JLabel infoLabel = new JLabel(selectedStudent.getClassName() + " - " + 
                        selectedStudent.getName() + " (学号: " + selectedStudent.getStudentId() + ")");
                infoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                infoPanel.add(infoLabel);
                studentPanel.add(infoPanel);
                studentPanel.add(Box.createVerticalStrut(10));

                // 成绩编辑面板
                JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                scorePanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
                    "成绩信息"));
                JLabel scoreLabel = new JLabel("成绩: ");
                scoreLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                JTextField scoreField = new JTextField(selectedStudent.getScore(), 10);
                scoreField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                JButton updateButton = new JButton("更新成绩");
                updateButton.setBackground(new Color(100, 180, 100));
                updateButton.setForeground(Color.WHITE);
                updateButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                updateButton.setFocusPainted(false);

                scorePanel.add(scoreLabel);
                scorePanel.add(scoreField);
                scorePanel.add(updateButton);
                studentPanel.add(scorePanel);

                mainPanel.add(studentPanel, BorderLayout.CENTER);

                // 创建对话框
                JDialog dialog = new JDialog(randomRollCallView, "随机点名结果", true);
                dialog.setLayout(new BorderLayout());
                
                // 添加滚动面板
                JScrollPane scrollPane = new JScrollPane(mainPanel);
                scrollPane.setPreferredSize(new Dimension(400, 600));
                dialog.add(scrollPane, BorderLayout.CENTER);

                // 更新按钮事件
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
                randomRollCallView.appendResult("没有可用的学生");
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

    class ExportScoreListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String desktopPath = System.getProperty("user.home") + "/Desktop";
                String fileName = "学生成绩单_" + 
                    new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
                String fullPath = desktopPath + "/" + fileName;
                
                randomRollCallService.exportStudentScores(fullPath);
                
                JOptionPane.showMessageDialog(randomRollCallView,
                    "成绩单已成功导出到桌面！\n文件名：" + fileName,
                    "导出成功",
                    JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(randomRollCallView,
                    "导出失败：" + ex.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}