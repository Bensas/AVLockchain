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
        } else if(cmp.compare(current.elem,elem) < 0){
            current.right = addR(elem,current.right);
        }
        return current;
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
        if(current.elem.equals(elem)){
            Node<T> aux = getMinNode(current.right);
            if(aux == null){
                size--;
                return current.left;
            }
            Node<T> ret = new Node<T>(aux.elem,current.right,current.left);
            ret.right = removeR(ret.elem,ret.right);
            return ret;
        }
        if(cmp.compare(current.elem,elem) > 0){
            current.left = removeR(elem,current.left);
            return current;
        }
        current.right = removeR(elem,current.right);
        return current;
    }


    private Node<T> getMinNode(Node<T> n){
        if(n == null) return null;
        if(n.left == null) return n;
        return getMinNode(n.left);
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

        Node(T elem,Node<T> right,Node<T> left){
          this.elem = elem;
          this.right = right;
          this.left = left;
          balanceFactor = left.balanceFactor - right.balanceFactor;
        }

        Node(T elem){
            this.elem = elem;
            left = right = null;
            balanceFactor = 0;
        }
    }
}
