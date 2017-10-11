package Tests.AVLTree2;
import AVLTree2.AVLTree2;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class AVLTree2Test
{
    private AVLTree2<Integer>   bt;
    private AVLTree2<Integer>   t;


    class MyIntComparator implements Comparator<Integer>{
        public int compare(Integer a,Integer b){
            return a-b;
        }
    }

    @Before
    public void Before(){
        Integer blockId = 0;
        System.out.println("AVLTree 1");
        bt = new AVLTree2<Integer>(new MyIntComparator());
        bt.add(1,blockId++);
        bt.add(10,blockId++);
        bt.add(15,blockId++);
        bt.add(16,blockId++);
        bt.add(12,blockId++);
        bt.add(14,blockId++);
        bt.add(13,blockId++);
        bt.add(9,blockId++);
        bt.printTree();
        System.out.println("BALANCE FACTOR = " + bt.balanceFactor());
        System.out.println("HEIGHT = " + bt.height());

        System.out.println("\n\nAVLTree 2");
        t = new AVLTree2<Integer>(new MyIntComparator());
        for(int  i =0; i < 8; i++){
            t.add(i,i);
        }
        t.printTree();
        System.out.println("BALANCE FACTOR = " + t.balanceFactor());
        System.out.println("HEIGHT = " + t.height());
        System.out.println("TERMINO EL BEFORE\n\n");






    }

    @Test
    public void addNonExistingElementReturnTrue(){
        assertTrue(bt.add(11,9));
    }

    @Test
    public void addExistingElementReturnFalse(){
        assertFalse(bt.add(16,9));
    }

    @Test
    public void addNullReturnFalse(){
        assertFalse(bt.add(null,9));
    }

    @Test
    public void removeNonExistingElementReturnFalse(){
        assertFalse(bt.remove(25,9));
        bt.printTree();
        System.out.println("BALANCE FACTOR = " + bt.balanceFactor());
        System.out.println("HEIGHT = " + bt.height());

    }

    @Test
    public void removeExistingElementReturnTrue(){
        assertTrue(bt.remove(16,9));
        bt.printTree();
        System.out.println("BALANCE FACTOR = " + bt.balanceFactor());
        System.out.println("HEIGHT = " + bt.height());
    }

    @Test
    public void removeRootReturnTrue(){
        assertTrue(bt.remove(12,9));
        System.out.println("size = " + bt.size());
        bt.printTree();
        System.out.println("BALANCE FACTOR = " + bt.balanceFactor());
        System.out.println("HEIGHT = " + bt.height());

    }

    @Test
    public void removeAtLeftStayBalance(){
        assertTrue(t.remove(4,8));
        t.printTree();
        System.out.println("BALANCE FACTOR = " + t.balanceFactor());
        System.out.println("HEIGHT = " + t.height());
    }




}
