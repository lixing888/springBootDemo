package com.springboot.demo.entity;

import lombok.Data;

/**
 * @author lixing
 */
@Data
public class Node {
    public Integer data;
    public Node lift;
    public Node right;
    public boolean isDelete;
}
