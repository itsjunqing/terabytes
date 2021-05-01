package model.bidding;

import entity.BidPreference;
import lombok.Getter;
import observer.OSubject;
import observer.Observer;
import service.ApiService;
import stream.Bid;
import stream.BidAdditionalInfo;
import stream.User;

import java.util.Date;


@Getter
public abstract class BiddingModel {

    /*
    bidOffers used in the following:
    1. For OpenBid:
    - Stores the bid offered by tutors
    - Does not contain any information by student as student does not provide any reply after its preference is made

    2. For CloseBid:
    - Stores the messages by tutors, message initiated by students are filtered out
     */

    protected ApiService apiService;
    protected OSubject oSubject;
    protected String userId; // student's id
    protected String bidId; // Bid is not used because its content (offers / messages) are updated from time to time
    protected boolean expired;
    protected String errorLabel;

    protected BiddingModel() {
        this.apiService = new ApiService();
        this.oSubject = new OSubject();
        this.expired = false;
    }

    protected Bid createBid(String userId, BidPreference bp, String type) {
        BidAdditionalInfo bidAdditionalInfo = new BidAdditionalInfo(bp);
        Date dateCreated = new Date();
        String subjectId = apiService.getSubjectApi().getAll().stream()
                .filter(s -> s.getName().equals(bp.getSubject()))
                .findFirst()
                .orElse(null) // null guarantee to not occur as view selected is from a list of available subjects
                .getId();
        Bid bid = new Bid(type, userId, dateCreated, subjectId, bidAdditionalInfo);
        return apiService.getBidApi().add(bid); // post BID
    }

    protected Bid extractBid(String userId, String type) {
        return apiService.getBidApi().getAll().stream()
                .filter(b -> b.getDateClosedDown() == null)
                .filter(b -> b.getType().equalsIgnoreCase(type))
                .filter(b -> b.getInitiator().getId().equals(userId))
                .findFirst()
                .orElse(null); // can never exist
    }

    public void markBidClose() {
        Bid bidDateClosed = new Bid(new Date());
        apiService.getBidApi().close(bidId, bidDateClosed);
    }

    public Bid getBid() {
        return apiService.getBidApi().get(bidId);
    }

    public String getUserName(String Id){
        User user = apiService.getUserApi().get(Id);
        String givenName = user.getGivenName();
        String familyName = user.getFamilyName();
        return givenName + " " + familyName;
    }

    public void attach(Observer o) {
        oSubject.attach(o);
    }

    public abstract void refresh();
}
