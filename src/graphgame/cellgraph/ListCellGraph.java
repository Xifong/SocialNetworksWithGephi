package graphgame.cellgraph;

import graphgame.GephiAdaptor;
import graphgame.cell.Cell;
import graphgame.utilities.Shuffler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListCellGraph implements  CellGraph {
    private List<Cell> cells;

    public ListCellGraph(){
        cells = new ArrayList<>();
    }

    @Override
    public void addCell(Cell toAdd){
        cells.add(toAdd);
    }

    @Override
    public void connect(Cell a, Cell b){
        if(!GephiAdaptor.getInstance().areNodesConnected(a.getNodeID(), b.getNodeID()))
            GephiAdaptor.getInstance().connectNodes(a.getNodeID(), b.getNodeID());
    }

    @Override
    public void disconnect(Cell a, Cell b){
        if(GephiAdaptor.getInstance().areNodesConnected(a.getNodeID(), b.getNodeID()))
            GephiAdaptor.getInstance().disconnectNodes(a.getNodeID(), b.getNodeID());
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
    public Cell getRandomNonRepeatCell(Cell cell) {
        if(!isComplete()){
            Cell toConnectTo = getRandomCell();
            while(toConnectTo == cell || areConnected(cell, toConnectTo))
                toConnectTo = getRandomCell();
            return toConnectTo;
        }
        return null;
    }

    @Override
    public Iterator<Cell> iterator() {
        return cells.iterator();
    }

    @Override
    public Iterator<Cell> shuffledIterator() {
        List<Cell> shuffledCells = Shuffler.shuffle(cells);
        return shuffledCells.iterator();
    }
}
