package Tests.Blockchain;

import Blockchain.Block;
import Blockchain.TreeChain;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.security.NoSuchAlgorithmException;

public class BlockTest {

    private static final int ZEROS=5;
    private static final String HASHINGALG = "MD5";

    private Block dummyBlock1;
    //private Block dummyBlock2;
    private TreeChain blockchain;

    @Before
    public void Before() throws NoSuchAlgorithmException{
        blockchain = new TreeChain(ZEROS);
        blockchain.add(32);
        dummyBlock1 = blockchain.getLast();
       /* blockchain.add(55);
        dummyBlock2 = blockchain.getLast();*/

    }


    @Test
    public void proofOfWorkTimeTest() throws NoSuchAlgorithmException{
        blockchain.mine(dummyBlock1, TreeChain.ENCFUNCTION, blockchain.getZeroes());
        Assert.assertEquals(1,1);
    }

   /* @Test
    public void operationAddIncrementsSize(){

    }*/
}
