package model;
import java.io.Serializable;

// 学生信息类, 处理学生基本信息
// 实现Serializable接口，使得Student对象可以保存到文件
public class Student implements Serializable{
    // 所有属性全设为String
    private String className;    // 班级
    private String name;         // 姓名
    private String group;        // 小组
    private String studentId;    // 学号
    private String score;        // 成绩

    // 构造方法
    public Student(String className, String name, String group, String studentId) {
        this.className = className;
        this.name = name;
        this.group = group;
        this.studentId = studentId;
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
}
