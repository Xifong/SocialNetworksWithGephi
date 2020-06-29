package graphgame.cellgraph;

import graphgame.GephiAdaptor;
import graphgame.cell.Cell;

public interface CellGraph extends Iterable<Cell>{
    void addCell(Cell toAdd);
    void connectCells(Cell source, Cell dest);
    int size();
    Cell getCell(int id);
    Cell getRandomCell();
    Cell getRandomUnconnectedCell();

    default boolean isComplete(){
        return GephiAdaptor.getInstance().isComplete();
}

    default boolean areConnected(Cell firstCell, Cell secondCell){
        return GephiAdaptor.getInstance().
                areNodesConnected(firstCell.getNodeID(), secondCell.getNodeID());
    }
}
