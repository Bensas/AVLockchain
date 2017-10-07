package Tests.Blockchain;

import Blockchain.TreeChain;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TreeChainTest {
    TreeChain chain1;
    @Before
    public void Before(){
        chain1 = new TreeChain();
    }

    @Test
    public void addModifiesSize(){
        chain1.add(2);
        assertEquals(1,chain1.getSize());
    }

    @Test
    public void removeModifiesSize(){
        chain1.add(2);
        int size = chain1.getSize();
        chain1.add(3);
        chain1.remove(3);
        assertEquals(size-1, chain1.getSize());
    }

    @Test
    public void removeEmptyChainReturnsFalse(){
        assertFalse(chain1.remove(2));
    }
}
