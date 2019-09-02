package com.springboot.demo.util;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {

    private Node root;
    private List<Node> list = new ArrayList<Node>();

    //初始化二叉树
    public BinaryTree() {
        init();
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        System.out.println("根节点是:" + tree.root.data);
        //tree.preOrder(tree.root); //ABDECFHIG
        //tree.inOrder(tree.root); //DBEAHFICG
        tree.postOrder(tree.root); //GIHFCEDBA
        for (Node node : tree.getResult()) {
            System.out.print(node.data);
        }
        System.out.println("树的深度是" + tree.getTreeDepth(tree.root));


    }

    /**
     * 先序遍历
     */
    public void preOrder(Node node) {
        list.add(node);
        if (node.lchild != null)
            preOrder(node.lchild);
        if (node.rchild != null)
            preOrder(node.rchild);
    }

    /**
     * 中序遍历
     */
    public void inOrder(Node node) {
        if (node.lchild != null)
            inOrder(node.lchild);
        list.add(node);
        if (node.rchild != null)
            inOrder(node.rchild);
    }

    /**
     * 后序遍历
     */
    public void postOrder(Node node) {
        if (node.lchild != null)
            postOrder(node.lchild);
        if (node.rchild != null)
            postOrder(node.rchild);
        list.add(node);
    }

    /**
     * 返回当前节点所在数的深度
     * 说明:
     * 1、如果一棵树只有一个结点，它的深度为1。
     * 2、如果根结点只有左子树而没有右子树，那么树的深度是其左子树的深度加1；
     * 3、如果根结点只有右子树而没有左子树，那么树的深度应该是其右子树的深度加1；
     * 4、如果既有右子树又有左子树，那该树的深度就是其左、右子树深度的较大值再加1。
     */
    public int getTreeDepth(Node node) {
        if (node.lchild == null && node.rchild == null)
            return 0;
        int left = 0, right = 0;
        if (node.lchild != null) {
            left = getTreeDepth(node.lchild);
        }
        if (node.rchild != null) {
            right = getTreeDepth(node.rchild);
        }
        return left > right ? left + 1 : right + 1;

    }


    //获取遍历结果
    public List<Node> getResult() {
        return list;
    }


    public void init() {
        Node D = new Node("D", null, null);
        Node E = new Node("E", null, null);
        Node H = new Node("H", null, null);
        Node I = new Node("I", null, null);
        Node G = new Node("G", null, null);

        Node F = new Node("F", H, I);
        Node C = new Node("C", F, G);
        Node B = new Node("B", D, E);
        Node A = new Node("A", B, C);
        root = A;
    }

    /**
     * 内部类：节点类
     */
    private class Node {
        private String data; //父节点值
        private Node lchild; //左孩子节点
        private Node rchild;//右孩子节点

        private Node(String data, Node lchild, Node rchild) {
            this.data = data;
            this.lchild = lchild;
            this.rchild = rchild;
        }
    }
}
