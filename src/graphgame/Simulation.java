package graphgame;

import graphgame.cellgraph.CellGraph;
import graphgame.cellgraph.ListCellGraph;
import graphgame.cellgraphgeneration.AssociationBasedGenerator;
import graphgame.cellgraphgeneration.CellGraphGenerator;
import graphgame.cellgraphgeneration.ImportGenerator;
import graphgame.simulation.associationsAndPruningSim;
import graphgame.utilities.Observer;
import graphgame.view.TextBasedView;

public class Simulation implements Observer {
    private CellGraph cells;

    Simulation() {
        cells = new ListCellGraph();
    }

    @Override
    public void notify(Enum state) {
        switch ((TextBasedView.SubjectState) state) {
            case NOTHING:
                break;
            case IMPORT:
                cells = loadWorld();
                System.out.println("World Loaded");
                break;
            case GENERATE:
                cells = generateWorld();
                System.out.println("World Generated");
                break;
            case ITERATE:
                cells = iterateWorld();
                System.out.println("World Iterated");
                break;
            case SAVEEXIT:
                saveGraph();
                System.out.println("Graph Saved. Now Exiting...");
                exit();
                break;
            case EXIT:
                exit();
        }
    }

    private CellGraph generateWorld() {
        setupColumns();

        CellGraphGenerator graphGenerator =
                AssociationBasedGenerator.Factory.getGenerator(250, 8);
        graphGenerator.generate();
        return graphGenerator.getCellGraph();
    }

    private CellGraph loadWorld() {
        GephiAdaptor.getInstance().importGraph();
        CellGraphGenerator importGenerator = ImportGenerator.Factory.getGenerator();
        importGenerator.generate();
        return importGenerator.getCellGraph();
    }

    private CellGraph iterateWorld() {
        graphgame.simulation.Simulation sim = new associationsAndPruningSim(cells);
        sim.iterate();
        return sim.getState();
    }

    private void setupColumns() {
        GephiAdaptor.getInstance().addNodeAttribute("Wealth", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Social", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Intelligence", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Looks", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Average", double.class);
    }

    private void saveGraph(){
        GephiAdaptor.getInstance().exportGraph();
    }

    private void exit(){
        System.exit(0);
    }
}
