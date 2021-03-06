package Blockchain;
import AVLTree.AVLTree;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * The blockchain
 */
public class TreeChain implements Serializable {
    // Importante!: Agregar al final de FIRSTHASH 32 ceros más
    // si se va a cambiar la ENCFUNCTION a SHA-256
    public static final String FIRSTHASH = "00000000000000000000000000000000";
    public static final String ENCFUNCTION = "MD5";
    /*  Puesto que los bloques contienen transacciones, el primer bloque debe ser null.
    *   Poner un bloque que indique el árbol vacío sería erróneo puesto que
    *   ninguna transacción (operación sobre el árbol) fue realizada aun.
    */
    private Block last = null;
    private AVLTree<Integer> balance;
    private int size = 0;
    private int zeroes;
    private int lastIndex = 0;

    /**
     * TreeChain's Constructor, simulating a Blockchain.
     *
     * @param zeroes amount of zeros wanted in the hash.
     */
    public TreeChain(int zeroes) {
        this.zeroes = zeroes;
        if (balance == null) balance = new AVLTree(new IntegerComparator());
    }

    /**
     * Manually sets the hash value of a specified block.
     *
     * @param blockID ID of the block that will be modified.
     * @param newHash number to be assigned as the hash for the block.
     * @return true if the hash was successfully assigned, false if no block with matching ID was found.
     */
    public boolean modifyHash(int blockID, String newHash){
        Block current = last;
        while (current.getIndex() != blockID){
            current = current.getPrevBlock();
            if (current == null)
                return false;
        }
        current.setHash(newHash);
        return true;
    }

    /**
     * Adds an elements to the blockchain
     * @param element wanted to be added in the avl tree.
     * @return true if the element was added to the AVL tree. False otherwise.
     * @throws NoSuchAlgorithmException
     */
    public boolean add(int element) throws NoSuchAlgorithmException {

        boolean result = balance.add(element,last==null?0:lastIndex+1);

        String operation;
        if (result)
            operation = "Add : " + element;
        else
            operation = "Failed at add : " + element;

        last = new Block(last, last == null ? FIRSTHASH : last.getHash(), operation, balance.hashCode());
        lastIndex = last.getIndex();
        generateHash(last, ENCFUNCTION); //Después de crear el bloque debe generar el hash
        System.out.println("Mining... this could take a while");
        long tStart = System.currentTimeMillis();
        mine(last,ENCFUNCTION,zeroes); //Se mina el hash para que el bloque sea válido
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        double elapsedSeconds = tDelta / 1000.0;
        System.out.println("Time elapsed: " + elapsedSeconds + " seconds.");
        size++;
        return result;
    }

    /**
     * Removes an element from the blockchain
     * @param element that is going to be removed.
     * @return true if it was succesfully removed from the tree, or false if there wasn't such element in the tree.
     */
    public boolean remove(int element) throws NoSuchAlgorithmException{
        boolean result = balance.remove(element,last==null?0:lastIndex+1);
        String operation;
        if(result) {
            operation = "Remove: " + element;
        }
        else
            operation="Failed at remove : " + element;

        last= new Block(last, last == null ? FIRSTHASH : last.getHash(), operation, balance.hashCode());
        lastIndex = last.getIndex();
        generateHash(last, ENCFUNCTION); //Después de crear el bloque debe generar el hash
        System.out.println("Mining... this could take a while");
        long tStart = System.currentTimeMillis();
        mine(last,ENCFUNCTION,zeroes); //Se mina el hash para que el bloque sea válido
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        double elapsedSeconds = tDelta / 1000.0;
        System.out.println("Time elapsed: " + elapsedSeconds + " seconds.");
        size++;
        return result;
    }

    /**
     *
     * @return current tree balance.
     */
    public AVLTree getBalance() {
        return balance;
    }

    /**
     * Checks the integrity of the blockchain
     * @return true if the blockchain is validated or false otherwise.
     */
    public boolean validate(){

        if(last==null)
            return true;
        Block current= last;
        Block previous= last.getPrevBlock();

        while(previous!=null){
            if(!current.getPreviousHash().equals(previous.getHash()))
                return false;
            current= previous;
            previous= current.getPrevBlock();
        }
        return true;
    }

    /**
     * Generates a hash based on the blocks' index, nonce and the previous block hash
     *  @param block the block where the hash will be saved
     *  @param alg the encryption algorithm that will generate the hash
     *  @throws NoSuchAlgorithmException
     */
    private void generateHash(Block block,String alg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(alg);
        String data = block.getIndex() + block.getPreviousHash() + block.getNonce();
        md.update(data.getBytes());
        byte[] digest = md.digest();
        //convertir los bytes a formato hexa
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }

        block.setHash(sb.toString());
    }

    /**
     * Tries hashes by brute force until it finds one that returns true when
     * passed as an argument in "miningCondtion()"
     *
     * @param block wanted to be mined
     * @param alg the algorithm saved in the block
     * @param zeros amount of zeros wanted in the hash
     * @throws NoSuchAlgorithmException
     */
    public void mine(Block block,String alg,int zeros) throws NoSuchAlgorithmException{
        char[] hashChar = block.getHash().toCharArray();
        while (!miningCondition(hashChar,zeros)){
            block.incrementNonce();
            generateHash(block,alg);
            //System.out.println(hash);
            hashChar = block.getHash().toCharArray();

        }

    }

    /**
     *
     * @param hash that is gonna be checked.
     * @param zeros amount of zeros wanted.
     * @return true if the hash starts with the correct amount of zeroes. False otherwise.
     */
    private boolean miningCondition(char[] hash, int zeros){
        for (int i=0; i<=zeros; i++){
            if(hash[i]!=48) return false;
        }
        return true;
    }
    //A la noche lo termino (soy nacho negro)
    public String lookup(int num) throws NoSuchAlgorithmException{
        ArrayList<HashSet<Integer>> modifiers = balance.getModifiers(num);
        if (modifiers == null)  return null;
        String ret = new String("Element " + num +" found successfully\n"+ "The element was modified by the following blocks \n");
        String aux = new String();
        int i = 0;
        for (HashSet<Integer> set : modifiers){
            if(set.size() > 0){
                for (int blockId: set){
                    aux += blockId + " ";
                }

                switch (i){
                    case 0:{
                        aux = "Modified by add: " + aux + "\n";
                        break;
                    }
                    case 1:{
                        aux = "Modified by remove: " + aux + "\n";
                        break;
                    }
                    case 2:{
                        aux = "Modified by rotation: " + aux + "\n";
                        break;
                    }
                }
                ret += aux;
                aux = new String();
            }
            i++;
        }

        last = new Block(last, last == null ? FIRSTHASH : last.getHash(), ret==null?"lookup "+num+" - false":"lookup "+num+" - true", balance.hashCode());
        lastIndex = last.getIndex();
        generateHash(last, ENCFUNCTION); //Después de crear el bloque debe generar el hash
        System.out.println("Mining... this could take a while");
        long tStart = System.currentTimeMillis();
        mine(last,ENCFUNCTION,zeroes); //Se mina el hash para que el bloque sea válido
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        double elapsedSeconds = tDelta / 1000.0;
        System.out.println("Time elapsed: " + elapsedSeconds + " seconds.");
        size++;
        return ret;
    }

    public int getSize(){
        return size;
    }

    public Block getLast(){
        return last;
    }

    /**
     *
     * @param zeroes amount wanted to be changed.
     */
    public void setZeroes(int zeroes){
        this.zeroes = zeroes;
    }

    /**
     *
     * @return the amount of zeros wanted.
     */
    public int getZeroes(){
        return zeroes;
    }

}
