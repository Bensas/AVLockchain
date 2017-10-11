package AVLTree;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * AVL Tree class with its basic functions
 * Node class is defined inside it.
 *
 */
public class AVLTree implements Serializable{
    public Node root;
    int size= 0;

    /**
     * Constructor of the class that only initialize root.
     */
    public AVLTree(){
        root = null;
    }

    /**
     *
     * @param id id to search in the avl tree.
     * @return true if the node was found, false if otherwise.
     */
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

    /**
     *
     * @param id id to search in the avl tree.
     * @return true if the node was found, false if otherwise.
     */
    public ArrayList<Integer> lookUp(int id){
        Node current = root;
        while(current!=null){
            if(current.data==id){
                return current.modifierBlocks;
            }else if(current.data>id){
                current = current.left;
            }else{
                current = current.right;
            }
        }
        return null;
    }

    /**
     *
     * @param n Node which you want to know its height.
     * @return node's height if its not null. Otherwise it returns -1.
     */
    public int height (Node n){
        return n == null ? -1 : n.height;
    }

    /**
     *
     * @param a first value.
     * @param b second value.
     * @return which value is higher.
     */
    public int max (int a, int b){
        if (a > b)
            return a;
        return b;
    }

    /**
     *
     * @param data wanted to add in the avl tree.
     * @param modifierBlock which block is making the modification.
     * @return if the data was successfully added by checking avl size.
     */
    public boolean add (int data, int modifierBlock){
        int previousSize= size;
        root = add (data, root, new AtomicBoolean(false), modifierBlock);
        if(size > previousSize)
            return true;
        return false;
    }

    /**
     *
     * Wrapper's recursive add. If the tree gets unbalanced when the insertion is made, then a rotation ocurrs.
     *
     * @param data wanted to be added.
     * @param current Node.
     * @param modified if there has been a modification.
     * @param modifierBlock which block is making the modification.
     * @return a Node that should be the child of the previous recursive call.
     */
    private Node add (int data, Node current, AtomicBoolean modified, int modifierBlock){

        if (current == null) {
            current = new Node(data);
            modified.set(true);
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
        if(modified.get())
            current.modifierBlocks.add(modifierBlock);
        current.height = max(height(current.left), height(current.right)) + 1;
        return current;
    }

    /**
     *
     * Left rotation.
     *
     * @param unbalanced Node unbalanced that is gonna be rotated.
     * @return upper Node in the new rotation.
     */
    private Node rotateWithLeftChild(Node unbalanced){
        Node aux = unbalanced.left;

        unbalanced.left = aux.right;
        aux.right = unbalanced;

        unbalanced.height = max(height(unbalanced.left), height(unbalanced.right)) + 1;
        aux.height = max(height(aux.left), unbalanced.height) + 1;

        return aux;
    }

    /**
     *
     * LeftLeft rotation.
     *
     * @param unbalanced Node unbalanced that is gonna be rotated.
     * @return upper Node in the new rotation.
     */
    private Node doubleWithLeftChild(Node unbalanced){
        unbalanced.left = rotateWithRightChild(unbalanced.left);
        return rotateWithLeftChild(unbalanced);
    }

    /**
     *
     * Right rotation.
     *
     * @param unbalanced Node unbalanced that is gonna be rotated.
     * @return upper Node in the new rotation.
     */
    private Node rotateWithRightChild(Node unbalanced){
        Node aux = unbalanced.right;

        unbalanced.right = aux.left;
        aux.left = unbalanced;

        unbalanced.height = max(height(unbalanced.left), height(unbalanced.right)) + 1;
        aux.height = max(height(aux.right), unbalanced.height) + 1;

        return (aux);
    }

    /**
     *
     * RightRight rotation.
     *
     * @param unbalanced Node unbalanced that is gonna be rotated.
     * @return upper Node in the new rotation.
     */
    private Node doubleWithRightChild(Node unbalanced){
        unbalanced.right = rotateWithLeftChild(unbalanced.right);
        return rotateWithRightChild(unbalanced);
    }

    /**
     *
     * Prints the tree separating by levels, and adding Node's child data values.
     */
    public void printTree(){
        if (root != null) {
            Queue<Node> s1 = new LinkedList<>();
            Queue<Node> s2 = new LinkedList<>();
            s1.add(root);
            int level = 0;
            while (!s1.isEmpty() || !s2.isEmpty()) {
                if (s1.isEmpty()) {
                    s1 = s2;
                    s2 = new LinkedList<>();
                }
                System.out.println("Level: " + level++);
                while (!s1.isEmpty()) {
                    Node aux = s1.remove();
                    System.out.print(aux.data + " (");
                    if (aux.left != null) {
                        System.out.print(aux.left.data);
                        s2.add(aux.left);
                    }
                    System.out.print(",");
                    if (aux.right != null) {
                        System.out.print(aux.right.data);
                        s2.add(aux.right);
                    }
                    System.out.println(")");
                }
            }
        }
    }


    //  MAÑANA LO HAGO (SOY NACHO NEGRO)
    public boolean remove() {


        return true;
    }

    /**
     *
     * @return AVL tree prefix hash code.
     */
    public int hashCode(){
        if(root == null)
            return 0;
        else
            return hashCode(1, root);
    }

    /**
     *
     * HashCode's recursive wrapper function.
     *
     * @param currentHash the sum of all previous prefix values multiplied by 31.
     * @param current Node.
     */
    private int hashCode(int currentHash, Node current){

        if(current == null)
            return currentHash;

        currentHash= 31*currentHash + current.hashCode();
        currentHash= hashCode(currentHash, current.left);
        currentHash= hashCode(currentHash, current.right);
        return currentHash;
    }


    /**
     * Node class for the AVL tree.
     */
    private static class Node implements Serializable{
        int data;
        Node left;
        Node right;
        int height= 0;
        ArrayList<Integer> modifierBlocks = new ArrayList<>();

        /**
         *
         * @param data of the Node.
         */
        public Node(int data) {
            this.data = data;
            left = null;
            right = null;
            }

        /**
         *
         * @param data of the Node.
         * @param left Node.
         * @param right Node.
         */
        public Node(int data, Node left, Node right){
            this.data = data;
            this.left = left;
            this.right = right;
            }

        /**
         *
         * @return Node's balance factor.
         */
        public int getBalanceFactor(){
            return (left == null?0:left.height) - (right == null? 0:right.height);
        }

        /**
         *
         * @return Node's hash code.
         */
        public int hashCode () {
            return this.data +37*(getBalanceFactor() + 2);
        }
    }

    public static void main(String[] args){

        AVLTree t= new AVLTree();
        t.add(1,0);
        t.add(2,1);
        t.add(3,2);
        t.add(4,3);

        ArrayList<Integer> a= t.lookUp(1);
        for(Integer i: a){
            System.out.println(i + " ");
        }
    }
}

