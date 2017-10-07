package AVLTree;
import java.util.ArrayList;
import java.util.LinkedList;

public class LevelPriorityQueue {

    private ArrayList<LinkedList<Integer>> levels;

    public void add(int element, int level){

        if(levels.size() < level){
            for(int i=levels.size(); i<level; i++){
                levels.add(new LinkedList<>());
            }
        }

        levels.get(level-1).add(element);
    }

    public Integer pop(int level){
        return levels.get(level).pop();
    }

    public boolean isEmpty(int level){
        //si esta vacio, eso quiere decir que ese nivel ya se imprimió del todo,
        //por lo que se lo puede vaciar
        if(levels.get(level).isEmpty()){

            levels.remove(level);
            return true;
        }
        return false;
    }
}

