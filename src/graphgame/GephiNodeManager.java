package graphgame;

import org.gephi.graph.api.Node;

import java.util.HashMap;

class GephiNodeManager {
    private long currentId = 0;
    private HashMap<Long, Node> index = new HashMap<>();

    long createNode(){
        Node newNode = GephiAdaptor.graphModel.factory().newNode(String.valueOf(currentId));
        GephiAdaptor.graph.addNode(newNode);
        index.put(currentId, newNode);
        return currentId++;
    }

    Node getNode(long id){
        return index.get(id);
    }
}
