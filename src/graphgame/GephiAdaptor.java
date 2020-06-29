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
    static Workspace workspace;
    static Lookup defaultLookup = Lookup.getDefault();
    static GraphModel graphModel;
    static Graph graph;
    static FilterController filterController;
    static ImportController importController;
    static Table nodeTable;
    private static GephiNodeManager nodeManager;

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
        nodeManager = new GephiNodeManager();
    }

    private static Workspace setupInitialProject(){
        ProjectController pc = defaultLookup.lookup(ProjectController.class);
        pc.newProject();
        return pc.getCurrentWorkspace();
    }

    public long createNode(){
        return nodeManager.createNode();
    }

    void addNodeAttribute(String attributeName, Class type){
        nodeTable.addColumn(attributeName, type);
    }

    public void setNodeAttribute(long nodeID, String attribute, Double value){
        nodeManager.getNode(nodeID).setAttribute(attribute, value);
    }

    public void connectNodes(long sourceID, long destID){
        Node source = nodeManager.getNode(sourceID);
        Node dest = nodeManager.getNode(destID);
        Edge newEdge = graphModel.factory().newEdge(source, dest);
        graph.addEdge(newEdge);
    }

    public boolean areNodesConnected(long firstNodeID, long secondNodeID){
        Node firstNode = nodeManager.getNode(firstNodeID);
        Node secondNode = nodeManager.getNode(secondNodeID);
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
