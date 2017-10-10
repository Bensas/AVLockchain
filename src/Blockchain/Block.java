package Blockchain;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

    /**
     *  Los bloques que constituyen la blockchain. Cada bloque consta
     *  de 4 elementos escenciales:
     *  - index         : Su posición en la blockchain
     *  - hash          : Código que para transformarse en un bloque válido
     *                    debe empezar con 4 ceros
     *  - previousHash  : El hash del bloque con indice = index-1 (en caso de
     *                    ser el primer bloque el previousHash es todos ceros)
     *  - nonce         : Número aleatorio al principio que se lo cambia para
     *                    hacer que el hash empiece con 4 ceros
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
        //getter agregado por josé, se necesita un getter del hash para cuando creas un bloque
        //nuevo para pasar como previousHash

        /**
         *
         * @return current hash.
         */
        public String getHash() {
            return hash;
        }

        //getter agregado por José, se necesita un getter del previous hash para checkear si es válida
        //la cadena. Aunque, si uno lo quiere pensar desde el punto de vista objetoso, puede ser mejor que
        //sean los bloques en si los que se fijan si ellos mismos son válidos. Cosas para debatirse.

        /**
         *
         * @return previous hash.
         */
        public String getPreviousHash() {
            return previousHash;
        }

    }

