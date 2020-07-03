package graphgame.cell;

import org.gephi.graph.api.Node;
import graphgame.probabilityassignment.ProbabilityAssignerContainer;

import java.util.HashMap;

class StandardAssociationDecisionMaking implements Cell {
    private Cell innerCell;
    private ProbabilityAssignerContainer probabilityAssigner;

    StandardAssociationDecisionMaking(Cell innerCell, ProbabilityAssignerContainer probabilityAssigner){
        this.innerCell = innerCell;
        this.probabilityAssigner = probabilityAssigner;
    }

    /*
    void tryMakeNewConnection(){
        Query fOfFriendsQ = buildFofFQuery();
        GraphView view = filterController.filter(fOfFriendsQ);
        Graph viewGraph = graphgame.CallingGephi.graphModel.getGraph(view);
        viewGraph.
        List<Cell> fOfFriends = graphgame.CallingGephi.graphModel.getEdgeTable().
    }

    private Query buildFofFQuery(){
        INTERSECTIONBuilder.IntersectionOperator intersectionOp =
                new INTERSECTIONBuilder.IntersectionOperator();
        Query intersectQ = filterController.createQuery(intersectionOp);

        EgoBuilder.EgoFilter egoFilter = new EgoBuilder.EgoFilter();
        egoFilter.setPattern(String.valueOf(innerCell.getNodeID().getId()));
        egoFilter.setDepth(2);
        Query ego2Q = filterController.createQuery(egoFilter);

        NOTBuilderNode.NOTOperatorNode notOp =
                new NOTBuilderNode.NOTOperatorNode();
        Query notQ = filterController.createQuery(notOp);

        egoFilter.setDepth(1);
        Query ego1Q = filterController.createQuery(egoFilter);

        filterController.setSubQuery(notQ, ego1Q);
        filterController.setSubQuery(intersectQ, ego2Q);
        filterController.setSubQuery(intersectQ, notQ);

        return intersectQ;
    }*/

    @Override
    public boolean willAssociate(Cell cell){
        HashMap<String, Double> attributes = cell.getAttributes();
        return probabilityAssigner.overallProbability(attributes) > Math.random();
    }

    @Override
    public int getNodeID() {
        return innerCell.getNodeID();
    }

    @Override
    public HashMap<String, Double> getAttributes(){
        return innerCell.getAttributes();
    }
}
