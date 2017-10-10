package AVLTree2;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class AVLTree2<T> {

    private int size;
    private Node<T> head;
    private Comparator<T> cmp;

    public AVLTree2(Comparator<T> cmp){
        size = 0;
        head = null;
        this.cmp = cmp;
    }

    public boolean add(T elem){
        if(elem == null) return false;
        int prevSize = size;
        head = addR(elem,head);
        return size > prevSize;
    }

    private Node<T> addR(T elem,Node<T> current){
        if(current == null){
            size++;
            return new Node<>(elem);
        }
        if(cmp.compare(current.elem,elem) > 0){
            current.left = addR(elem,current.left);
            current.updateBalanceFactor();
            current = balanceLeft(current,elem);
        } else if(cmp.compare(current.elem,elem) < 0){
            current.right = addR(elem,current.right);
            current.updateBalanceFactor();
            current = balanceRight(current,elem);
        }

        current.height = Integer.max((current.left!=null?current.left.height:0),(current.right!=null?current.right.height:0)) + 1;
        return current;
    }

    private Node<T> balanceLeft(Node<T> n,T elem){
        if(n.balanceFactor == 2){
            if (cmp.compare(n.left.elem,elem) > 0){
                n = rotateWithLeftChild(n);
            }
            else {
                n = doubleWithLeftChild(n);
            }
        }
        return n;
    }

    private Node<T> balanceRight(Node<T> n,T elem){
        if (n.balanceFactor == -2)
            if (cmp.compare(elem,n.right.elem) > 0){
                n = rotateWithRightChild(n);
            }
            else{
                n = doubleWithRightChild(n);
            }
            return n;
    }

    public boolean remove(T elem){
        if(elem == null) return false;
        int prevSize = size;
        head = removeR(elem,head);
        return prevSize > size;
    }

    private Node<T> removeR(T elem,Node<T> current){
        if(current == null){
            return null;
        }
        /*  si es menor*/
        if(cmp.compare(current.elem,elem) > 0){
            int prevSize = size;
            current.left = removeR(elem,current.left);
            if(size < prevSize){
                current.height = Integer.max((current.left!=null?current.left.height:0),(current.right!=null?current.right.height:0)) + 1;
                current.updateBalanceFactor();
                if((current.balanceFactor > 1) || (current.balanceFactor < -1))  current = rotateWithRightChild(current);
            }
            return current;
        }

        /*  si es mayor*/
        if(cmp.compare(current.elem,elem) < 0){
            int prevSize = size;
            current.right = removeR(elem,current.right);
            if(size < prevSize){
                current.height = Integer.max((current.left!=null?current.left.height:0),(current.right!=null?current.right.height:0)) + 1;
                current.updateBalanceFactor();
                if((current.balanceFactor > 1) || (current.balanceFactor < -1))  current = rotateWithLeftChild(current);
            }
            return current;
        }

        /*  si es igual*/
        if(current.left == null){
            size--;
            return current.right;
        }
        Node<T> aux = getMinNode(current.right);
        if(aux == null){
            size--;
            return current.left;
        }
        Node<T> ret = new Node<T>(aux.elem,current.right,current.left);
        ret.right = removeR(ret.elem,ret.right);
        ret.height = Integer.max((ret.left!=null?ret.left.height:0),(ret.right!=null?ret.right.height:0)) + 1;

        return ret;
     }

     /**
     *
     * Left rotation.
     *
     * @param unbalanced Node unbalanced that is gonna be rotated.
     * @return upper Node in the new rotation.
     */
    private Node<T> rotateWithLeftChild(Node<T> unbalanced){
        Node<T> aux = unbalanced.left;

        unbalanced.left = aux.right;
        aux.right = unbalanced;

        unbalanced.height = Integer.max((unbalanced.left!=null?unbalanced.left.height:0),(unbalanced.right!=null?unbalanced.right.height:0)) + 1;
        aux.height = Integer.max(aux.left!=null?aux.left.height:0,aux.right.height) + 1;

        return aux;
    }

    /**
     *
     * LeftLeft rotation.
     *
     * @param unbalanced Node unbalanced that is gonna be rotated.
     * @return upper Node in the new rotation.
     */
    private Node<T> doubleWithLeftChild(Node<T> unbalanced){
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
    private Node<T> rotateWithRightChild(Node<T> unbalanced){
        Node<T> aux = unbalanced.right;

        unbalanced.right = aux.left;
        aux.left = unbalanced;

        unbalanced.height = Integer.max((unbalanced.left!=null?unbalanced.left.height:0),(unbalanced.right!=null?unbalanced.right.height:0)) + 1;
        aux.height = Integer.max(aux.left.height,aux.right!=null?aux.right.height:0) + 1;

        return aux;
    }

    /**
     *
     * RightRight rotation.
     *
     * @param unbalanced Node unbalanced that is gonna be rotated.
     * @return upper Node in the new rotation.
     */
    private Node<T> doubleWithRightChild(Node<T> unbalanced){
        unbalanced.right = rotateWithLeftChild(unbalanced.right);
        return rotateWithRightChild(unbalanced);
    }

    public int height(){
        return head.height;
    }

    public int balanceFactor(){
        return head.balanceFactor;
    }

    private Node<T> getMinNode(Node<T> n){
        if(n == null) return null;
        if(n.left == null) return n;
        return getMinNode(n.left);
    }

    public int size(){
        return size;
    }

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

    private static class Node<T>{
        T elem;
        Node<T> left;
        Node<T> right;
        int balanceFactor;
        int height;

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

            void updateBalanceFactor(){
            balanceFactor = (left!=null?left.height:0) - (right!=null?right.height:0);
        }

            public int hashCode () {
                return this.elem.hashCode() * (balanceFactor + 2);
            }
    }
}
