package graphgame.cellgraph;

import graphgame.cell.AbstractCellFactory;
import graphgame.cell.Cell;
import graphgame.cell.CellFactoryProducer;

public class UnfairCellGraphGenerator extends AbstractCellGraphGenerator{
    @Override
    public void generate(int vertexCount) {
        reset();
        AbstractCellFactory cellFactory = CellFactoryProducer.getFactory("STANDARD");
        for(int i=0; i<vertexCount; ++i){
            Cell newCell = cellFactory.createCell();
            cells.addCell(newCell);
            makeNonRepeatConnectionFor(newCell);
        }
    }
}
