package observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    protected List<Observer> observers = new ArrayList<Observer>();

    public void attach(Observer o) {
        observers.add(o);
    }

    public void notifyObservers() {
        observers.forEach(o -> o.update());
    }
}
