package com.springboot.demo.util;

import com.springboot.demo.entity.Node;

/**
 * @author lixing
 * 二叉树
 */
public class TwoTree {
    private Node root;
    private int size;

    public void add(Integer data) {
        Node newNode = new Node();
        newNode.data = data;
        if (root == null) {
            root = newNode;
        } else {
            Node current = root;
            Node parent;
            while (current != null) {
                parent = current;
                if (current.data > data) {
                    current = current.lift;
                    if (current == null) {
                        parent.lift = newNode;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        parent.right = newNode;
                    }
                }
            }
        }
        size++;
    }

    public boolean isExists(Integer data) {
        Node current = root;
        while (current != null) {
            if (current.data > data) {
                current = current.lift;
            } else if (current.data < data) {
                current = current.right;
            } else {
                return !current.isDelete;
            }
        }
        return false;
    }

    public void delete(Integer data) {
        Node current = root;
        while (current != null) {
            if (current.data > data) {
                current = current.lift;
            } else if (current.data < data) {
                current = current.right;
            } else {
                if (current.lift == null && current.right == null) {
                    current = null;
                    size--;
                } else {
                    if (!current.isDelete) {
                        current.isDelete = true;
                        size--;
                    }
                }
                break;
            }
        }
    }

    public void forEach(Node root) {
        if (null != root) {
            forEach(root.lift);
            System.out.println(root.data);
            forEach(root.right);
        }
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {

    }
}
