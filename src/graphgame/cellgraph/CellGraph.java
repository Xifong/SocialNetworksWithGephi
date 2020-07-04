package graphgame.cellgraph;

import graphgame.GephiAdaptor;
import graphgame.cell.Cell;

import java.util.Iterator;

public interface CellGraph extends Iterable<Cell>{
    void addCell(Cell toAdd);
    void connect(Cell source, Cell dest);
    void disconnect(Cell source, Cell dest);
    int size();
    Cell getCell(int id);
    Cell getRandomCell();
    Cell getRandomNonRepeatCell(Cell cell);
    Iterator<Cell> shuffledIterator();

    default boolean isComplete(){
        int sumToGoTo = size() - 1;

        if(sumToGoTo <= 0){
            return true;
        }

        int edges = GephiAdaptor.getInstance().getEdgeCount();

        long currentfactorial = 1;

        while(edges >= currentfactorial && sumToGoTo > 1){
            currentfactorial += sumToGoTo--;
        }
        return edges >= currentfactorial;
    }

    default boolean areConnected(Cell firstCell, Cell secondCell){
        return GephiAdaptor.getInstance().
                areNodesConnected(firstCell.getNodeID(), secondCell.getNodeID());
    }
}
