package com.springboot.demo.ListUtil;

import lombok.Data;

@Data
public class Node<T> {
    private T data;
    private Node<T> next;
}
