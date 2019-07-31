package com.springboot.demo.ListUtil;

public class MyLienkedList<T> {
    private Node<T> root;
    private Node<T> end;
    private int size;

    public void add(T data) {
        if (root == null) {
            root = new Node<>();
            root.setData(data);
            end = root;
        } else {
            Node<T> newNode = new Node<>();
            newNode.setData(data);
            end.setNext(newNode);
            end = newNode;
        }
        size++;
    }

    public void show() {
        Node<T> current = root;
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNext();
        }
    }
}
