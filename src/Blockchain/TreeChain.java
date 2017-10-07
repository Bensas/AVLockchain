package Blockchain;
import AVLTree.*;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TreeChain implements Serializable{

    //  puesto que los bloques contienen transacciones, el primer bloque debe ser null. Poner un bloque que
    //  indique el árbol vacío sería erróneo puesto que ninguna transacción (operación sobre el árbol) fue
    //  realizada aun.
    private Block last= null;
    private AVLTree balance= new AVLTree();
    private int size = 0;

    public boolean add(int element){
        boolean result = balance.add(element, size);

        String operation;
        if(result)
            operation= "add";
        else
            operation= "!add";

        //esto es porque en el momento me parece mejor guardar un hash que un árbol.
        if(last == null) last = new Block
                (last, "00000000000000000000000000000000", operation, balance.hashCode());
        else last = new Block(last, last.getHash(), operation, balance.hashCode());
        size++;
        return result;
    }

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
     *  Genera un hash mediante a la función SHA-256 en base al
     *  indice, el nonce y el previous hash del objecto Block instanciado.
     *  @throws NoSuchAlgorithmException
     */
    private String generateHash(Block block,String alg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(alg);
        String data = block.getIndex() + block.getPreviousHash() + block.getNonce();
        md.update(data.getBytes());
        byte[] digest = md.digest();
        //convertir los bytes a formato hexa
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public void mine(Block block,String alg,int zeros) throws NoSuchAlgorithmException{
        char[] hashChar = block.getHash().toCharArray();
        while (!miningCondition(hashChar,zeros)){
            block.incrementNonce();
            block.setHash(generateHash(block,alg));
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


    public boolean lookup(int num){
        return getBalance().find(num);
    }

    public int getSize(){
        return size;
    }

    public void setZeroes(int zeroes){

    }
}
