package Blockchain;
import AVLTree.*;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TreeChain implements Serializable {
    private static final String FIRSTHASH = "00000000000000000000000000000000";
    public static final String ENCFUNCTION = "MD5";
    //  puesto que los bloques contienen transacciones, el primer bloque debe ser null. Poner un bloque que
    //  indique el árbol vacío sería erróneo puesto que ninguna transacción (operación sobre el árbol) fue
    //  realizada aun.
    private Block last = null;
    private AVLTree balance;
    private int size = 0;
    private int zeroes;

    /**
     * TreeChain's Constructor, simulating a Blockchain.
     *
     * @param zeroes amount of zeros wanted in the hash.
     */
    public TreeChain(int zeroes) {
        this.zeroes = zeroes;
        if (balance == null) balance = new AVLTree();
    }

    /**
     * @param element wanted to be added in the avl tree.
     * @return true if the element was added to the AVL tree. False otherwise.
     * @throws NoSuchAlgorithmException
     */
    public boolean add(int element) throws NoSuchAlgorithmException {
        boolean result = balance.add(element, size);

        String operation;
        if (result)
            operation = "add";
        else
            operation = "!add";

        //esto es porque en el momento me parece mejor guardar un hash que un árbol.
        last = new Block(last, last == null ? FIRSTHASH : last.getHash(), operation, balance.hashCode());
        generateHash(last, ENCFUNCTION); //Después de crear el bloque debe generar el hash
        size++;
        return result;
    }

    /**
     * @param element that is going to be removed.
     * @return true if it was succesfully removed from the tree, or false if there wasn't such element in the tree.
     */
    public boolean remove(int element){
        boolean result = balance.remove();
        String operation;
        if(result) {
            operation = "rmv";
        }
        else
            operation="!rmv";

        last= new Block(last, last.getHash(), operation, balance.hashCode());
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

    /* TODO Para agregar cuando tengamos lo demás
    public AVLTree getBalanceAt(int index){

        if(index>=size || index<0)
            throw new NonExistantTransactionException();
        if(index== size - 1)
            return getBalance();
        Block current= last;
        while(index>0){
            current=current.getPrevBlock();
            index--;
        }
        return buildTree(current);

    }

    public AVLTree buildTree(Block last){

        Block current= last;
        AVLTree tree= new AVLTree();

    }
    */

    /**
     *
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
     *  Genera un hash mediante a la función especificada en el argumento alg en base al
     *  indice, el nonce y el previous hash del objecto Block instanciado.
     *  @param block el bloque en el que se va a generar el hash
     *  @param alg  El algoritmo de hashing a usar
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
     * Mine by brute force to get the hash of the block.
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
     * @return true if the hash has in teh beginning the amount of zeros wanted. False otherwise.
     */
    private boolean miningCondition(char[] hash, int zeros){
        for (int i=0; i<=zeros; i++){
            if(hash[i]!=48) return false;
        }
        return true;
    }


    public boolean lookup(int num){
        return getBalance().find(num);
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
