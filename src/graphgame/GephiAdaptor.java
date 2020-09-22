package graphgame;

import graphgame.gephiutilities.GraphLayouter;
import org.gephi.appearance.api.AppearanceController;
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
    private static AppearanceController appearanceController;

    private static GephiNodeManager nodeManager;
    private static GraphLayouter layouter;

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
        appearanceController = defaultLookup.lookup(AppearanceController.class);

        nodeManager = new GephiNodeManager();
        layouter = new GraphLayouter();
    }

    private static Workspace setupInitialProject(){
        ProjectController pc = defaultLookup.lookup(ProjectController.class);
        pc.newProject();
        return pc.getCurrentWorkspace();
    }

    public static GraphModel getGraphModel(){
        return graphModel;
    }

    public static Graph getGraph(){
        return graph;
    }

    public static GraphLayouter getLayouter(){
        return layouter;
    }

    public static AppearanceController getAppearanceController(){
        return appearanceController;
    }

    void addNodeAttribute(String attributeName, Class type){
        nodeTable.addColumn(attributeName, type);
    }

    public int createNode(){
        return nodeManager.createNode();
    }

    public void setNodeAttribute(int nodeID, String attribute, Double value){
        nodeManager.getNode(nodeID).setAttribute(attribute, value);
    }

    public double getNumericalNodeAttribute(int nodeId, String attribute){
        return (double)nodeManager.getNode(nodeId).getAttribute(attribute);
    }

    public int getEdgeCount(){
        return graph.getEdgeCount();
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

    public boolean areNodesConnected(int firstNodeID, int secondNodeID){
        return connectedForwards(firstNodeID, secondNodeID) ||
                connectedForwards(secondNodeID, firstNodeID);
    }

    private boolean connectedForwards(int firstNodeID, int secondNodeID){
        Node firstNode = nodeManager.getNode(firstNodeID);
        Node secondNode = nodeManager.getNode(secondNodeID);
        return graph.getEdges(firstNode, secondNode).toArray().length != 0;
    }

    public void connectNodes(int sourceID, int destID){
        Node source = nodeManager.getNode(sourceID);
        Node dest = nodeManager.getNode(destID);
        Edge newEdge = graphModel.factory().newEdge(source, dest);
        graph.addEdge(newEdge);
    }

    public void disconnectNodes(int sourceID, int destID){
        Node source = nodeManager.getNode(sourceID);
        Node dest = nodeManager.getNode(destID);

        if(connectedForwards(sourceID, destID))
            graph.removeEdge(graph.getEdge(source, dest));
        else if(connectedForwards(destID, sourceID))
            graph.removeEdge(graph.getEdge(dest, source));
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
