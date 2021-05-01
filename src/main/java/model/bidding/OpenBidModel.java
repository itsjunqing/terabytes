package model.bidding;

import entity.BidInfo;
import entity.BidPreference;
import lombok.Getter;
import lombok.Setter;
import service.BuilderService;
import service.ExpiryService;
import service.Service;
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
        Bid bidCreated = BuilderService.buildBid(userId, bp, "Open");
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
        // change to to usage of contract factory
        ExpiryService expiryService = new ExpiryService();
        if (!expiryService.checkIsExpired(currentBid)){
            return BuilderService.buildContract(currentBid, bidInfo);
        }
        else {
            errorLabel = "This Bid has expired, please close this window";
            oSubject.notifyObservers();
            return null;
        }
    }

    @Override
    public void refresh() {
        openBidOffers.clear();
        Bid bid = Service.bidApi.get(getBidId());
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
