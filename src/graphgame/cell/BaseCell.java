package graphgame.cell;

import graphgame.GephiAdaptor;

class BaseCell implements Cell {
    private long nodeID;

    BaseCell(){
        this.nodeID = GephiAdaptor.getInstance().createNode();
    }

    @Override
    public long getNodeID() {
        return nodeID;
    }
}
