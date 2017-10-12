package Blockchain;

import java.io.Serializable;
import java.util.Comparator;


public class IntegerComparator implements Comparator<Integer>, Serializable{

    public int compare(Integer a,Integer b){
        return a - b;
    }
}
