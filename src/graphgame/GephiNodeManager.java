package graphgame;

import org.gephi.graph.api.Node;

class GephiNodeManager {
    private int currentId = 0;

    int createNode(){
        Node newNode = GephiAdaptor.graphModel.factory().newNode(String.valueOf(currentId));
        GephiAdaptor.graph.addNode(newNode);
        return currentId++;
    }

    Node getNode(int id){
        return GephiAdaptor.graphModel.getGraph().getNode(String.valueOf(id));
    }
}
