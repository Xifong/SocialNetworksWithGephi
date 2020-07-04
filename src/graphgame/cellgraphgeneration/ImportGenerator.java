package graphgame.cellgraphgeneration;

import graphgame.GephiAdaptor;
import graphgame.cell.CellFactory;
import graphgame.cell.CellFactoryProducer;
import org.openide.util.Pair;
import graphgame.cell.CellFactoryProducer.FactoryType;

import java.util.List;

public class ImportGenerator extends AbstractCellGraphGenerator {
    public static class Factory{
        public static ImportGenerator getGenerator(){
            return new ImportGenerator();
        }
    }

    @Override
    public void generate() {
        resetCellsOnly();
        CellFactory factory =  CellFactoryProducer.getFactory(FactoryType.STANDARD);

        List<Integer> IDs =  GephiAdaptor.getInstance().getNodeIDs();
        for(Integer ID: IDs){
            cells.addCell(factory.createCellForExistingNode(ID));
        }

        List<Pair<Integer, Integer>> adjacencies =  GephiAdaptor.getInstance().getIDAdjacencies();
        for(Pair<Integer, Integer> pair: adjacencies){
            cells.connect(cells.getCell(pair.first()), cells.getCell(pair.second()));
        }
    }
}
