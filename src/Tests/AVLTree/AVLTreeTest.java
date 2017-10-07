package Tests.AVLTree;

import AVLTree.AVLTree;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class AVLTreeTest {
    AVLTree tree1;
    AVLTree tree2;

    @Before
    public void Before(){
        tree1 = new AVLTree();
        tree2 = new AVLTree();
        tree2.add(2,10); //TODO ver bien qué valores toma modifierBlock
        tree2.add(4,11);
        tree2.add(5,12);
        tree2.add(1,13);
    }

    @Test
    public void removeWithZeroElementsReturnsFalse(){
        assertFalse(tree1.remove());
    }

    @Test
    public void removeWithMoreThanOneElementReturnsTrue(){
        tree1.add(12,10);
        assertTrue(tree1.remove()); // TODO hacer remove
    }

    @Test
    public void findExistingElement(){
        assertTrue(tree2.find(5));
    }

    @Test
    public void findNonExistingElementReturnsFalse(){
        assertFalse(tree2.find(120));
    }

    //TODO Ver que agregar los modifierBlock random no me rompan todo
    @Test
    public void addExistingElementReturnsFalse(){
        assertFalse(tree2.add(2,2));
    }
    @Test
    public void addNotExistingElementReturnsTrue(){
        assertTrue(tree2.add(124,3));
    }
}
