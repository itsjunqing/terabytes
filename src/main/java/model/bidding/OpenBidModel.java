package model.bidding;

import entity.BidInfo;
import entity.BidPreference;
import lombok.Getter;
import lombok.Setter;
import service.BuilderService;
import service.ApiService;
import stream.Bid;
import stream.Contract;

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
        Bid bid = BuilderService.buildBid(userId, bp, "Open");
        Bid bidCreated = ApiService.bidApi.add(bid);
        initModel(userId, bidCreated);
    }

    /**
     * Constructor to construct existing OpenBid
     * @param userId
     */
    public OpenBidModel(String userId) {
        Bid existingBid = extractBid(userId, "Open");
        initModel(userId, existingBid);
    }

    private void initModel(String userId, Bid bid) {
        this.bidId = bid.getId();
        this.userId = userId;
        this.openBidOffers = new ArrayList<>();
        refresh();
    }

    public Contract offerSelection(int selection){
        Bid currentBid = getBid();
        BidInfo bidInfo = getOpenBidOffers().get(selection-1);
        markBidClose();
        System.out.println("From OpenBidController: Selected offer = " + bidInfo.toString());
        if (!expiryService.checkIsExpired(currentBid)){
            return BuilderService.buildContract(currentBid, bidInfo);
        }
        errorText = "This Bid has expired or closed down, please close and refresh main page";
        oSubject.notifyObservers();
        return null;
    }

    @Override
    public void refresh() {
        this.errorText = "";
        Bid bid = ApiService.bidApi.get(getBidId());
        // If bid is expired, remove the bid
        if (!expiryService.checkIsExpired(bid)) {
            openBidOffers = new ArrayList<>(bid.getAdditionalInfo().getBidOffers()); // reference copy
        } else {
            errorText = "This Bid has expired or closed down, please refresh main page";
        }
        oSubject.notifyObservers();
    }

}
