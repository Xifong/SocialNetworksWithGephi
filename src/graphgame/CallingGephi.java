package graphgame;

import graphgame.cellgraph.ListCellGraph;
import graphgame.cellgraph.CellGraph;
import graphgame.cellgraphgeneration.CellGraphGenerator;
import graphgame.cellgraphgeneration.AssociationBasedGenerator;
import graphgame.cellgraphgeneration.ImportGenerator;

import java.util.Scanner;

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
            loadWorld();
            iterateWorld();
        }
        else
            generateWorld();
    }

    private void generateWorld(){
        setupColumns();

        CellGraphGenerator graphGenerator =
                AssociationBasedGenerator.Factory.getGenerator(250, 8);
        graphGenerator.generate();
        cells = graphGenerator.getCellGraph();

        GephiAdaptor.getInstance().exportGraph();
    }

    private void loadWorld(){
        GephiAdaptor.getInstance().importGraph();
        CellGraphGenerator importGenerator = ImportGenerator.Factory.getGenerator();
        importGenerator.generate();
        cells = importGenerator.getCellGraph();
    }

    private void iterateWorld(){
        System.out.println(GephiAdaptor.graph.getNodeCount());
        System.out.println(GephiAdaptor.graph.getEdgeCount());
        System.out.println(cells.size());
    }

    private void setupColumns(){
        GephiAdaptor.getInstance().addNodeAttribute("Wealth", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Social", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Intelligence", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Looks", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Average", double.class);
    }
}