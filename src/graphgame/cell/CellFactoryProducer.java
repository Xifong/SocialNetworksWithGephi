package graphgame.cell;

public class CellFactoryProducer {
    public static CellFactory getFactory(String type){
        switch(type){
            case "STANDARD":
                return new StandardPersonFactory();
            default:
                return null;
        }
    }
}
