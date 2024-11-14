package model;
import javax.swing.*;
import java.io.Serializable;

// 学生实体类, 处理学生基本信息
// 实现Serializable接口，使得Student对象可以保存到文件
public class Student implements Serializable{
    // 所有属性全设为String
    private String className;    // 班级
    private String name;         // 姓名
    private String group;        // 小组
    private String studentId;    // 学号
    private String score;        // 成绩，默认初值100
    private String photoPath;    // 学生照片路径


    // 构造方法
    public Student(String className, String name, String group, String studentId, String score, String photoPath) {
        this.className = className;
        this.name = name;
        this.group = group;
        this.studentId = studentId;
        this.score = score;
        this.photoPath = photoPath;
    }

    // 带参构造方法，默认成绩为100
    public Student(String className, String name, String group, String studentId) {
        this(className, name, group, studentId, "100", null); // 设置默认成绩为100，默认照片路径为null
    }


    // 获取学生图片对应的ImageIcon对象，用于在界面显示
    public ImageIcon getStudentImageIcon() {
        if (photoPath!= null &&!photoPath.isEmpty()) {
            return new ImageIcon(photoPath);
        }
        return null; // 如果路径为空，返回null，表示没有可用图片
    }

    //getter和setter
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}