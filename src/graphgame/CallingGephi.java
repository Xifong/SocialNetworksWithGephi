package graphgame;

import graphgame.cellgraph.BasicCellGraph;
import graphgame.cellgraph.CellGraph;
import graphgame.cellgraph.CellGraphGenerator;
import graphgame.cellgraph.FairCellGraphGenerator;

import java.util.Scanner;

public class CallingGephi {
    public static void main(String[] args){
        new CallingGephi().run();
    }
    private CellGraph cells;

    private CallingGephi(){
        cells = new BasicCellGraph();
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

        CellGraphGenerator graphGenerator = new FairCellGraphGenerator();
        graphGenerator.generate(250);
        cells = graphGenerator.getCellGraph();

        GephiAdaptor.getInstance().exportGraph();
    }

    private void loadWorld(){
        GephiAdaptor.getInstance().importGraph();
    }

    private void iterateWorld(){

    }

    private void setupColumns(){
        GephiAdaptor.getInstance().addNodeAttribute("Wealth", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Social", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Intelligence", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Looks", double.class);
        GephiAdaptor.getInstance().addNodeAttribute("Average", double.class);
    }
}