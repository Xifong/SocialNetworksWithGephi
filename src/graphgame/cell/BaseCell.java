package graphgame.cell;

import graphgame.GephiAdaptor;
import org.gephi.graph.api.*;

class BaseCell implements Cell {
    static class BaseCellFactory{
        private static int currentID = 0;
        static BaseCell createBasicCell(){
            return new BaseCell(GephiAdaptor.getInstance().addNewNode(currentID++));
        }
    }

    private Node gephiNode;

    private BaseCell(Node gephiNode){
        this.gephiNode = gephiNode;
    }

    @Override
    public Node getGephiNode() {
        return gephiNode;
    }
}
