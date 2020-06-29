package graphgame.cell;

import java.util.ArrayList;

public abstract class AbstractCellFactory {
    public abstract Cell createCell();

    public ArrayList<Cell> createCells(int number){
        ArrayList<Cell> cells = new ArrayList<>();
        for(int i=0; i<number; ++i){
            cells.add(createCell());
        }
        return cells;
    }
}
