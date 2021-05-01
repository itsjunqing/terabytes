package model.offering;

import entity.BidInfo;
import lombok.Getter;
import model.BasicModel;
import service.ExpiryService;
import service.Service;
import stream.Bid;
import stream.BidAdditionalInfo;
import stream.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OpenOffersModel extends BasicModel {

    private String bidId;
    private BidInfo myOffer;
    private List<BidInfo> openOffers;
    private boolean expired;

    public OpenOffersModel(String userId, String bidId) {
        this.userId = userId;
        this.bidId = bidId;
        this.openOffers = new ArrayList<>();
        this.expired = false;
        refresh();
    }

    public void refresh() {
        openOffers.clear();
        Bid bid = Service.bidApi.get(bidId);
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
                    openOffers.add(bidInfo);
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
        oSubject.notifyObservers();
    }

    public Bid getBid() {
        return Service.bidApi.get(bidId);
    }


    public String getUserName(String Id){
        User user = Service.userApi.get(Id);
        return user.getGivenName() + " " + user.getFamilyName();
    }

    public void sendOffer(BidInfo bidInfo) {
        BidAdditionalInfo info = Service.bidApi.get(bidId).getAdditionalInfo();
        BidInfo currentBidInfo = info.getBidOffers().stream()
                                    .filter(i -> i.getInitiatorId().equals(userId))
                                    .findFirst()
                                    .orElse(null);
        // if the tutor has provided an offer before, remove the offer
        if (currentBidInfo != null) {
            info.getBidOffers().remove(currentBidInfo);
        }
        info.getBidOffers().add(bidInfo);
        Service.bidApi.patch(bidId, new Bid(info));
    }

}





