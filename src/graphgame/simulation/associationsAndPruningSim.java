package graphgame.simulation;

import graphgame.cell.Cell;
import graphgame.cellgraph.CellGraph;

public class associationsAndPruningSim extends AbstractSim {

    public associationsAndPruningSim(CellGraph cells){
        super(cells);
    }

    @Override
    public void iterate() {
        makeNewAssociations();
        pruneUnhappyAssociations();
    }

    private void makeNewAssociations() {
        for (Cell cell : cells) {
            Cell found = cells.getRandomNonRepeatCell(cell);
            if (cell.willAssociate(found))
                cells.connect(cell, found);
        }
    }

    private void pruneUnhappyAssociations() {
        /*
        for(Cell cell : cells){
            Cell toPrune = cell.pruneAssociations();
            cells.disconnect(cell, toPrune);
        }*/
    }
}
