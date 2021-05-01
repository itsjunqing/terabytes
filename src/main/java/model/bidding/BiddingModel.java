package model.bidding;

import lombok.Getter;
import model.BasicModel;
import service.ApiService;
import stream.Bid;
import stream.User;

import java.util.Date;


@Getter
public abstract class BiddingModel extends BasicModel {

    /*
    bidOffers used in the following:
    1. For OpenBid:
    - Stores the bid offered by tutors
    - Does not contain any information by student as student does not provide any reply after its preference is made

    2. For CloseBid:
    - Stores the messages by tutors, message initiated by students are filtered out
     */

    protected String bidId;
    protected boolean expired;
    protected String errorLabel;

    protected BiddingModel() {
        this.expired = false;
    }

    protected Bid extractBid(String userId, String type) {
        return ApiService.bidApi.getAll().stream()
                .filter(b -> b.getDateClosedDown() == null)
                .filter(b -> b.getType().equalsIgnoreCase(type))
                .filter(b -> b.getInitiator().getId().equals(userId))
                .findFirst()
                .orElse(null); // can never exist
    }

    public void markBidClose() {
        Bid bidDateClosed = new Bid(new Date());
        ApiService.bidApi.close(bidId, bidDateClosed);
    }

    public Bid getBid() {
        return ApiService.bidApi.get(bidId);
    }

    public String getUserName(String Id){
        User user = ApiService.userApi.get(Id);
        String givenName = user.getGivenName();
        String familyName = user.getFamilyName();
        return givenName + " " + familyName;
    }

}
