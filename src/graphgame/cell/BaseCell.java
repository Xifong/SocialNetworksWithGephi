package graphgame.cell;

import graphgame.GephiAdaptor;

class BaseCell implements Cell {
    private int nodeID;

    BaseCell(){
        this.nodeID = GephiAdaptor.getInstance().createNode();
    }

    BaseCell(int nodeID){
        this.nodeID = nodeID;
    }

    @Override
    public int getNodeID() {
        return nodeID;
    }
}
