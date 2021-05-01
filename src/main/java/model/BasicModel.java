package model;

import lombok.Getter;
import observer.OSubject;
import observer.Observer;
import service.ExpiryService;

@Getter
public abstract class BasicModel {

    protected String userId;
    protected OSubject oSubject;
    protected ExpiryService expiryService;

    protected BasicModel() {
        this.oSubject = new OSubject();
        this.expiryService = new ExpiryService();
    }

    public void attach(Observer o) {
        oSubject.attach(o);
    }

    public abstract void refresh();
}
