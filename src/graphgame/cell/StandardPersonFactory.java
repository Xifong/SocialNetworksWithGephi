package graphgame.cell;

import graphgame.GephiAdaptor;
import graphgame.probabilityassignment.Sigmoid;
import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import graphgame.probabilityassignment.SigmoidProbAssigner;

import java.util.HashMap;

public class StandardPersonFactory implements CellFactory {
    private static MultivariateNormalDistribution attributeDist =
            new MultivariateNormalDistribution(new double[]{0, 0, 0, 0},
                    new double[][]{new double[]{1, 0, 0, 0},
                                   new double[]{0, 1, 0, 0},
                                   new double[]{0, 0, 1, 0},
                                   new double[]{0, 0, 0, 1}});

    @Override
    public Cell createCell() {
        return wrapAssociationBehaviour(wrapSocialAttributes(new BaseCell()));
    }

    private Cell wrapSocialAttributes(Cell cell){
        double[] attributeVector = attributeDist.sample();
        return  new SocialAttributes(cell, attributeVector);
    }

    @Override
    public Cell createCellForExistingNode(int nodeId) {
        return wrapAssociationBehaviour(wrapExistingSocialAttributes(new BaseCell(nodeId)));
    }

    private Cell wrapExistingSocialAttributes(Cell cell){
        double[] attributeVector = getSocialAttributes(cell.getNodeID());
        return new SocialAttributes(cell, attributeVector);
    }

    private double[] getSocialAttributes(int nodeId){
        return new double[]{
                GephiAdaptor.getInstance().getNumericalNodeAttribute(nodeId, "Wealth"),
                GephiAdaptor.getInstance().getNumericalNodeAttribute(nodeId, "Social"),
                GephiAdaptor.getInstance().getNumericalNodeAttribute(nodeId, "Intelligence"),
                GephiAdaptor.getInstance().getNumericalNodeAttribute(nodeId, "Looks"),
        };
    }

    private Cell wrapAssociationBehaviour(Cell cell){
        return new StandardAssociationDecision(cell, getProbabilityAssigner());
    }

    private SigmoidProbAssigner getProbabilityAssigner(){
        HashMap<String, Sigmoid> probabilties= new HashMap<>();
        probabilties.put("Wealth",
                new Sigmoid(0, 1,
                        Sigmoid.Direction.FORWARDS, 1.5, 0.5));
        probabilties.put("Social",
                new Sigmoid(0, 1,
                        Sigmoid.Direction.FORWARDS, 1.5, 0.5));
        probabilties.put("Intelligence",
                new Sigmoid(0, 1,
                        Sigmoid.Direction.FORWARDS, 1.5, 0.5));
        probabilties.put("Looks",
                new Sigmoid(0, 1,
                        Sigmoid.Direction.FORWARDS, 1.5, 0.5));


        return new SigmoidProbAssigner(probabilties);
    }
}
