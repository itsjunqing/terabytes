package model;

import api.BidApi;
import api.SubjectApi;
import lombok.Getter;
import lombok.Setter;
import observer.OSubject;
import stream.Bid;

import java.util.Date;
import java.util.List;


@Getter @Setter
public abstract class BiddingModel extends OSubject {

    private BidApi bidApi;
    private SubjectApi subjectApi;
    private String bidId; // Bid is not used because its content (offers / messages) are updated from time to time
    private List<BidInfo> bidOffers;

    public BiddingModel() {
        this.bidApi = new BidApi();
        this.subjectApi = new SubjectApi();
        refresh();
    }

    public void markBidClose() {
        Bid bidDateClosed = new Bid(new Date());
        bidApi.closeBid(String.valueOf(bidId), bidDateClosed);
    }

    public Bid getBid() {
        return bidApi.getBid(bidId);
    }

    public abstract void createBid(String userId, BidPreference bp);
    public abstract void refresh();
}
