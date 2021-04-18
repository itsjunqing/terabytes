package model;

import api.BidApi;
import lombok.Getter;
import observer.Subject;
import stream.Bid;

import java.util.Date;
import java.util.List;


@Getter
public abstract class BiddingModel extends Subject {

    private int bidId;
    private BidPreference bidPreference;
    private BidApi bidApi;

    public BiddingModel(int bidId, BidPreference bidPreference) {
        this.bidId = bidId;
        this.bidPreference = bidPreference;
        this.bidApi = new BidApi();
    }

    public Bid getBid() {
        return bidApi.getBid(String.valueOf(bidId));
    }

    public void markBidClose() {
        Bid bidDateClosed = new Bid(new Date());
        bidApi.closeBid(String.valueOf(bidId), bidDateClosed);
    }

    public abstract void refreshBid();

    public abstract List<BidInfo> getBidInfos();


}
