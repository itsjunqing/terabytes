package model.bidding;

import entity.BidInfo;
import entity.BidPreference;
import lombok.Getter;
import lombok.Setter;
import model.factory.ContractFactory;
import service.ExpiryService;
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

    public Contract offerSelection(int selection){
        Bid currentBid = getBid();
        BidInfo bidInfo = getOpenBidOffers().get(selection-1);
        markBidClose();
        System.out.println("From OpenBidController: Selected offer = " + bidInfo.toString());
        // change to to usage of contract factory
        ContractFactory contractFactory = new ContractFactory();
        ExpiryService expiryService = new ExpiryService();
        if (!expiryService.checkIsExpired(currentBid)){
            return contractFactory.createContract(currentBid, bidInfo);
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
