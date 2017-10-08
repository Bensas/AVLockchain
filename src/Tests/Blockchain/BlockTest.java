package Tests.Blockchain;

import Blockchain.Block;
import Blockchain.TreeChain;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.security.NoSuchAlgorithmException;
/*
*   Ver si encontramos algo que valga la pena testearle
*   al bloque porque sino se va
 */
@Deprecated
public class BlockTest {
    private static final int ZEROS=5;
    private static final String HASHINGALG = "MD5";

    private Block dummyBlock1;
    private TreeChain blockchain;

    @Before
    public void Before() throws NoSuchAlgorithmException{
        blockchain = new TreeChain(ZEROS);
        blockchain.add(32);
        dummyBlock1 = blockchain.getLast();

    }

   /* @Test
    public void operationAddIncrementsSize(){

    }*/
}
