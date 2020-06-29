package graphgame.probabilityassignment;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public interface ProbabilityAssignerContainer {
    default double overallProbability(HashMap<String, Double> attributes){
        Stream<Map.Entry<String, Double>> attributeStream = attributes.entrySet().stream();

        return attributeStream.map(this::getProbabilityForMapEntry).reduce(0D, Double::sum)/
                attributes.size();
    }

    default double getProbabilityForMapEntry(Map.Entry<String, Double> entry){
        return getProbability(entry.getKey(), entry.getValue());
    }

    double getProbability(String attribute, double xValue);
}
