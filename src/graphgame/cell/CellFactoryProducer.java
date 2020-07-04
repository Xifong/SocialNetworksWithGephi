package graphgame.cell;

public class CellFactoryProducer {
    public enum FactoryType{
        STANDARD
    }

    public static CellFactory getFactory(FactoryType type){
        switch(type){
            case STANDARD:
                return new StandardPersonFactory();
            default:
                return null;
        }
    }
}
