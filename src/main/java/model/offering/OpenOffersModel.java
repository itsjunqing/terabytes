package model.offering;

import entity.BidInfo;
import entity.Preference;
import lombok.Getter;
import model.BasicModel;
import service.ApiService;
import service.BuilderService;
import service.ExpiryService;
import stream.Bid;
import stream.BidAdditionalInfo;
import stream.Contract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A Class of OpenOffersModel that stores the data of an Open Offer by tutor
 */
@Getter
public class OpenOffersModel extends BasicModel {

    private String bidId;
    private BidInfo myOffer;
    private List<BidInfo> openOffers;

    /**
     * Constructs a OpenOffersModel
     * @param userId a String of user id
     * @param bidId a Bid id
     */
    public OpenOffersModel(String userId, String bidId) {
        this.userId = userId;
        this.bidId = bidId;
        this.openOffers = new ArrayList<>();
        this.errorText = "";
        refresh();
    }

    /**
     * Refreshes the model.
     */
    @Override
    public void refresh() {
        openOffers.clear();
        errorText = "";
        Bid bid = ApiService.bidApi().get(bidId);
        List<BidInfo> offers = new ArrayList<>(bid.getAdditionalInfo().getBidOffers());
        System.out.println("From OpenOfferModel Refreshing..");
        ExpiryService expiryService = new ExpiryService();
        // if bid has expired, close down the bid
        if (!expiryService.checkIsExpired(bid)){
            for (BidInfo bidInfo: offers) {
                // myOffer is BidInfo offered by itself
                if (bidInfo.getInitiatorId().equals(userId)) {
                    myOffer = bidInfo;
                    // openOffers includes all the BidInfo offers (by all tutors) except the current tutor
                } else {
                    openOffers.add(bidInfo);
                }
            }
        } else{
            myOffer = null;
            openOffers.clear();
            errorText = "This Bid has expired or closed down, please refresh main page";
        }
        oSubject.notifyObservers();
    }

    /**
     * Respond to the Bid by providing an offer contained within a BidInfo
     * @param bidInfo a BidInfo object
     */
    public void respond(BidInfo bidInfo) {
        if (!expiryService.checkIsExpired(getBid())) {
            sendOffer(bidInfo);
        } else {
            errorText = "Bid Has Expired";
        }
        refresh();
    }

    /**
     * Sends an offer to the student by patching to the API.
     * @param bidInfo a BidInfo object
     */
    private void sendOffer(BidInfo bidInfo) {
        // Update offer
        BidAdditionalInfo info = ApiService.bidApi().get(bidId).getAdditionalInfo();
        BidInfo currentBidInfo = info.getBidOffers().stream()
                                    .filter(i -> i.getInitiatorId().equals(userId))
                                    .findFirst()
                                    .orElse(null);
        // if the tutor has provided an offer before, remove the offer
        if (currentBidInfo != null) {
            info.getBidOffers().remove(currentBidInfo);
        }
        info.getBidOffers().add(bidInfo);
        ApiService.bidApi().patch(bidId, new Bid(info));
    }

    /**
     * Buy out a Bid
     */
    public Contract buyOut() {
        Bid currentBid = getBid();
        if (!expiryService.checkIsExpired(currentBid)) {
            Preference bp = getBid().getAdditionalInfo().getPreference();
            BidInfo bidInfo = bp.getPreferences();
            bidInfo.setInitiatorId(getUserId());
            sendOffer(bidInfo);

            // Mark bid as closed
            ApiService.bidApi().close(bidId, new Bid(new Date()));

            return BuilderService.contractBuilder().buildContract(getBid(), bidInfo);
        }
        errorText = "Bid Has Expired";
        oSubject.notifyObservers();
        return null;
    }

    /**
     * Gets the Bid object of the corresponding offer
     * @return a Bid object
     */
    public Bid getBid() {
        return ApiService.bidApi().get(bidId);
    }

}





