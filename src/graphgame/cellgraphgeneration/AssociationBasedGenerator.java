package graphgame.cellgraphgeneration;

import graphgame.cell.Cell;
import graphgame.cell.CellFactory;
import graphgame.cell.CellFactoryProducer;
import graphgame.cell.CellFactoryProducer.FactoryType;

public class AssociationBasedGenerator extends AbstractCellGraphGenerator {
    public static class Factory{
        public static AssociationBasedGenerator getGenerator(int vertices, int edgeIterations){
            return new AssociationBasedGenerator(vertices, edgeIterations);
        }
    }

    private CellFactory cellFactory = CellFactoryProducer.getFactory(FactoryType.STANDARD);
    private int edgeIterations;
    private int vertices;

    private AssociationBasedGenerator(int vertices, int edgeIterations){
        this.edgeIterations = edgeIterations;
        this.vertices = vertices;
    }

    @Override
        public void generate() {
        reset();
        for(int i=0; i<vertices; ++i){
            Cell newCell = cellFactory.createCell();
            cells.addCell(newCell);
        }
        iterateRandomAssociations(edgeIterations);
    }
}
