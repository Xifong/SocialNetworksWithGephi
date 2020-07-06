package graphgame.utilities;

import java.util.ArrayList;
import java.util.List;

public interface Subject {

    List<Observer> observers = new ArrayList<>();

    default void registerObserver(Observer observer) {
        observers.add(observer);
    }

    default void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    default void notifyObservers() {
        for (Observer observer : observers) {
            observer.notify(getState());
        }
    }

    Enum getState();
}
