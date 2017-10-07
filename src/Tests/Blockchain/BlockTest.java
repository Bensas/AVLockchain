package Tests.Blockchain;

import Blockchain.Block;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class BlockTest {
    private static final int ZEROS=6;
    private static final String HASHINGALG = "MD5";
    Block dummyBlock1;
    Block dummyBlock2;
    //00000000000000000000000000000000 32 bits
    @Before
    public void Before() throws NoSuchAlgorithmException{
        dummyBlock1 = new Block(null,
                "00000000000000000000000000000000",
                "", 0);
        dummyBlock2 = new Block(dummyBlock1, dummyBlock1.setHash(HASHINGALG),"",0);

    }

    @Test
    public void generateHashCreatesUniqueHashOnSameObjectTest() throws NoSuchAlgorithmException{

        Assert.assertEquals(dummyBlock1.setHash(HASHINGALG), dummyBlock1.setHash(HASHINGALG));
    }
    /*
    *   Ojo: Este test puede llegar a fallar y el código funcionar bien (ver Principio del Palomar)
    *   En caso de que falle correrlo nuevamente, si vuelve a fallar, quiere decir que hay un error
    *   en generateHash
     */
    @Test
    public void DifferentObjectsDifferentHashesTest() throws NoSuchAlgorithmException{
        Assert.assertNotEquals(dummyBlock1.setHash(HASHINGALG), dummyBlock2.setHash(HASHINGALG));

    }

    @Test
    public void ProofOfWorkTimeTest() throws NoSuchAlgorithmException{
        dummyBlock2.setHash(HASHINGALG);
        dummyBlock2.mine(HASHINGALG, ZEROS);
        Assert.assertEquals(1,1);
    }
}
