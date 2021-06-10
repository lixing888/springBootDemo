package com.springboot.demo.util.CreateExcelUtil;

/**
 * Copyright (C), 2018-2021
 * FileName: Taxi
 * Author:   李兴
 * Date:     2021/6/8 17:23
 * Description: 出租车实体类
 */
public class Taxi implements  Comparable<Taxi>{
    int id;
    int starttime;
    String stime;
    double waittime;
    double earntime;

    double score;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStarttime() {
        return starttime;
    }

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public double getWaittime() {
        return waittime;
    }

    public void setWaittime(double waittime) {
        this.waittime = waittime;
    }

    public double getEarntime() {
        return earntime;
    }

    public void setEarntime(double earntime) {
        this.earntime = earntime;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Taxi{" +
                "id=" + id +
                ", starttime=" + starttime +
                ", stime='" + stime + '\'' +
                ", waittime=" + waittime +
                ", earntime=" + earntime +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(Taxi o) {
        return this.starttime-o.starttime;
    }
}

