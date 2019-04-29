package com.springboot.demo.vo;

import lombok.Data;

@Data
public class MyBean {

    private Student first;

    public Student getFirst() {
        return first;
    }

    public void setFirst(Student first) {
        this.first = first;
    }


}
