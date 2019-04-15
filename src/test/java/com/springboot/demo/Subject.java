package com.springboot.demo;

import java.util.ArrayList;

/**
 * 观察者模式 用于一对多 或者是 一对一 关系中 在一中有什么改动
 * 会导致和他有关系的多(也就是观察者们也发生变动)
 * @Authors lixing
 */
public interface Subject {
    public void attach(Observer o);
    public void detach(Observer o);
    public void notice();
}
interface Observer{
    public void update();
}
class Student implements Observer{
    String teacherPhone = "";
    Teacher teacher ;
    public Student(String phone,Teacher t){
        teacher = t;
        teacherPhone = phone;
    }
    @Override
    public void update() {
        teacherPhone = teacher.getPhone();
    }
    public void show(){
        System.out.println(teacherPhone);
    }
}
class Teacher implements Subject {
    private String phone;
    private ArrayList<Observer> students;

    public Teacher() {
        phone = "";
        students = new ArrayList<Observer>();
    }

    @Override
    public void attach(Observer o) {
        students.add(o);
    }

    @Override
    public void detach(Observer o) {
        students.remove(o);
    }

    @Override
    public void notice() {
        for (Observer o : students) {
            o.update();
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notice();
    }

    public static void main(String[] args) {
        Teacher teacher = new Teacher();
        Student s = new Student("", teacher);
        Student s1 = new Student("", teacher);
        teacher.attach(s);
        teacher.attach(s1);
        //核心在这个set方法上 他的变动导致很多都发生了改变
        teacher.setPhone("13215646");
        s.show();
        s1.show();

    }
}