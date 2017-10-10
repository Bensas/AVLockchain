package Blockchain;
import AVLTree.*;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TreeChain implements Serializable{
    private static final String FIRSTHASH="00000000000000000000000000000000";
    public static final String ENCFUNCTION = "MD5";
    //  puesto que los bloques contienen transacciones, el primer bloque debe ser null. Poner un bloque que
    //  indique el árbol vacío sería erróneo puesto que ninguna transacción (operación sobre el árbol) fue
    //  realizada aun.
    private Block last= null;
    private AVLTree balance;
    private int size = 0;
    private int zeroes;

    public TreeChain(int zeroes){
        this.zeroes = zeroes;
        if (balance == null) balance = new AVLTree();
    }

    public boolean add(int element) throws NoSuchAlgorithmException{
        boolean result = balance.add(element,size);

        String operation;
        if(result)
            operation= "add";
        else
            operation= "!add";

        last = new Block(last, last==null?FIRSTHASH:last.getHash(), operation, balance.hashCode());
        generateHash(last,ENCFUNCTION); //Después de crear el bloque debe generar el hash
        size++;
        return result;
    }

    public boolean remove(int element) throws NoSuchAlgorithmException{
        boolean result = balance.remove();
        String operation;
        if(result) {
            operation = "rmv";
        }
        else
            operation="!rmv";

        last= new Block(last, last==null?FIRSTHASH:last.getHash(), operation, balance.hashCode());
        size++;
        return result;
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

    public void mine(Block block,String alg,int zeros) throws NoSuchAlgorithmException{
        char[] hashChar = block.getHash().toCharArray();
        while (!miningCondition(hashChar,zeros)){
            block.incrementNonce();
            generateHash(block,alg);
            //System.out.println(hash);
            hashChar = block.getHash().toCharArray();

        }

    }

    private boolean miningCondition(char[] hash, int zeros){
        for (int i=0; i<=zeros; i++){
            if(hash[i]!=48) return false;
        }
        return true;
    }

    public AVLTree getBalance() {
        return balance;
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
    public void setZeroes(int zeroes){
        this.zeroes = zeroes;
    }

    public int getZeroes(){
        return zeroes;
    }

}
