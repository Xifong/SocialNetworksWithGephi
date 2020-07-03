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
import org.openide.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GephiAdaptor {
    private static Workspace workspace;
    private static Lookup defaultLookup = Lookup.getDefault();
    static GraphModel graphModel;
    static Graph graph;
    private static FilterController filterController;
    private static ImportController importController;
    private static Table nodeTable;
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

    public int createNode(){
        return nodeManager.createNode();
    }

    void addNodeAttribute(String attributeName, Class type){
        nodeTable.addColumn(attributeName, type);
    }

    public void setNodeAttribute(int nodeID, String attribute, Double value){
        nodeManager.getNode(nodeID).setAttribute(attribute, value);
    }

    public double getNumericalNodeAttribute(int nodeId, String attribute){
        return (double)nodeManager.getNode(nodeId).getAttribute(attribute);
    }

    public List<Integer> getNodeIDs(){
        List<Integer> IDs = new ArrayList<>();
        for(Node node : graph.getNodes().toCollection()){
            IDs.add(Integer.valueOf((String)node.getId()));
        }
        return IDs;
    }

    public List<Pair<Integer, Integer>> getIDAdjacencies(){
        List<Pair<Integer, Integer>> adjacencies = new ArrayList<>();
        for(Edge edge : graph.getEdges().toCollection()){
            adjacencies.add(
                    Pair.of(Integer.valueOf((String)edge.getSource().getId()),
                            Integer.valueOf((String)edge.getTarget().getId())));
        }
        return adjacencies;
    }

    public void connectNodes(int sourceID, int destID){
        Node source = nodeManager.getNode(sourceID);
        Node dest = nodeManager.getNode(destID);
        Edge newEdge = graphModel.factory().newEdge(source, dest);
        graph.addEdge(newEdge);
    }

    public boolean areNodesConnected(int firstNodeID, int secondNodeID){
        Node firstNode = nodeManager.getNode(firstNodeID);
        Node secondNode = nodeManager.getNode(secondNodeID);
        return graph.getEdges(firstNode, secondNode).toArray().length != 0 ||
                graph.getEdges(secondNode, firstNode).toArray().length != 0;
    }

    public int getEdgeCount(){
        return graph.getEdgeCount();
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
