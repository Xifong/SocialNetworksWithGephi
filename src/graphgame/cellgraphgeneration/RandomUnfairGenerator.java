package graphgame.cellgraphgeneration;

import graphgame.cell.CellFactory;
import graphgame.cell.Cell;
import graphgame.cell.CellFactoryProducer;
import graphgame.cell.CellFactoryProducer.FactoryType;

public class RandomUnfairGenerator extends AbstractCellGraphGenerator{
    public static class Factory{
        public static RandomUnfairGenerator getGenerator(int vertices){
            return new RandomUnfairGenerator(vertices);
        }
    }

    private CellFactory cellFactory = CellFactoryProducer.getFactory(FactoryType.STANDARD);
    private int vertices;

    private RandomUnfairGenerator(int vertices){
        this.vertices = vertices;
    }

    @Override
    public void generate() {
        reset();
        for(int i=0; i<vertices; ++i){
            Cell newCell = cellFactory.createCell();
            cells.addCell(newCell);
            makeNonRepeatConnectionFor(newCell);
        }
    }
}
