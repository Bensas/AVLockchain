package AVLTree;

import java.io.Serializable;

public class AVLTree implements Serializable{
    public static  Node root;
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

    public boolean add (int data){
        root = add (data, root);
        return true;
    }

    private Node add (int data, Node current){
        if (current == null)
            current = new Node(data);
        else if (data < current.data){
            current.left = add(data, current.left);
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
            current.right = add(data, current.right);

            if ( height(current.right) - height(current.left) == 2)
                if (data > current.right.data){
                    current = rotateWithRightChild(current);
                }
                else{
                    current = doubleWithRightChild(current);
                }
        }
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

        if(root == null)
            System.out.println();
        else{

            LevelPriorityQueue list= new LevelPriorityQueue();
            printR(root, true, 1, list);
        }
    }

    private void printR(Node current, boolean isFurtherMostRight, int level, LevelPriorityQueue toBePrinted){

        if(current== null)
            return;

        toBePrinted.add(current.data, level);
        if(isFurtherMostRight){

            while(!toBePrinted.isEmpty(level)){

                //aqui iria nuestra manera ascii art de imprimir el arbol, adentro de este loop
                //se imprimiran solo los elementos del nivel en el que se esta.
            }
            printR(current.left, false, level + 1, toBePrinted);
            printR(current.right, true, level + 1, toBePrinted);
            return;
        }
        printR(current.left, false, level + 1, toBePrinted);
        printR(current.right, false, level + 1, toBePrinted);
        return;
    }

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

