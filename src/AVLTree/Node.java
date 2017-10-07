package AVLTree;
/**
 *
 * @author Guido
 */
public class Node{
    int data;
    Node left;
    Node right;
    int height;

    public Node(int data){
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
