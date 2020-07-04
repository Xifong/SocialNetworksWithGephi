package graphgame.simulation;

import graphgame.cellgraph.CellGraph;

abstract class AbstractSim implements  Simulation{
    CellGraph cells;

    AbstractSim(CellGraph cells){
        this.cells = cells;
    }

    @Override
    public CellGraph getState(){
        return  cells;
    }
}
