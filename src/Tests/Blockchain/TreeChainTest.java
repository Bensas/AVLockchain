package Tests.Blockchain;

import Blockchain.Block;
import Blockchain.TreeChain;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TreeChainTest {
    private TreeChain chain1;
    private Block dummyBlock1;
    @Before
    public void Before() throws NoSuchAlgorithmException{
        chain1 = new TreeChain(5);
        chain1.add(23);
        dummyBlock1 = chain1.getLast();
    }

    @Test
    public void addModifiesSize() throws NoSuchAlgorithmException {
        chain1.add(2);
        assertEquals(1,chain1.getSize());
    }

    @Test
    public void removeModifiesSize() throws NoSuchAlgorithmException {
        chain1.add(2);
        int size = chain1.getSize();
        chain1.add(3);
        chain1.remove(3);
        assertEquals(size-1, chain1.getSize());
    }

    @Test
    public void removeEmptyChainReturnsFalse() throws NoSuchAlgorithmException{
        assertFalse(chain1.remove(2));
    }

    @Test
    public void proofOfWorkTimeTest() throws NoSuchAlgorithmException{
        chain1.mine(dummyBlock1, TreeChain.ENCFUNCTION, chain1.getZeroes());
        Assert.assertEquals(1,1);
    }


}
