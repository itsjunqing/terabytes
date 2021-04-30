package model.bidding;

import api.BidApi;
import api.SubjectApi;
import api.UserApi;
import entity.BidPreference;
import lombok.Getter;
import lombok.Setter;
import observer.OSubject;
import stream.Bid;
import stream.BidAdditionalInfo;
import stream.User;

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

    protected BidApi bidApi;
    protected SubjectApi subjectApi;
    protected String userId; // student's id
    protected String bidId; // Bid is not used because its content (offers / messages) are updated from time to time

    protected BiddingModel() {
        this.bidApi = new BidApi();
        this.subjectApi = new SubjectApi();
    }

    protected Bid createBid(String userId, BidPreference bp, String type) {
        BidAdditionalInfo bidAdditionalInfo = new BidAdditionalInfo(bp);
        Date dateCreated = new Date();
        String subjectId = getSubjectApi().getAllSubjects().stream()
                .filter(s -> s.getName().equals(bp.getSubject()))
                .findFirst()
                .orElse(null) // null guarantee to not occur as view selected is from a list of available subjects
                .getId();
        Bid bid = new Bid(type, userId, dateCreated, subjectId, bidAdditionalInfo);
        return bidApi.addBid(bid); // post BID
    }

    protected Bid extractBid(String userId, String type) {
        return bidApi.getAllBids().stream()
                .filter(b -> b.getDateClosedDown() == null)
                .filter(b -> b.getType().equalsIgnoreCase(type))
                .filter(b -> b.getInitiator().getId().equals(userId))
                .findFirst()
                .orElse(null); // can never exist
    }

    public void markBidClose() {
        Bid bidDateClosed = new Bid(new Date());
        bidApi.closeBid(bidId, bidDateClosed);
    }

    public Bid getBid() {
        return bidApi.getBid(bidId);
    }

    public String getUserName(String Id){
        UserApi userApi = new UserApi();
        User user = userApi.getUser(Id);
        String givenName = user.getGivenName();
        String familyName = user.getFamilyName();
        return givenName + " " + familyName;
    }

    public abstract void refresh();
}
