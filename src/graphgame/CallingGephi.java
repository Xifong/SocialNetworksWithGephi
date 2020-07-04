package graphgame;

import java.util.Scanner;

import graphgame.cellgraph.ListCellGraph;
import graphgame.cellgraph.CellGraph;
import graphgame.cellgraphgeneration.CellGraphGenerator;
import graphgame.cellgraphgeneration.AssociationBasedGenerator;
import graphgame.cellgraphgeneration.ImportGenerator;
import graphgame.simulation.Simulation;
import graphgame.simulation.associationsAndPruningSim;

public class CallingGephi {
    public static void main(String[] args){
        new CallingGephi().run();
    }
    private CellGraph cells;

    private CallingGephi(){
        cells = new ListCellGraph();
    }

    private void run(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("New world y/n?");
        String response = scanner.next();
        if (response.charAt(0) == 'n'){
            this.cells = loadWorld();
            this.cells = iterateWorld();
        }
        else
            this.cells = generateWorld();

        GephiAdaptor.getInstance().exportGraph();
    }

    private CellGraph generateWorld(){
        setupColumns();

        CellGraphGenerator graphGenerator =
                AssociationBasedGenerator.Factory.getGenerator(250, 8);
        graphGenerator.generate();
        return graphGenerator.getCellGraph();
    }

    private CellGraph loadWorld(){
        GephiAdaptor.getInstance().importGraph();
        CellGraphGenerator importGenerator = ImportGenerator.Factory.getGenerator();
        importGenerator.generate();
        return importGenerator.getCellGraph();
    }

    private CellGraph iterateWorld(){
        Simulation sim = new associationsAndPruningSim(cells);
        sim.iterate();
        return sim.getState();
    }

    private void setupColumns(){
        GephiAdaptor.getInstance().addNodeAttribute("Wealth", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Social", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Intelligence", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Looks", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Average", double.class);
    }
}