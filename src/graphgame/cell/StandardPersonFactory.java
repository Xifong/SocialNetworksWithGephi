package graphgame.cell;

import graphgame.probabilityassignment.Sigmoid;
import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import graphgame.probabilityassignment.SigmoidProbAssigner;

import java.util.HashMap;

public class StandardPersonFactory extends AbstractCellFactory {
    private static MultivariateNormalDistribution attributeDist =
            new MultivariateNormalDistribution(new double[]{0, 0, 0, 0},
                    new double[][]{new double[]{1, 0, 0, 0},
                                   new double[]{0, 1, 0, 0},
                                   new double[]{0, 0, 1, 0},
                                   new double[]{0, 0, 0, 1}});

    @Override
    public Cell createCell() {
        Cell baseCell = new BaseCell();
        double[] attributeVector = attributeDist.sample();

        Cell withAttributes = new SocialAttributes(baseCell, attributeVector);

        return new StandardAssociationDecisionMaking(withAttributes, getProbabilityAssigner());
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
