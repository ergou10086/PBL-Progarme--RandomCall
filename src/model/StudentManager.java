package model;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// 学生列表管理类，维护学生数据
public class StudentManager {
    // 用来存储Student对象的集合
    private List<Student> students = new ArrayList<>();
    // IO方法，存储到students.dat
    private final String FILE_NAME = "students.dat";

    // 构造方法改为了每次读取学生列表，实现随时刷新
    public StudentManager() {
        loadStudents();
    }


    // 保存学生信息的方法，使用IO
    public void saveStudents() {
        try (ObjectOutputStream oosS = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            // 向输出流中写入学生信息
            oosS.writeObject(students);
            // 异常捕获
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取学生信息的方法，使用IO
    private void loadStudents() {
        try (ObjectInputStream oisS = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            // 使用输入流从List<Stduent>集合中读取学生的信息
            students = (List<Student>) oisS.readObject();
            // 异常捕获
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    // 获取所有不重复的班级名称
    public List<String> getAllClassNames() {
<<<<<<< HEAD
        // 创建一个存储班级的集合
=======
>>>>>>> 62e8d20de764bcbff0c65bae5dc96e5518c440ed
        List<String> classNames = new ArrayList<>();
        for (Student student : students) {
            if (!classNames.contains(student.getClassName())) {
                classNames.add(student.getClassName());
            }
        }
        return classNames;
    }


    // 根据小组获取学生列表
    public List<Student> getByStudentsInGroup(String group) {
<<<<<<< HEAD
        // 创建存储组内学生的列表，每次检索并存储
=======
>>>>>>> 62e8d20de764bcbff0c65bae5dc96e5518c440ed
        List<Student> groupStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getGroup().equals(group)) {
                groupStudents.add(student);
            }
        }
        return groupStudents;
    }
<<<<<<< HEAD

=======
    //获取学生的分数
    /*public List<String> getAllScore() {
        List<String> scores = new ArrayList<>();
        for (Student student : students) {
            if (!scores.contains(student.getScore())) {
                scores.add(student.getScore());
            }
        }
        return scores;
    }*/
>>>>>>> 62e8d20de764bcbff0c65bae5dc96e5518c440ed

    // 之前的普通删除，每次删除最后一个
    public void removeLastStudent() {
        if (!students.isEmpty()) {
            students.remove(students.size() - 1);
            saveStudents();
        }
    }

    // 增
    public void addStudent(Student student) {
        students.add(student);
        saveStudents();
    }

    // 更好的删除，根据索引，从1开始
    public void removeStudentAtIndex(int index) {
        if (index < 0 || index >= students.size()) {
            throw new IndexOutOfBoundsException("输入的数据不合理");
        }
        if(!students.isEmpty()) {
            students.remove(index);
            saveStudents();
        }
    }

<<<<<<< HEAD

    // 查
    // 使用学号查
    public Student checkStudentByID(String studentId) {
        Student foundStudent = null;

        // 根据学号查找学生
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                foundStudent = student;
                break;
            }
        }

        // 如果没有找到学生，返回 null
        return foundStudent;
    }
    // 使用姓名查
    public Student checkStudentByName(String studentName) {
        Student foundStudent = null;

        // 根据学号查找学生
        for (Student student : students) {
            if (student.getName().equals(studentName)) {
                foundStudent = student;
                break;
            }
        }

        // 如果没有找到学生，返回 null
        return foundStudent;
    }


=======
>>>>>>> 62e8d20de764bcbff0c65bae5dc96e5518c440ed
    // 改
    public void editStudentAtIndex(int index) {
        if (index < 0 || index >= students.size()) {
            throw new IndexOutOfBoundsException("输入的数据不合理");
        }

        Student student = students.get(index);

        // 使用对话框获取新信息
        String newClassName = JOptionPane.showInputDialog("请输入新的班级名:", student.getClassName());
        String newName = JOptionPane.showInputDialog("请输入新的姓名:", student.getName());
        String newGroup = JOptionPane.showInputDialog("请输入新的小组:", student.getGroup());
        String newStudentId = JOptionPane.showInputDialog("请输入新的学生ID:", student.getStudentId());
<<<<<<< HEAD
        String newStudentScore = JOptionPane.showInputDialog("请输入新的学生成绩：",student.getScore());

        if (isValidScore(newStudentScore)) {
            // 替换非空输入
            if (newClassName != null) student.setClassName(newClassName);
            if (newName != null) student.setName(newName);
            if (newGroup != null) student.setGroup(newGroup);
            if (newStudentId != null) student.setStudentId(newStudentId);
            student.setScore(newStudentScore);
        } else {
            JOptionPane.showMessageDialog(null, "输入的成绩无效，请输入数字！", "错误", JOptionPane.ERROR_MESSAGE);
        }

        saveStudents();
    }

    // 检查成绩是否为有效数字
    private boolean isValidScore(String score) {
        if (score == null || score.trim().isEmpty()) {
            return false; // 空输入无效
        }
        try {
            Double.parseDouble(score); // 使用包装类，转换为数字
            return true;
        } catch (NumberFormatException e) {
            return false; // 捕获格式错误
        }
    }
=======

        // 不空则替换
        if (newClassName != null && newName != null && newGroup != null && newStudentId != null) {
            student.setClassName(newClassName);
            student.setName(newName);
            student.setGroup(newGroup);
            student.setStudentId(newStudentId);
        }
        // 保存更新
        saveStudents();
    }
>>>>>>> 62e8d20de764bcbff0c65bae5dc96e5518c440ed


    // getter每次传递加载好的内容，就不用刷新了
    public List<Student> getStudents() {
        loadStudents();
        return students;
    }

    // setter返回时候要保存学生
    public void setStudents(List<Student> students) {
        this.students = students;
        saveStudents();
    }
}
