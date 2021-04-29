package model;

import api.BidApi;
import api.SubjectApi;
import lombok.Getter;
import lombok.Setter;
import observer.OSubject;
import stream.Bid;

import java.util.Date;


@Getter @Setter
public abstract class BiddingModel extends OSubject {

    /*
    bidOffers used in the following:
    1. For OpenBid:
    - Stores the bid offered by tutors
    - Does not contain any information by student as student does not provide any reply after its preference is made

    2. For CloseBid:
    - Stores the messages by tutors, message initiated by students are filtered out
     */

    private BidApi bidApi;
    private SubjectApi subjectApi;
    private String userId; // student's id
    private String bidId; // Bid is not used because its content (offers / messages) are updated from time to time

    public BiddingModel() {
        this.bidApi = new BidApi();
        this.subjectApi = new SubjectApi();
//        refresh();
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
