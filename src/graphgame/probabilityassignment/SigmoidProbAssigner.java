package graphgame.probabilityassignment;

import java.util.HashMap;

public class SigmoidProbAssigner implements ProbabilityAssignerContainer {

    private HashMap<String, Sigmoid> sigmoids;

    public SigmoidProbAssigner(HashMap<String, Sigmoid> sigmoids){
        this.sigmoids = sigmoids;
    }

    @Override
    public double getProbability(String attribute, double xValue) {
        return sigmoids.get(attribute).f(xValue);
    }
}
