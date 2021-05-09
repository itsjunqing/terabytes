package model.bidding;

import entity.BidInfo;
import entity.BidPreference;
import entity.Constants;
import lombok.Getter;
import lombok.Setter;
import service.ApiService;
import service.BuilderService;
import stream.Bid;
import stream.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * A class of OpenBidModel that stores the data of an Open Bidding.
 * Contains the list of open offers provided by tutor(s).
 */
@Getter @Setter
public class OpenBidModel extends BiddingModel {

    private List<BidInfo> openBidOffers;

    /**
     * Constructor to construct a new OpenBidModel with a new Bid initiated.
     * @param userId a String of user id
     * @param bp a BidPreference object
     */
    public OpenBidModel(String userId, BidPreference bp) {
        Bid bid = BuilderService.buildBid(userId, bp, "Open");
        Bid bidCreated = ApiService.bidApi().add(bid);
        initModel(userId, bidCreated);
    }

    /**
     * Constructor to construct a OpenBidModel based on the existing on-going Bid.
     * @param userId a String of user id
     */
    public OpenBidModel(String userId) {
        Bid existingBid = extractBid(userId, "Open");
        initModel(userId, existingBid);
    }

    /**
     * Initializes the model attributes.
     * @param userId a String of user id
     * @param bid a Bid object (created or extracted)
     */
    private void initModel(String userId, Bid bid) {
        this.bidId = bid.getId();
        this.userId = userId;
        this.openBidOffers = new ArrayList<>();
        refresh();
    }


    /**
     * Refreshes the model.
     */
    @Override
    public void refresh() {
        this.errorText = "";
        Bid bid = ApiService.bidApi().get(getBidId());
        // If bid is expired, remove the bid
        if (!expiryService.checkIsExpired(bid)) {
            openBidOffers = new ArrayList<>(bid.getAdditionalInfo().getBidOffers()); // reference copy
        } else {
            errorText = "This Bid has expired or closed down, please refresh main page";
        }
        oSubject.notifyObservers();
    }

//    /**
//     * Selects and return an offer based on the selection option.
//     * @param selection an integer selection
//     * @return a BidInfo object
//     */
//    @Override
//    public BidInfo selectOffer(int selection) {
//        BidInfo bidInfo = openBidOffers.get(selection-1);
//        markBidClose();
//        return bidInfo;
//    }


    public Contract formContract(int selection) {
        Bid currentBid = getBid();
        BidInfo bidInfo = openBidOffers.get(selection-1);
        markBidClose();
        if (!expiryService.checkIsExpired(currentBid)){
            // TODO: to be replaced
            return BuilderService.buildContract(currentBid, bidInfo, Constants.DEFAULT_CONTRACT_DURATION);
        }
        errorText = "This Bid has expired or closed down, please close and refresh main page";
        oSubject.notifyObservers();
        return null;
    }

}
