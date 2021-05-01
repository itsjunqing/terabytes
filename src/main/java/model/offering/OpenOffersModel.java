package model.offering;

import api.BidApi;
import api.UserApi;
import entity.BidInfo;
import lombok.Getter;
import service.ExpiryService;
import observer.OSubject;
import stream.Bid;
import stream.BidAdditionalInfo;
import stream.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OpenOffersModel extends OSubject {

    private String userId;
    private String bidId;
    private BidApi bidApi;
    private UserApi userApi;
    private BidInfo myOffer;
    private List<BidInfo> openOffers;
    private boolean expired;

    public OpenOffersModel(String userId, String bidId) {
        this.userId = userId;
        this.bidId = bidId;
        this.bidApi = new BidApi();
        this.userApi = new UserApi();
        this.openOffers = new ArrayList<>();
        this.expired = false;
        refresh();
    }

    public void refresh() {
        openOffers.clear();
        Bid bid = bidApi.getBid(bidId);
        List<BidInfo> offers = bid.getAdditionalInfo().getBidOffers();
        System.out.println("From OpenOfferModel Refreshing..");
        ExpiryService expiryService = new ExpiryService();
        // if bid has expired, close down the bid
        if (!expiryService.checkIsExpired(bid)){
            // openOffers includes all the BidInfo offers (by all tutors) except the current tutor
            // myOffer is BidInfo offered by itself
            for (BidInfo bidInfo: offers) {
                if (bidInfo.getInitiatorId().equals(userId)) {
                    myOffer = bidInfo;
                } else {
                    openOffers.add(myOffer);
                }
            }
        }
        else{
            myOffer = null;
            openOffers.clear();
            expired = true;
        }
        System.out.println("From OpenOfferModel (Refresh): myOffer = " + myOffer.toString());
        System.out.println("From OpenOfferModel (Refresh): openOffers = " + openOffers.toString());
        notifyObservers();
    }

    public Bid getBid() {
        return bidApi.getBid(bidId);
    }


    public String getUserName(String Id){
        UserApi userApi = new UserApi();
        User user = userApi.getUser(Id);
        String givenName = user.getGivenName();
        String familyName = user.getFamilyName();
        return givenName + " " + familyName;
    }

    public void sendOffer(BidInfo bidInfo) {
        BidAdditionalInfo info = bidApi.getBid(bidId).getAdditionalInfo();
        BidInfo currentBidInfo = info.getBidOffers().stream()
                                    .filter(i -> i.getInitiatorId().equals(userId))
                                    .findFirst()
                                    .orElse(null);
        // if the tutor has provided an offer before, remove the offer
        if (currentBidInfo != null) {
            info.getBidOffers().remove(currentBidInfo);
        }
        info.getBidOffers().add(bidInfo);
        bidApi.patchBid(bidId, new Bid(info));
    }

}





