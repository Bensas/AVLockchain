package AVLTree;

import java.io.Serializable;
import java.util.*;

/**
 * AVL tree implementation (with minor modifications)
 * @param <T>
 */
public class AVLTree<T> implements Serializable{

    private int size;
    private Node<T> head;
    private Comparator<T> cmp;

    public AVLTree(Comparator<T> cmp){
        size = 0;
        head = null;
        this.cmp = cmp;
    }

    public AVLTree(){
        size = 0;
        head = null;
        this.cmp = new ComparableComparator<>();
    }

    /**
     * Adds an element to the tree and logs the modification in a
     * block
     * @param elem element to be added
     * @param blockId identifier of the block
     * @return boolean: returns if the element was added successfully
     */
    public boolean add(T elem,int blockId){
        if(elem == null) return false;
        int prevSize = size;
        head = addR(elem,head,blockId);
        return size > prevSize;
    }

    /**
     * Auxiliary recursive function to add an element
     * @param elem element to be added
     * @param current current node in scope
     * @param blockId identifier of the block
     * @return the first element of the blockchain
     */
    private Node<T> addR(T elem, Node<T> current, int blockId){
        int prevSize= size;
        if(current == null){
            size++;
            current = new Node<T>(elem);
            current.modifiersByAdding.add(blockId);
            return current;
        }
        if(cmp.compare(current.elem,elem) > 0){
            current.left = addR(elem,current.left,blockId);
            current.updateBalanceFactor();
            current = balanceLeft(current,elem,blockId);
        } else if(cmp.compare(current.elem,elem) < 0){
            current.right = addR(elem,current.right,blockId);
            current.updateBalanceFactor();
            current = balanceRight(current,elem,blockId);
        }

        if(prevSize < size){
            current.modifiersByAdding.add(blockId);
        }

        current.updateHeight();
        return current;
    }

    /**
     * Auxiliary function to check if the left subtree is balanced. If it's not performs
     * a rotation.
     * @param n
     * @param elem
     * @param blockId
     * @return
     */
    private Node<T> balanceLeft(Node<T> n,T elem,int blockId){
        n.updateHeight();
        n.updateBalanceFactor();
        if(n.balanceFactor == 2){
            if (cmp.compare(n.left.elem,elem) > 0){
                n = rotateWithLeftChild(n,blockId);
            }
            else {
                n = doubleWithLeftChild(n,blockId);
            }
        }
        return n;
    }
    /**
     * Auxiliary function to check if the right subtree is balanced. If it's not performs
     * a rotation.
     * @param n
     * @param elem
     * @param blockId
     * @return
     */
    private Node<T> balanceRight(Node<T> n,T elem,int blockId){
        n.updateHeight();
        n.updateBalanceFactor();
        if (n.balanceFactor == -2)
            if (cmp.compare(elem,n.right.elem) > 0){
                n = rotateWithRightChild(n,blockId);
            }
            else{
                n = doubleWithRightChild(n,blockId);
            }
            return n;
    }

    /**
     * Removes an element from the tree
     * @param elem element to be removed
     * @param blockId identifier of the block
     * @return returns true if the element was removed
     */
    public boolean remove(T elem,int blockId){
        if(elem == null) return false;
        int prevSize = size;
        head = removeR(elem,head,blockId);
        return prevSize > size;
    }

    /**
     * Auxiliary function to remove an element from the tree
     * @param elem element to be removed
     * @param current current node in scope
     * @param blockId identifier of the block
     * @return returns the root of the tree/subtree.
     */
    private Node<T> removeR(T elem,Node<T> current,int blockId){
        if(current == null){
            return null;
        }
        /*  if its smaller*/
        if(cmp.compare(current.elem,elem) > 0){
            int prevSize = size;
            current.left = removeR(elem,current.left,blockId);
            if(size < prevSize){
                current.modifiersByRemoving.add(blockId);
                current.updateHeight();
                current.updateBalanceFactor();
                if((current.balanceFactor > 1) || (current.balanceFactor < -1))  current = rotateWithRightChild(current,blockId);
            }
            return current;
        }

        /*  if its greater*/
        if(cmp.compare(current.elem,elem) < 0){
            int prevSize = size;
            current.right = removeR(elem,current.right,blockId);
            if(size < prevSize){
                current.modifiersByRemoving.add(blockId);
                current.updateHeight();
                current.updateBalanceFactor();
                if((current.balanceFactor > 1) || (current.balanceFactor < -1))  current = rotateWithLeftChild(current,blockId);
            }
            return current;
        }

        /*  if its equals*/
        if(current.left == null){
            size--;
            if(current.right != null)   current.right.modifiersByRemoving.add(blockId);
            return current.right;
        }
        Node<T> aux = getMinNode(current.right);
        if(aux == null){
            size--;
            if (current.left != null)   current.left.modifiersByRemoving.add(blockId);
            return current.left;
        }
        Node<T> ret = new Node<T>(aux.elem,current.right,current.left);
        ret.right = removeR(ret.elem,ret.right,blockId);
        ret.updateHeight();

        return ret;
     }

    /**
     * Gets the elements that were modified by operations
     * @param elem
     * @return
     */
    public ArrayList<HashSet<Integer>> getModifiers(T elem) {
        Node<T> current = head;
        ArrayList<HashSet<Integer>> modifiers = new ArrayList<>();
        while (current != null){
            if (cmp.compare(current.elem, elem) < 0)
                current = current.right;
            else if (cmp.compare(current.elem, elem) > 0)
                current = current.left;
            else{
                modifiers.add(current.modifiersByAdding);
                modifiers.add(current.modifiersByRemoving);
                modifiers.add(current.modifiersByRotation);
                return modifiers;
            }
        }
        return null;
    }

     /**
     *
     * Left rotation.
     *
     * @param unbalanced unbalanced Node which is going to be rotated.
     * @return upper Node in the new rotation.
     */
    private Node<T> rotateWithLeftChild(Node<T> unbalanced,int blockId){
        Node<T> aux = unbalanced.left;
        unbalanced.modifiersByRotation.add(blockId);

        if(unbalanced.left != null) unbalanced.left.modifiersByRotation.add(blockId);

        unbalanced.left = aux.right;

        if(aux.right != null)   aux.right.modifiersByRotation.add(blockId);

        aux.right = unbalanced;

        unbalanced.updateHeight();
        aux.updateHeight();

        return aux;
    }

    /**
     *
     * LeftLeft rotation.
     *
     * @param unbalanced Node unbalanced that is gonna be rotated.
     * @return upper Node in the new rotation.
     */
    private Node<T> doubleWithLeftChild(Node<T> unbalanced,int blockId){
        unbalanced.left = rotateWithRightChild(unbalanced.left,blockId);
        return rotateWithLeftChild(unbalanced,blockId);
    }

    /**
     *
     * Right rotation.
     *
     * @param unbalanced unbalanced Node that is going to be rotated.
     * @return upper Node in the new rotation.
     */
    private Node<T> rotateWithRightChild(Node<T> unbalanced,int blockId){
        Node<T> aux = unbalanced.right;

        unbalanced.modifiersByRotation.add(blockId);
        if(unbalanced.right != null)    unbalanced.right.modifiersByRotation.add(blockId);

        unbalanced.right = aux.left;

        aux.left = unbalanced;

        unbalanced.updateHeight();
        aux.updateHeight();

        return aux;
    }

    /**
     *
     * RightRight rotation.
     *
     * @param unbalanced Node unbalanced that is gonna be rotated.
     * @return upper Node in the new rotation.
     */
    private Node<T> doubleWithRightChild(Node<T> unbalanced,int blockId){
        unbalanced.right = rotateWithLeftChild(unbalanced.right,blockId);
        return rotateWithRightChild(unbalanced,blockId);
    }

    public int height(){
        return head.height;
    }

    public int balanceFactor(){
        return head.balanceFactor;
    }

    /**
     * Find the leftmost element of the tree recursively
     * @param n current node in scope
     * @return leftmost element
     */
    private Node<T> getMinNode(Node<T> n){
        if(n == null) return null;
        if(n.left == null) return n;
        return getMinNode(n.left);
    }

    public int size(){
        return size;
    }
    /**
     *  Prints a description of the tree
     */
    public void printTree(){
        if (head != null) {
            Queue<Node<T>> s1 = new LinkedList<>();
            Queue<Node<T>> s2 = new LinkedList<>();
            s1.add(head);
            int level = 0;
            while (!s1.isEmpty() || !s2.isEmpty()) {
                if (s1.isEmpty()) {
                    s1 = s2;
                    s2 = new LinkedList<>();
                }
                System.out.println("Level: " + level++);
                while (!s1.isEmpty()) {
                    Node<T> aux = s1.remove();
                    System.out.print(aux.elem + " (");
                    if (aux.left != null) {
                        System.out.print(aux.left.elem);
                        s2.add(aux.left);
                    }
                    System.out.print(",");
                    if (aux.right != null) {
                        System.out.print(aux.right.elem);
                        s2.add(aux.right);
                    }
                    System.out.println(")");
                }
            }
        }
    }
    /**
     *  Generate an Ascii representation of the tree.
     */
    public String   getAsciiRepresentation(){
        return generateAsciiRepresentation(head,0,0);
    }

    /*  State: -1 = prev. was left; 0 = root; 1 = was right */
    private String  generateAsciiRepresentation(Node<T> current,int level,int state){
        String ret = new String();
        if(current == null){
            return "";
        }
        if(state == 0) {
            ret += generateAsciiRepresentation(current.right, level + 1, 1);
            if(!ret.substring(ret.length() -1 ).equals("\n"))   ret+= "\n";
            ret += "-"+ current.elem.toString() + "\n"+ generateAsciiRepresentation(current.left, level + 1, -1);
            return ret;
        }
        if(current.right != null)   ret += generateAsciiRepresentation(current.right,level+1,1) + "\n";
        for (int i = 0; i < level; i++) ret += "\t";
        if(state == 1)  ret +=  "/" + current.elem.toString();
        else    ret +=  "\\" + current.elem.toString();
        if(current.left != null) ret += "\n" + generateAsciiRepresentation(current.left,level+1,-1);
        return ret;
    }

    private static class Node<T> implements Serializable{
        T elem;
        Node<T> left;
        Node<T> right;
        int balanceFactor;
        int height;
        HashSet<Integer> modifiersByAdding   = new HashSet<>();
        HashSet<Integer>  modifiersByRemoving = new HashSet<>();
        HashSet<Integer>  modifiersByRotation = new HashSet<>();

        Node(T elem,Node<T> right,Node<T> left){
          this.elem = elem;
          this.right = right;
          this.left = left;
          updateBalanceFactor();
        }

        Node(T elem){
            this.elem = elem;
            left = right = null;
            balanceFactor = 0;
            height = 1;
        }

            void updateHeight() {   height = Integer.max((left!=null?left.height:0),(right!=null?right.height:0)) + 1; }

            void updateBalanceFactor(){
            balanceFactor = (left!=null?left.height:0) - (right!=null?right.height:0);
        }

            public int hashCode () {
                return this.elem.hashCode() * (balanceFactor + 2);
            }
    }

    private class ComparableComparator<T> implements Comparator<T>{

        public int compare(T elem1,T elem2){
            Comparable<T> cmpElem= (Comparable<T>) elem1;
            return cmpElem.compareTo(elem2);
        }
    }
}
