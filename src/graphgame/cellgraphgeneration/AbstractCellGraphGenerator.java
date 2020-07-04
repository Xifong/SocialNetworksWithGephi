package graphgame.cellgraphgeneration;

import graphgame.GephiAdaptor;
import graphgame.cell.Cell;
import graphgame.cellgraph.ListCellGraph;
import graphgame.cellgraph.CellGraph;

abstract class AbstractCellGraphGenerator implements CellGraphGenerator{
    CellGraph cells;

    @Override
    public CellGraph getCellGraph() {
        return cells;
    }

    void reset(){
        GephiAdaptor.getInstance().resetGraph();
        resetCellsOnly();
    }

    void resetCellsOnly(){
        cells = new ListCellGraph();
    }

    public void iterateRandomConnections(int iterations){
        for(int i = 0; i <iterations; ++i) {
            for(Cell cell: cells){
                makeNonRepeatConnectionFor(cell);
            }
        }
    }

    void makeNonRepeatConnectionFor(Cell cell){
        Cell toConnectTo = cells.getRandomNonRepeatCell(cell);
        if(toConnectTo != null){
            cells.connect(cell, toConnectTo);
        }
    }

    void iterateRandomAssociations(int iterations){
        for(int i = 0; i <iterations; ++i) {
            for(Cell cell: cells){
                makeAssociationFor(cell);
            }
        }
    }

    private void makeAssociationFor(Cell cell){
        Cell toConnectTo = cells.getRandomNonRepeatCell(cell);
        if(toConnectTo != null && cell.willAssociate(toConnectTo) && toConnectTo.willAssociate(cell)){
            cells.connect(cell, toConnectTo);
        }
    }
}
