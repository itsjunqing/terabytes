package observer;

import java.util.ArrayList;
import java.util.List;

public class OSubject {
    protected List<Observer> observers;

    public OSubject(){
        observers = new ArrayList<Observer>();
    }

    public void attach(Observer o) {
        observers.add(o);
    }

    public void notifyObservers() {
        for (Observer o:observers) {
            o.update();
            System.out.println("From " + this.getClass().getName() + ": updating " + o.getClass().getName());
        }
    }
}
