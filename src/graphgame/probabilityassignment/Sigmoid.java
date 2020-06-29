package graphgame.probabilityassignment;

public class Sigmoid {
    public enum Direction{
        FORWARDS,
        BACKWARDS
    }

    private double constant;
    private double numerator;
    private double xCoefficient;
    private double xTranslation;

    public Sigmoid(double lowerAsymptote, double upperAsymptote,
            Direction direction, double steepness,
            double midPoint){
        this.constant = lowerAsymptote;
        this.numerator = upperAsymptote-lowerAsymptote;
        this.xCoefficient = direction == Direction.BACKWARDS ? steepness : -steepness;
        this.xTranslation = midPoint;
    }

    double f(double x){
        return constant + numerator/(1 + Math.exp(xCoefficient*(x-xTranslation)));
    }
}
