package graphgame.cellgraph;

import graphgame.cell.Cell;

public class FairCellGraphGenerator extends AbstractCellGraphGenerator {
    @Override
        public void generate(int vertexCount) {
        reset();
        for(int i=0; i<vertexCount; ++i){
            Cell newCell = cellFactory.createCell();
            cells.addCell(newCell);
        }
        iterateRandomAAssociations(8);
    }
}
