package graphgame.cell;

import java.util.HashMap;

abstract public class CellDecoratorBase implements Cell {
    private Cell innerCell;

    CellDecoratorBase(Cell innerCell){
        this.innerCell = innerCell;
    }

    @Override
    public int getNodeID(){
        return innerCell.getNodeID();
    }

    @Override
    public boolean willAssociate(Cell cell){
        return innerCell.willAssociate(cell);
    }

    @Override
    public HashMap<String, Double> getAttributes(){
        return innerCell.getAttributes();
    }
}
