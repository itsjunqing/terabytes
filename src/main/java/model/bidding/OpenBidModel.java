package model.bidding;

import entity.BidInfo;
import entity.BidPreference;
import lombok.Getter;
import lombok.Setter;
import service.ExpiryService;
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
        super();
        Bid bidCreated = createBid(userId, bp, "Open");
        this.bidId = bidCreated.getId(); // set ID for future references
        this.userId = userId;
        this.openBidOffers = new ArrayList<>();
        refresh();
    }

    /**
     * Constructor to construct existing OpenBid
     * @param userId
     */
    public OpenBidModel(String userId) {
        super();
        Bid existingBid = extractBid(userId, "Open");
        this.bidId = existingBid.getId();
        this.userId = userId;
        this.openBidOffers = new ArrayList<>();
        refresh();
    }

    @Override
    public void refresh() {
        openBidOffers.clear();
        Bid bid = apiService.getBidApi().get(getBidId());
        ExpiryService expiryService = new ExpiryService();
        // check if the bid is expired, if the bid is expired, then remove the bid,
        // return an empty list, and update the error text
        if (!expiryService.checkIsExpired(bid)) {
            openBidOffers = bid.getAdditionalInfo().getBidOffers();
        } else{
//            errorText = "This Bid has expired, please make a new one";
            expired = true;
        }
        oSubject.notifyObservers();
    }

}
