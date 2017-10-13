package Tests.AVLTree2;
import AVLTree2.AVLTree2;
import Blockchain.Block;
import Blockchain.TreeChain;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class AVLTree2Test
{

    private AVLTree2<Integer> bt;
    private Block b;


    class MyIntComparator implements Comparator<Integer>{
        public int compare(Integer a,Integer b){
            return a-b;
        }
    }
    @Before
    public void Before(){
        bt = new AVLTree2<Integer>(new MyIntComparator());
        b = new Block(null,TreeChain.FIRSTHASH,"add",bt.hashCode());
        bt.add(1,b.getIndex());
        bt.add(10,b.getIndex());
        bt.add(15,b.getIndex());
        bt.add(16,b.getIndex());
        bt.add(12,b.getIndex());
        bt.add(14,b.getIndex());
        bt.add(13,b.getIndex());
        bt.add(9,b.getIndex());
        bt.printTree();
        System.out.println("BALANCE FACTOR = " + bt.balanceFactor());
        System.out.println("HEIGHT = " + bt.height());
        System.out.println("TERMINO EL BEFORE\n\n");


    }

    @Test
    public void addNonExistingElementReturnTrue(){
        assertTrue(bt.add(11,b.getIndex()));
    }

    @Test
    public void addExistingElementReturnFalse(){
        assertFalse(bt.add(16,b.getIndex()));
    }

    @Test
    public void addNullReturnFalse(){
        assertFalse(bt.add(null,b.getIndex()));
    }

    @Test
    public void removeNonExistingElementReturnFalse(){
        assertFalse(bt.remove(25,b.getIndex()));
        bt.printTree();
        System.out.println("BALANCE FACTOR = " + bt.balanceFactor());
        System.out.println("HEIGHT = " + bt.height());

    }

    @Test
    public void removeExistingElementReturnTrue(){
        assertTrue(bt.remove(16,b.getIndex()));
        bt.printTree();
        System.out.println("BALANCE FACTOR = " + bt.balanceFactor());
        System.out.println("HEIGHT = " + bt.height());
    }

    @Test
    public void removeRootReturnTrue(){
        assertTrue(bt.remove(12,b.getIndex()));
        System.out.println("size = " + bt.size());
        bt.printTree();
        System.out.println("BALANCE FACTOR = " + bt.balanceFactor());
        System.out.println("HEIGHT = " + bt.height());

    }



}
