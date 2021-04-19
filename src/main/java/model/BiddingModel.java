package model;

import api.BidApi;
import api.SubjectApi;
import lombok.Getter;
import lombok.Setter;
import observer.OSubject;
import stream.Bid;
import stream.User;

import java.util.Date;
import java.util.List;


@Getter @Setter
public abstract class BiddingModel extends OSubject implements ModelFeatures {

    private String bidId;
    private BidPreference bidPreference;
    private BidApi bidApi;
    private SubjectApi subjectApi;

    public BiddingModel() {
//        this.bidPreference = bidPreference; // not sure if needed
        this.bidApi = new BidApi();
    }

    public Bid getBid() {
        return bidApi.getBid(String.valueOf(bidId));
    }

    public void markBidClose() {
        Bid bidDateClosed = new Bid(new Date());
        bidApi.closeBid(String.valueOf(bidId), bidDateClosed);
    }

    public abstract void createBid(User user, BidPreference bidPreference);

    public abstract List<BidInfo> getBidInfos();





}
