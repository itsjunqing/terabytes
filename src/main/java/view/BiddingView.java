package view;

import lombok.Getter;
import model.BiddingModel;

@Getter
public abstract class BiddingView {

    private BiddingModel biddingModel;

    public BiddingView(BiddingModel biddingModel) {
        this.biddingModel = biddingModel;
    }

//    public abstract void updateDisplay();
}
