package model;

import lombok.Getter;
import lombok.Setter;
import stream.Bid;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OpenBiddingModel extends BiddingModel {

    private List<BidInfo> bidOffers;

    public OpenBiddingModel(int bidId, BidPreference bidPreference) {
        super(bidId, bidPreference);
        this.bidOffers = new ArrayList<>();
    }

    @Override
    public void refreshBid() {
        Bid bid = getBidApi().getBid(String.valueOf(getBidId()));
        bidOffers.clear(); // for memory cleaning
        bidOffers = bid.getAdditionalInfo().getBidOffers(); // reset all bid offers to be displayed
        notifyObservers();
    }

    @Override
    public List<BidInfo> getBidInfos() {
        return bidOffers;
    }
}
