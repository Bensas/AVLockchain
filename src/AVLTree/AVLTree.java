package AVLTree;

import java.util.Stack;

public class AVLTree {
    public static  Node root;
    int size= 0;
    public AVLTree(){
        root = null;
    }

    public boolean find(int id){
        Node current = root;
        while(current!=null){
            if(current.data==id){
                return true;
            }else if(current.data>id){
                current = current.left;
            }else{
                current = current.right;
            }
        }
        return false;
    }

    public int height (Node n){
        return n == null ? -1 : n.height;
    }

    public int max (int a, int b){
        if (a > b)
            return a;
        return b;
    }

    public boolean add (int data, int modifierBlock){
        int previousSize= size;
        root = add (data, root, false, modifierBlock);
        if(size > previousSize)
            return true;
        return false;
    }

    private Node add (int data, Node current, Boolean modified, int modifierBlock){

        if (current == null) {
            current = new Node(data);
            modified= true;
            current.modifierBlocks.add(modifierBlock);
            size++;
        }
        else if (data < current.data){
            current.left = add(data, current.left, modified, modifierBlock);
            if (height(current.left) - height(current.right) == 2){
                if (data < current.left.data){
                    current = rotateWithLeftChild(current);
                }
                else {
                    current = doubleWithLeftChild(current);
                }
            }
        }
        else if (data > current.data){
            current.right = add(data, current.right, modified, modifierBlock);

            if ( height(current.right) - height(current.left) == 2)
                if (data > current.right.data){
                    current = rotateWithRightChild(current);
                }
                else{
                    current = doubleWithRightChild(current);
                }
        }
        if(modified)
            current.modifierBlocks.add(modifierBlock);
        current.height = max(height(current.left), height(current.right)) + 1;
        return current;
    }

    private Node rotateWithLeftChild (Node k2){
        Node k1 = k2.left;

        k2.left = k1.right;
        k1.right = k2;

        k2.height = max(height (k2.left), height (k2.right)) + 1;
        k1.height = max(height (k1.left), k2.height) + 1;

        return (k1);
    }

    private Node doubleWithLeftChild (Node k3){
        k3.left = rotateWithRightChild (k3.left);
        return rotateWithLeftChild (k3);
    }

    private Node rotateWithRightChild (Node k1){
        Node k2 = k1.right;

        k1.right = k2.left;
        k2.left = k1;

        k1.height = max(height (k1.left), height (k1.right)) + 1;
        k2.height = max(height (k2.right), k1.height) + 1;

        return (k2);
    }

    private Node doubleWithRightChild (Node k1){
        k1.right = rotateWithLeftChild (k1.right);
        return rotateWithRightChild (k1);
    }

    public void printTree(){
        Stack<Node> s1 = new Stack<>();
        Stack<Node> s2 = new Stack<>();
        s1.push(root);
        int level = 0;
        while (!s1.isEmpty() || !s2.isEmpty()){
            if(s1.isEmpty()) {
                s1 = s2;
                s2 = new Stack<>();
            }
            System.out.println("Level: " + level++);
            while (!s1.isEmpty()) {
                Node aux = s1.pop();
                System.out.print(aux.data + " (");
                if(aux.left != null) {
                    System.out.print(aux.left.data + ", ");
                    s2.push(aux.left);
                }
                if(aux.right != null){
                    System.out.print(aux.right.data);
                    s2.push(aux.right);
                }
                System.out.println(")");
            }
        }
    }

    /* try with this
    *         AVLTree avl = new AVLTree();
        avl.add(5);
        avl.add(10);
        avl.add(6);
        avl.add(1);
        avl.add(7);
        avl.printTree();
    * */

    public boolean remove() {


        return true;
    }

    private static class Node {
        int data;
        Node left;
        Node right;
        int height;

        public Node(int data) {
            this.data = data;
            left = null;
            right = null;
            }

        public Node(int data, Node left, Node right){
            this.data = data;
            this.left = left;
            this.right = right;
            }
    }
}

