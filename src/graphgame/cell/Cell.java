package graphgame.cell;

import java.util.HashMap;

public interface Cell {
    int getNodeID();

    default boolean willAssociate(Cell cell){
        return true;
    }

    default HashMap<String, Double> getAttributes(){
        return new HashMap<>();
    }
}
