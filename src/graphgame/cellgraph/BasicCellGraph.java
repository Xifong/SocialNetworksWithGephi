package graphgame.cellgraph;

import graphgame.GephiAdaptor;
import graphgame.cell.Cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BasicCellGraph implements  CellGraph {
    private List<Cell> cells;

    public BasicCellGraph(){
        cells = new ArrayList<>();
    }

    @Override
    public void addCell(Cell toAdd){
        cells.add(toAdd);
    }

    public void connectCells(Cell source, Cell dest){
        GephiAdaptor.getInstance().connectNodes(source.getNodeID(), dest.getNodeID());
    }

    @Override
    public int size() {
        return cells.size();
    }

    @Override
    public Cell getCell(int id) {
        return cells.get(id);
    }

    @Override
    public Cell getRandomCell() {
        int randomIndex = (int) Math.floor(Math.random() * cells.size());
        return cells.get(randomIndex);
    }

    @Override
    public Cell getRandomUnconnectedCell() {
        return null;
    }

    @Override
    public Iterator<Cell> iterator() {
        return cells.iterator();
    }
}
