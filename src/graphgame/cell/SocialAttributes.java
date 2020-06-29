package graphgame.cell;

import org.gephi.graph.api.Node;
import java.util.HashMap;

class SocialAttributes implements Cell {
    private Cell innerCell;

    private HashMap<String, Double> attributes;

    SocialAttributes(Cell innerCell, double[] attributeVector){
        this.innerCell = innerCell;
        this.attributes = new HashMap<>();
        attributes.put("Wealth", attributeVector[0]);
        attributes.put("Social", attributeVector[1]);
        attributes.put("Intelligence", attributeVector[2]);
        attributes.put("Looks", attributeVector[3]);

        innerCell.getGephiNode().setAttribute("Wealth", this.attributes.get("Wealth"));
        innerCell.getGephiNode().setAttribute("Social", this.attributes.get("Social"));
        innerCell.getGephiNode().setAttribute("Intelligence", this.attributes.get("Intelligence"));
        innerCell.getGephiNode().setAttribute("Looks", this.attributes.get("Looks"));
        innerCell.getGephiNode().setAttribute("Average", averageStat());
    }

    private Double averageStat(){
        return attributes.values().stream().reduce(0D, Double::sum)/attributes.size();
    }

    @Override
    public HashMap<String, Double> getAttributes(){
        return attributes;
    }

    @Override
    public Node getGephiNode() {
        return innerCell.getGephiNode();
    }

    @Override
    public boolean willAssociate(Cell cell){
        return innerCell.willAssociate(cell);
    }
}
