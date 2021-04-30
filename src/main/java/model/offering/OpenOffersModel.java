package model.offering;

import api.BidApi;
import api.UserApi;
import entity.BidInfo;
import lombok.Data;
import stream.Bid;
import stream.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OpenOffersModel {
    private BidApi bidApi;
    private UserApi userApi;
    private List<BidInfo> openOffers;
    private List<BidInfo> otherOffers;
    private String bidId;
    private String userId;
    private Bid bid;
    private BidInfo myOffer;

    public OpenOffersModel(String bidId, String userId) {
        this.bidId = bidId;
        this.bidApi = new BidApi();
        this.userApi = new UserApi();
        this.openOffers = new ArrayList<>();
        this.otherOffers = new ArrayList<>();
    }


    public void refresh() {
    this.openOffers.clear(); // for memory cleaning
    this.otherOffers.clear();
    this.bid = null;
    this.myOffer = null;


    // Getting the correct bid based on id
    List<Bid> bids = bidApi.getAllBids();
    for (Bid b: bids) {
    	if (b.getId().equals(this.getBidId())){
    	    this.bid = b;
        }
    }
    // Getting all offers
    openOffers = bid.getAdditionalInfo().getBidOffers();

    // Getting my offer
    for (BidInfo bI: openOffers){
        if (bI.getInitiatorId().equals(this.userId)){
            this.myOffer = bI;
        }
    }

    // Getting the list of other offers
    this.otherOffers = this.openOffers.stream()
            .filter(o -> !o.getInitiatorId().equals(this.userId))
            .collect(Collectors.toList());
    }

//        notifyObservers();
    public String getUserName(String Id){
        UserApi userApi = new UserApi();
        User user = userApi.getUser(Id);
        String givenName = user.getGivenName();
        String familyName = user.getFamilyName();
        return givenName + " " + familyName;
    }

    public void sendOffer(BidInfo bidInfo) {
//        Bid bidChosen = getOpenBids().get(bidIndex);
//        BidAdditionalInfo bidAdditionalInfo = bidChosen.getAdditionalInfo();
//        bidAdditionalInfo.getBidOffers().add(bidInfo); // add offer
//
//        // can only patch bid because api doesn't allow adding of information of the additionalInfo attribute
//        bidApi.patchBid(bidChosen.getId(), new Bid(bidAdditionalInfo));
    }


}





