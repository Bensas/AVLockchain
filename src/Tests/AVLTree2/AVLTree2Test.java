package Tests.AVLTree2;
import AVLTree2.AVLTree2;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class AVLTree2Test
{
    private AVLTree2<Integer> bt;


    class MyIntComparator implements Comparator<Integer>{
        public int compare(Integer a,Integer b){
            return a-b;
        }
    }

    @Before
    public void Before(){
        bt = new AVLTree2<Integer>(new MyIntComparator());
        bt.add(1);
        bt.add(10);
        bt.add(15);
        bt.add(16);
        bt.add(12);
        bt.add(14);
        bt.add(13);
        bt.add(9);
        bt.printTree();
        System.out.println("BALANCE FACTOR = " + bt.balanceFactor());
        System.out.println("HEIGHT = " + bt.height());
        System.out.println("TERMINO EL BEFORE\n\n");


    }

    @Test
    public void addNonExistingElementReturnTrue(){
        assertTrue(bt.add(11));
    }

    @Test
    public void addExistingElementReturnFalse(){
        assertFalse(bt.add(16));
    }

    @Test
    public void addNullReturnFalse(){
        assertFalse(bt.add(null));
    }

    @Test
    public void removeNonExistingElementReturnFalse(){
        assertFalse(bt.remove(25));
        bt.printTree();
        System.out.println("BALANCE FACTOR = " + bt.balanceFactor());
        System.out.println("HEIGHT = " + bt.height());

    }

    @Test
    public void removeExistingElementReturnTrue(){
        assertTrue(bt.remove(16));
        bt.printTree();
        System.out.println("BALANCE FACTOR = " + bt.balanceFactor());
        System.out.println("HEIGHT = " + bt.height());
    }

    @Test
    public void removeRootReturnTrue(){
        assertTrue(bt.remove(12));
        System.out.println("size = " + bt.size());
        bt.printTree();
        System.out.println("BALANCE FACTOR = " + bt.balanceFactor());
        System.out.println("HEIGHT = " + bt.height());

    }




}
