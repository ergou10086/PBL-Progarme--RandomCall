package controller;

import exceptions.StudentManageExceptions;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;
import javax.swing.*;
import model.Student;
import service.StudentManagerService;
import service.impl.StudentManagerServiceImpl;
import view.StudentManagementView;

public class StudentManagementController {
    private StudentManagementView studentManagementView;
    private StudentManagerService studentManagerService;

    public StudentManagementController(StudentManagementView studentManagementView) {
        this.studentManagementView = studentManagementView;
        this.studentManagerService = new StudentManagerServiceImpl();
        
        // 添加按钮监听器
        this.studentManagementView.getAddButton().addActionListener(new AddStudentListener());
        this.studentManagementView.getRemoveButton().addActionListener(new RemoveStudentListener());
        this.studentManagementView.getEditButton().addActionListener(new UpdateStudentListener());
        this.studentManagementView.getReadItem().addActionListener(new ReadStudentsListener());
        this.studentManagementView.getExportItem().addActionListener(new ExportStudentsListener());

        updateDisplay();
    }

    class ReadStudentsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.getName().toLowerCase().endsWith(".dat")) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile))) {
                        List<Student> studentsFromFile = (List<Student>) ois.readObject();
                        studentManagerService.setStudents(studentsFromFile);
                        JOptionPane.showMessageDialog(studentManagementView, "学生信息已经成功被读取" + selectedFile.getName());
                        updateDisplay();
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "读取学生信息失败：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请选择后缀为.dat的文件");
                }
            }
        }
    }

    class ExportStudentsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser saveFileChooser = new JFileChooser();
            int returnValue = saveFileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File fileToSave = saveFileChooser.getSelectedFile();
                if (!fileToSave.getName().toLowerCase().endsWith(".dat")) {
                    fileToSave = new File(fileToSave.getAbsolutePath() + ".dat");
                }
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                    List<Student> studentsToSave = studentManagerService.getStudents();
                    oos.writeObject(studentsToSave);
                    JOptionPane.showMessageDialog(null, "学生信息已成功保存到 " + fileToSave.getAbsolutePath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "保存学生信息失败：" + ex.getMessage());
                }
            }
        }
    }

    class AddStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String className = studentManagementView.getClassName();
            String name = studentManagementView.getName();
            String group = studentManagementView.getGroup();
            String studentId = studentManagementView.getStudentId();
            
            String photoPath = null;
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg") 
                        || f.getName().toLowerCase().endsWith(".png");
                }
                public String getDescription() {
                    return "Image files (*.jpg, *.png)";
                }
            });
            
            int result = fileChooser.showOpenDialog(studentManagementView);
            if (result == JFileChooser.APPROVE_OPTION) {
                photoPath = fileChooser.getSelectedFile().getAbsolutePath();
            }

            Student student = new Student(className, name, group, studentId, "100", photoPath);
            studentManagerService.addStudent(student);
            updateDisplay();
        }
    }

    public class RemoveStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog(studentManagementView, "请输入要删除的学生的索引（从1开始）：");
            try {
                int index = Integer.parseInt(input) - 1;
                studentManagerService.removeStudentAtIndex(index);
                updateDisplay();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(studentManagementView, "请输入有效的数字", "错误", JOptionPane.ERROR_MESSAGE);
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(studentManagementView, "索引超出范围", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class UpdateStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String editInput = JOptionPane.showInputDialog(studentManagementView, "请输入你要更改的学生索引(1开):");
            try {
                int index = Integer.parseInt(editInput) - 1;
                List<Student> students = studentManagerService.getStudents();
                if (index >= 0 && index < students.size()) {
                    Student student = students.get(index);
                    showEditDialog(student);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(studentManagementView, "请输入有效的索引数字！");
            }
        }

        private void showEditDialog(Student student) {
            JDialog editDialog = new JDialog(studentManagementView, "编辑学生信息", true);
            editDialog.setLayout(new GridLayout(7, 2));
            
            JTextField classField = new JTextField(student.getClassName());
            JTextField nameField = new JTextField(student.getName());
            JTextField groupField = new JTextField(student.getGroup());
            JTextField idField = new JTextField(student.getStudentId());
            JTextField scoreField = new JTextField(student.getScore());
            JLabel photoLabel = new JLabel(student.getPhotoPath() != null ? student.getPhotoPath() : "无照片");
            
            JButton selectPhotoButton = new JButton("选择照片");
            final String[] selectedPhotoPath = {student.getPhotoPath()};

            selectPhotoButton.addActionListener(evt -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg") 
                            || f.getName().toLowerCase().endsWith(".png");
                    }
                    public String getDescription() {
                        return "Image files (*.jpg, *.png)";
                    }
                });
                
                int result = fileChooser.showOpenDialog(editDialog);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedPhotoPath[0] = fileChooser.getSelectedFile().getAbsolutePath();
                    photoLabel.setText(selectedPhotoPath[0]);
                }
            });
            
            JButton confirmButton = new JButton("确认");
            confirmButton.addActionListener(evt -> {
                student.setClassName(classField.getText());
                student.setName(nameField.getText());
                student.setGroup(groupField.getText());
                student.setStudentId(idField.getText());
                student.setScore(scoreField.getText());
                student.setPhotoPath(selectedPhotoPath[0]);
                
                studentManagerService.saveStudents();
                updateDisplay();
                editDialog.dispose();
            });
            
            // 添加组件到对话框
            editDialog.add(new JLabel("班级:"));
            editDialog.add(classField);
            editDialog.add(new JLabel("姓名:"));
            editDialog.add(nameField);
            editDialog.add(new JLabel("小组:"));
            editDialog.add(groupField);
            editDialog.add(new JLabel("学号:"));
            editDialog.add(idField);
            editDialog.add(new JLabel("成绩:"));
            editDialog.add(scoreField);
            editDialog.add(new JLabel("照片:"));
            editDialog.add(photoLabel);
            editDialog.add(selectPhotoButton);
            editDialog.add(confirmButton);
            
            editDialog.pack();
            editDialog.setLocationRelativeTo(studentManagementView);
            editDialog.setVisible(true);
        }
    }

    private void updateDisplay() {
        StringBuilder sbs = new StringBuilder();
        for (Student student : studentManagerService.getStudents()) {
            sbs.append(student.getClassName()).append(", ")
               .append(student.getName()).append(", ")
               .append(student.getGroup()).append(", ")
               .append(student.getStudentId()).append(", ")
               .append(student.getScore()).append("\n");
        }
        studentManagementView.displayStudents(sbs.toString());
    }
}
