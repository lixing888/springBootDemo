package com.springboot.demo.Stream;

/**
 * @program: springBootDemo
 * @description: stream测试实体类
 * @author: lixing
 * @create: 2020-12-12 22:07
 **/
public class TestStreamModel {

    private int id;

    private String name;

    private int grade;

    private int classes;

    private double score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getClasses() {
        return classes;
    }

    public void setClasses(int classes) {
        this.classes = classes;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "TestStreamModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", classes=" + classes +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestStreamModel that = (TestStreamModel) o;

        if (id != that.id) return false;
        if (grade != that.grade) return false;
        if (classes != that.classes) return false;
        if (Double.compare(that.score, score) != 0) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + grade;
        result = 31 * result + classes;
        temp = Double.doubleToLongBits(score);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
