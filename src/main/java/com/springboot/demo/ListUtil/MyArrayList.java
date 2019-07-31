package com.springboot.demo.ListUtil;

public class MyArrayList<T> {
    private Object[] arr;
    private int size;

    public <T> MyArrayList() {
        arr = new Object[]{};
    }

    public void add(T data) {
        Object[] newArr = new Object[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }
        newArr[newArr.length - 1] = data;
        arr = newArr;
        size++;
    }

    public void remove(int index) {
        Object[] newArr = new Object[arr.length - 1];
        for (int i = 0; i < newArr.length; i++) {
            if (i < index) {
                newArr[i] = arr[i];
            } else {
                newArr[i] = arr[i + 1];
            }
        }
        arr = newArr;
        size--;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void remove(T data) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == data) {
                remove(i);
                break;
            }
        }
    }

    public int size() {
        return size;
    }

    public void show() {
        for (Object o : arr) {
            System.out.println(o);
        }
    }
}
