package com.springboot.demo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Transaction {
    private final int id;
    private final Integer value;
    private final Type type;
    @JsonIgnore
    private String age;

    public Transaction(int id, Integer value, Type type) {
        this.id = id;
        this.value = value;
        this.type = type;
    }

    public enum Type {
        A, B, C, D, GEOCERY
    }

    public int getId() {
        return id;
    }

    public Integer getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

}
