package Blockchain;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

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
    public class Block {
        private int index;
        private int tree;
        private Block prevBlock;
        private String operation;
        private int nonce;
        private String hash;
        private String previousHash;

        public Block(Block prevBlock, String previousHash, String operation, int tree){
            this.index= (prevBlock == null?0:prevBlock.getIndex());
            this.previousHash = previousHash;
            this.operation = operation;
            this.tree = tree;
            this.nonce= 0;
        }

        /**
         *  Genera un hash mediante a la función SHA-256 en base al
         *  indice, el nonce y el previous hash del objecto Block instanciado.
         *  @throws NoSuchAlgorithmException
         */
        private String generateHash(String alg) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance(alg);
            String data = this.index + this.previousHash + this.nonce;
            md.update(data.getBytes());
            byte[] digest = md.digest();
            //convertir los bytes a formato hexa
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        }

        public void mine(String alg,int zeros) throws NoSuchAlgorithmException{
            char[] hashChar = hash.toCharArray();
            while (!miningCondition(hashChar,zeros)){
                this.nonce+=1;
                this.hash=generateHash(alg);
                //System.out.println(hash);
                hashChar = hash.toCharArray();

            }

        }

        private boolean miningCondition(char[] hash, int zeros){
            for (int i=0; i<=zeros; i++){
                if(hash[i]!=48) return false;
            }
            return true;
        }


        //getters and setters



        public int getIndex(){
            return index;
        }

        public int getTree(){
            return tree;
        }

        public String getOperation(){
            return operation;
        }

        public Block getPrevBlock(){
            return prevBlock;
        }

        // Para los tests
        public String setHash(String alg)throws NoSuchAlgorithmException{
            this.hash=generateHash(alg);
            return hash;
        }
        //getter agregado por josé, se necesita un getter del hash para cuando creas un bloque
        //nuevo para pasar como previousHash

        public String getHash() {
            return hash;
        }

        //getter agregado por José, se necesita un getter del previous hash para checkear si es válida
        //la cadena. Aunque, si uno lo quiere pensar desde el punto de vista objetoso, puede ser mejor que
        //sean los bloques en si los que se fijan si ellos mismos son válidos. Cosas para debatirse.
        public String getPreviousHash() {
            return previousHash;
        }
    }

