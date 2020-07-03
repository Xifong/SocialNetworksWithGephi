package graphgame.cell;

import java.util.ArrayList;

public interface CellFactory {
    Cell createCell();
    Cell createCellForExistingNode(int nodeId);

    default ArrayList<Cell> createCells(int number){
        ArrayList<Cell> cells = new ArrayList<>();
        for(int i=0; i<number; ++i){
            cells.add(createCell());
        }
        return cells;
    }
}
