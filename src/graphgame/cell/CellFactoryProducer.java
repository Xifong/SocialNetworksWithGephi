package graphgame.cell;

public class CellFactoryProducer {
    public static AbstractCellFactory getFactory(String type){
        switch(type){
            case "STANDARD":
                return new StandardPersonFactory();
            default:
                return null;
        }
    }
}
