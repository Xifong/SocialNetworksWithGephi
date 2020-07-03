package graphgame.cell;

import graphgame.GephiAdaptor;

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

        GephiAdaptor.getInstance().setNodeAttribute(getNodeID(),
                "Wealth", this.attributes.get("Wealth"));
        GephiAdaptor.getInstance().setNodeAttribute(getNodeID(),
                "Social", this.attributes.get("Social"));
        GephiAdaptor.getInstance().setNodeAttribute(getNodeID(),
                "Intelligence", this.attributes.get("Intelligence"));
        GephiAdaptor.getInstance().setNodeAttribute(getNodeID(),
                "Looks", this.attributes.get("Looks"));
        GephiAdaptor.getInstance().setNodeAttribute(getNodeID(),
                "Average", this.averageStat());
    }

    private Double averageStat(){
        return attributes.values().stream().reduce(0D, Double::sum)/attributes.size();
    }

    @Override
    public HashMap<String, Double> getAttributes(){
        return attributes;
    }

    @Override
    public int getNodeID() {
        return innerCell.getNodeID();
    }

    @Override
    public boolean willAssociate(Cell cell){
        return innerCell.willAssociate(cell);
    }
}
