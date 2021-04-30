package model.bidding;

import entity.BidInfo;
import entity.BidPreference;
import lombok.Getter;
import lombok.Setter;
import stream.Bid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        refresh();
    }

    @Override
    public void refresh() {
        Bid bid = getBidApi().getBid(getBidId());
        openBidOffers.clear();
        openBidOffers = bid.getAdditionalInfo().getBidOffers();
//        notifyObservers();
    }



    /*
    OLD STUFFS BELOW HERE
     */

    @Override
    public void lookForBid(String userId) {
        this.setUserId(userId);
        List<Bid> bidList = getBidApi().getAllBids().stream()
                .filter(b -> b.getDateClosedDown() == null)
                .filter(b -> b.getType().equalsIgnoreCase("Open"))
                .filter(b -> b.getInitiator().getId().equals(getUserId()))
                .collect(Collectors.toList());
        this.setBidId(bidList.get(0).getId());
    }
}
