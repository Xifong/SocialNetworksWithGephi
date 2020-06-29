package graphgame;

import org.gephi.filters.api.FilterController;
import org.gephi.graph.api.*;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDirectionDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GephiAdaptor {
    private static Workspace workspace;
    private static Lookup defaultLookup = Lookup.getDefault();
    private static GraphModel graphModel;
    private static Graph graph;
    private static FilterController filterController;
    private static ImportController importController;
    private static Table nodeTable;

    private static GephiAdaptor singleton;

    public static GephiAdaptor getInstance(){
        if(singleton == null){
            singleton = new GephiAdaptor();
        }
        return singleton;
    }

    private GephiAdaptor(){
        workspace = setupInitialProject();
        graphModel = defaultLookup.lookup(GraphController.class).getGraphModel();
        graph = graphModel.getGraph();
        filterController = defaultLookup.lookup(FilterController.class);
        importController = defaultLookup.lookup(ImportController.class);
        nodeTable = graphModel.getNodeTable();
    }

    private static Workspace setupInitialProject(){
        ProjectController pc = defaultLookup.lookup(ProjectController.class);
        pc.newProject();
        return pc.getCurrentWorkspace();
    }

    public Node addNewNode(int id){
        Node newNode = graphModel.factory().newNode(String.valueOf(id));
        graph.addNode(newNode);
        return newNode;
    }

    void addNodeAttribute(String attributeName, Class type){
        nodeTable.addColumn(attributeName, type);
    }

    public void connectNodes(Node source, Node dest){
        Edge newEdge = graphModel.factory().newEdge(source, dest);
        graph.addEdge(newEdge);
    }

    public boolean areNodesConnected(Node firstNode, Node secondNode){
        return graph.getEdges(firstNode, secondNode).toArray().length != 0 ||
                graph.getEdges(secondNode, firstNode).toArray().length != 0;
    }

    public boolean isComplete(){
        int sumToGoTo = graph.getNodeCount()-1;

        if(sumToGoTo <= 0){
            return true;
        }

        int edges = graph.getEdgeCount();

        long currentfactorial = 1;

        while(edges >= currentfactorial && sumToGoTo > 1){
            currentfactorial += sumToGoTo--;
        }
        return edges >= currentfactorial;
    }

    public void resetGraph(){
        graph.clear();
    }

    void exportGraph(){
        ExportController exportController = defaultLookup.lookup(ExportController.class);
        try{
            exportController.exportFile(new File("test_game_graph.gexf"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    void importGraph(){
        Container container;
        try{
            File file = new File("test_game_graph.gexf");
            container = importController.importFile(file);
            container.getLoader().setEdgeDefault(EdgeDirectionDefault.UNDIRECTED);
        } catch(FileNotFoundException e){
            e.printStackTrace();
            return;
        }
        importController.process(container, new DefaultProcessor(), workspace);
    }
}
