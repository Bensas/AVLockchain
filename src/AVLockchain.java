import Blockchain.TreeChain;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class AVLockchain {

    public static void main(String args[]) throws NoSuchAlgorithmException{
        int zeroes = 0;
        if (args.length == 0 || !args[0].equals("zeroes")){
            System.out.println("Please initialize the program with the parameter 'zeroes' followed by the number of zeroes hashes must start with. Exiting program.");
            System.exit(1);
        }
        try{
            zeroes = Integer.parseInt(args[1]);
        } catch (Exception e){
            System.out.println("The number of zeroes specified is not valid. Exiting program.");
            System.exit(1);
        }

        TreeChain chain = loadChainFromFile("tree_chain.eda");
        if (chain == null){
            chain = new TreeChain(zeroes);
            System.out.println("A new blockhain has been created.");
        }

        chain.setZeroes(zeroes);

        System.out.println("Your tree looks like this: ");
        chain.getBalance().printTree();

        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("Please enter a command below (enter \"exit\" to end the program and save the chain):");
        while (!(input = scanner.nextLine()).equals("exit")){
            try{
                processInput(input, chain);
                System.out.println("Please enter the next command below:");
            } catch (Exception e){
                System.out.println("There is a problem with the input format.");
            }
        }
        saveChainToFile(chain, "tree_chain.eda");
        System.out.println("Exiting program. Bye!");
        System.exit(0);
    }

    public static void processInput(String input, TreeChain chain) throws NoSuchAlgorithmException, NumberFormatException{
        if (input.contains("add")){
            if (chain.add(Integer.parseInt(input.split("add ")[1])))
                System.out.println(Integer.parseInt(input.split("add ")[1]) + " added successfully!");
            else
                System.out.println("There was an error adding the element to the tree. Make sure that it hasn't already been added.");
        }
        else if (input.contains("remove")){
            if (chain.remove(Integer.parseInt(input.split("remove ")[1])))
                System.out.println(Integer.parseInt(input.split("remove ")[1]) + " removed successfully!");
            else
                System.out.println("There was an error removing the element from the tree.");
        }
        else if (input.contains("lookup")){
            String result = chain.lookup(Integer.parseInt(input.split("lookup ")[1]));
            if (result == null)
                System.out.println(Integer.parseInt(input.split("lookup ")[1]) + " was not found. :(");
            else
                System.out.println(result);
            //dsa
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
        else if (input.contains("modify")){
            modifyBlockHashFromFile(input.split("modify ")[1], chain);
        }
        else {
            System.out.println("The command is invalid.");
        }
    }

    private static void modifyBlockHashFromFile(String fileName, TreeChain chain){
        int blockID = 0;
        String newHash = null;

        String inputLn;
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(fileName));
            while ((inputLn = br.readLine()) != null){
                    try{
                        if (inputLn.contains("Block ID: ")){
                            blockID = Integer.parseInt(inputLn.split("Block ID: ")[1]);
                        } else if (inputLn.contains("New Hash: ")){
                            newHash = inputLn.split("New Hash: ")[1];
                        } else {
                            System.out.println("The mod file has an invalid format.");
                        }
                    } catch (NumberFormatException e){
                        System.out.println("The mod file has an invalid format.");
                    }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        chain.modifyHash(blockID, newHash);

    }

    private static TreeChain loadChainFromFile(String fileName){
        TreeChain chain = null;
        FileInputStream fin = null;
        ObjectInputStream oin = null;
        try {
            fin = new FileInputStream(fileName);
            oin = new ObjectInputStream(fin);
            chain = (TreeChain) oin.readObject();
            System.out.println("The blockchain was loaded successfully.");
        } catch (FileNotFoundException e){
            System.out.println("No blockchain file found.");
        } catch (Exception e) {
            System.out.println("The blockchain file has been corrupted and thus cannot be loaded.");
        }
        return chain;
    }

    private static void saveChainToFile(TreeChain chain, String fileName){
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
