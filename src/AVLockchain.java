import Blockchain.TreeChain;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Created by Bensas on 10/7/17.
 */
public class AVLockchain {

    public static void main(String args[]) throws NoSuchAlgorithmException{
        TreeChain chain = loadChainFromFile("tree_chain.eda");
        if (chain == null){
            chain = new TreeChain(Integer.parseInt(args[1])); //TODO ver esto @Juan
            System.out.println("A new blockhain has been created.");
        }
        //chain.setZeroes(Integer.parseInt(args[1]));
        System.out.println("Your tree looks like this: ");
        chain.getBalance().printTree();

        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("Please enter a command below (enter \"exit\" to end the program):");
        while (!(input = scanner.nextLine()).equals("exit")){
            processInput(input, chain);
        }
        saveChainToFile(chain, "tree_chain.eda");
        System.out.println("Exiting program. Bye!");
        System.exit(0);
    }

    public static void processInput(String input, TreeChain chain) throws NoSuchAlgorithmException{
        System.out.println("Please enter a command below (enter \"exit\" to end the program):");
        if (input.contains("add")){
            if (chain.add(Integer.parseInt(input.split("add ")[1])))
                System.out.println(Integer.parseInt(input.split("add ")[1]) + " added successfully!");
            else
                System.out.println("There was an error adding the element to the tree.");
        }
        if (input.contains("remove")){
            if (chain.remove(Integer.parseInt(input.split("remove ")[1])))
                System.out.println(Integer.parseInt(input.split("remove ")[1]) + " removed successfully!");
            else
                System.out.println("There was an error removing the element from the tree.");
        }
        else if (input.contains("lookup")){
            if (chain.lookup(Integer.parseInt(input.split("lookup ")[1])))
                System.out.println(Integer.parseInt(input.split("lookup ")[1]) + " was found!");
            else
                System.out.println(Integer.parseInt(input.split("lookup ")[1]) + " was not found. :(");
        }
        else if (input.contains("validate")){
            if (!chain.validate())
                System.out.println("Blockchain is invalid. :(");
            else
                System.out.println("Blockchain is valid!");
        }
        else if (input.contains("print")){
            chain.getBalance().printTree();
        }
    }

    public static TreeChain loadChainFromFile(String fileName){
        TreeChain chain = null;
        FileInputStream fin;
        ObjectInputStream objectinputstream;
        try {
            fin = new FileInputStream(fileName);
            objectinputstream = new ObjectInputStream(fin);
            chain = (TreeChain) objectinputstream.readObject();
            System.out.println("The blockchain was loaded successfully.");
        } catch (FileNotFoundException e){
            System.out.println("No blockchain file found.");
        } catch (Exception e) {
            System.out.println("The blockchain file has been corrupted and thus cannot be loaded.");
        }
        return chain;
    }

    public static void saveChainToFile(TreeChain chain, String fileName){
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try{
            fout = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(chain);
            System.out.println("The blockchain was saved successfully.");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
