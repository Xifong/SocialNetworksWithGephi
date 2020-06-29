package graphgame.cellgraphgeneration;

import graphgame.GephiAdaptor;
import graphgame.cell.CellFactory;
import graphgame.cell.Cell;
import graphgame.cell.CellFactoryProducer;
import graphgame.cellgraph.ListCellGraph;
import graphgame.cellgraph.CellGraph;

abstract class AbstractCellGraphGenerator implements CellGraphGenerator{
    protected CellGraph cells;

    @Override
    public CellGraph getCellGraph() {
        return cells;
    }

    protected void reset(){
        GephiAdaptor.getInstance().resetGraph();
        cells = new ListCellGraph();
    }

    public void iterateRandomConnections(int iterations){
        for(int i = 0; i <iterations; ++i) {
            for(Cell cell: cells){
                makeNonRepeatConnectionFor(cell);
            }
        }
    }

    protected void makeNonRepeatConnectionFor(Cell cell){
        Cell toConnectTo = findConnectionFor(cell);
        if(toConnectTo != null){
            cells.connectCells(cell, toConnectTo);
        }
    }

    public void iterateRandomAAssociations(int iterations){
        for(int i = 0; i <iterations; ++i) {
            for(Cell cell: cells){
                makeAssociationFor(cell);
            }
        }
    }

    private void makeAssociationFor(Cell cell){
        Cell toConnectTo = findConnectionFor(cell);
        if(toConnectTo != null && cell.willAssociate(toConnectTo) && toConnectTo.willAssociate(cell)){
            cells.connectCells(cell, toConnectTo);
        }
    }

    private Cell findConnectionFor(Cell cell){
        if(!cells.isComplete()){
            Cell toConnectTo = cells.getRandomCell();
            while(toConnectTo == cell || cells.areConnected(cell, toConnectTo))
                toConnectTo = cells.getRandomCell();
            return toConnectTo;
        }
        return null;
    }
}
