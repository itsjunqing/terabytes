package model;

import lombok.Getter;
import observer.OSubject;
import observer.Observer;
import service.ExpiryService;

/**
 * An abstract base class of BasicModel that stores the basic common attributes and methods/
 */
@Getter
public abstract class BasicModel {

    protected String userId;
    protected OSubject oSubject;
    protected ExpiryService expiryService;
    protected String errorText;

    /**
     * Constructs a BasicModel
     */
    protected BasicModel() {
        this.oSubject = new OSubject();
        this.expiryService = new ExpiryService();
        this.errorText = "";
    }

    /**
     * Attach an observer to this base model.
     * @param o an Observer object
     */
    public void attach(Observer o) {
        oSubject.attach(o);
    }

    /**
     * Refreshes the model content.
     */
    public abstract void refresh();

}
