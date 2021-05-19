package observer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing the Subject of an Observer, contains a list of observers that observe this Subject.
 */
public class OSubject {
    protected List<Observer> observers;

    /**
     * Constructor.
     */
    public OSubject(){
        observers = new ArrayList<Observer>();
    }

    /**
     * Attaches an Observer to the Subject
     * @param o an Observer object
     */
    public void attach(Observer o) {
        observers.add(o);
    }

    public void remove(Observer o) {
        observers.remove(o);
    }

    /**
     * Notifies the list of observers.
     */
    public void notifyObservers() {
        for (Observer o:observers) {
            SwingUtilities.invokeLater(() -> {
                o.update();
            });
        }
    }
}
