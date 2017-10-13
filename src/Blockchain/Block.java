package Blockchain;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

    /**
     *  The blocks that make the blockchain. Each block has 4 elements:<br>
     *  <ul>
     *      <li> index         : It's position in the blockchain</li>
     *      <li> hash          : Number that makes the block unique, the blockchain
     *                          considers the block as valid if this number starts
     *                          with n zeros, where n is defined at the beginning of
     *                          the program.</li>
     *      <li> previousHash  : The hash of the block that has index=this.index-1. If
     *                           the block is the first block of the blockchain then
     *                           previousHash=00000000000000000000000000000000 (because
     *                           we are using a 32 digits encoding algorithm). </li>
     *      <li> nonce         : Starts at 0, it's the field that will be modified to
     *                           get a hash that starts with n zeros. </li>
     *  </ul>
     */
    public class Block implements Serializable{
        private int index;
        private int treeHash;
        private Block prevBlock;
        private String operation;
        private int nonce;
        private String hash;
        private String previousHash;

        /**
         *
         * Block's Constructor
         *
         * @param prevBlock previous Block in the BlockChain.
         * @param previousHash previous Block hash.
         * @param operation wanted to be saved in this Block.
         * @param tree avl tree hash.
         */
        public Block(Block prevBlock, String previousHash, String operation, int tree){
            this.index= (prevBlock == null?0:prevBlock.getIndex() + 1);
            this.treeHash = tree;
            this.prevBlock = prevBlock;
            this.previousHash = previousHash;
            this.operation = operation;
            this.nonce= 0;
        }


        //getters and setters

        /**
         *
         * @return current nonce value.
         */
        public int getNonce(){
            return nonce;
        }

        /**
         *
         * @return current index value.
         */
        public int getIndex(){
            return index;
        }

        /**
         *
         * @return current treeHash value.
         */
        public int getTreeHash(){
            return treeHash;
        }

        /**
         *
         * @return current operation.
         */
        public String getOperation(){
            return operation;
        }

        /**
         *
         * @return previous Block.
         */
        public Block getPrevBlock(){
            return prevBlock;
        }

        /**
         *
         * @param hash to set.
         */
        public void setHash(String hash){
            this.hash=hash;
        }

        /**
         * Increments nonce value by 1.
         */
        public void incrementNonce(){
            nonce+=1;
        }

        /**
         *
         * @return current hash.
         */
        public String getHash() {
            return hash;
        }

        /**
         *
         * @return previous hash.
         */
        public String getPreviousHash() {
            return previousHash;
        }

    }

