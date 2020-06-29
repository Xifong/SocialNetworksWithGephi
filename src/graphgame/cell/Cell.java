package graphgame.cell;

import org.gephi.graph.api.Node;
import java.util.HashMap;

public interface Cell {
    Node getGephiNode();

    default boolean willAssociate(Cell cell){
        return true;
    }

    default HashMap<String, Double> getAttributes(){
        return new HashMap<>();
    }
}
