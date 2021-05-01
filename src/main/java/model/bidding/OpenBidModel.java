package model.bidding;

import entity.BidInfo;
import entity.BidPreference;
import lombok.Getter;
import lombok.Setter;
import model.CheckExpired;
import observer.OSubject;
import stream.Bid;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OpenBidModel extends BiddingModel {

    private List<BidInfo> openBidOffers;

    /**
     * Constructor to construct a new OpenBid
     * @param userId
     * @param bp
     */
    public OpenBidModel(String userId, BidPreference bp) {

        Bid bidCreated = createBid(userId, bp, "Open");
        this.bidId = bidCreated.getId(); // set ID for future references
        this.userId = userId;
        this.openBidOffers = new ArrayList<>();
        oSubject = new OSubject();
        refresh();
    }

    /**
     * Constructor to construct existing OpenBid
     * @param userId
     */
    public OpenBidModel(String userId) {
        Bid existingBid = extractBid(userId, "Open");
        this.bidId = existingBid.getId();
        this.userId = userId;
        this.openBidOffers = new ArrayList<>();
        refresh();
    }

    @Override
    public void refresh() {
        openBidOffers.clear();
        Bid bid = getBidApi().getBid(getBidId());
        CheckExpired checkExpired = new CheckExpired();
        // check if the bid is expired, if the bid is expired, then remove the bid,
        // return an empty list, and update the error text
        if (!checkExpired.checkIsExpired(bid)) {
            openBidOffers = bid.getAdditionalInfo().getBidOffers();
            oSubject.notifyObservers();
        } else{
            errorText = "This Bid has expired, please make a new one";
        }
    }


}
