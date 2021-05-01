package model.offering;

import entity.BidInfo;
import entity.BidPreference;
import lombok.Getter;
import model.BasicModel;
import service.ApiService;
import service.BuilderService;
import service.ExpiryService;
import stream.Bid;
import stream.BidAdditionalInfo;
import stream.Contract;
import stream.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        this.errorText = "";
        refresh();
    }

    public void refresh() {
        openOffers.clear();
        this.errorText = "";
        Bid bid = ApiService.bidApi.get(bidId);
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
        } else{
            myOffer = null;
            openOffers.clear();
            expired = true;
        }
        oSubject.notifyObservers();
    }

    public Bid getBid() {
        return ApiService.bidApi.get(bidId);
    }


    public String getUserName(String Id){
        User user = ApiService.userApi.get(Id);
        return user.getGivenName() + " " + user.getFamilyName();
    }

    public void sendOffer(BidInfo bidInfo) {
        // Update offer
        BidAdditionalInfo info = ApiService.bidApi.get(bidId).getAdditionalInfo();
        BidInfo currentBidInfo = info.getBidOffers().stream()
                                    .filter(i -> i.getInitiatorId().equals(userId))
                                    .findFirst()
                                    .orElse(null);
        // if the tutor has provided an offer before, remove the offer
        if (currentBidInfo != null) {
            info.getBidOffers().remove(currentBidInfo);
        }
        info.getBidOffers().add(bidInfo);
        ApiService.bidApi.patch(bidId, new Bid(info));
    }

    public void buyBid(BidInfo bidInfo) {
        // Update offer -> Create contract -> Sign contract
        sendOffer(bidInfo);
        Contract contract = BuilderService.buildContract(getBid(), bidInfo);
        Contract contractCreated = ApiService.contractApi.add(contract);
        ApiService.contractApi.sign(contractCreated.getId(), new Contract(new Date()));
    }

    public void buyOut(){
        if (!expiryService.checkIsExpired(getBid())){
            BidPreference bp = getBid().getAdditionalInfo().getBidPreference();
            BidInfo bidInfo = bp.getPreferences();
            bidInfo.setInitiatorId(getUserId());
            sendOffer(bidInfo);
            Contract contract = BuilderService.buildContract(getBid(), bidInfo);
            // logic to post contract
            Contract contractCreated = ApiService.contractApi.add(contract);

            // add 10 seconds to contract signing as signDate > creationDate
            // TODO: we can do timeunit.delay?
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.SECOND, 10);
            ApiService.contractApi.sign(contractCreated.getId(), new Contract(c.getTime()));

            // mark bid as closed
            ApiService.bidApi.close(bidId, new Bid(new Date()));
        } else {
            errorText = "Bid Has Expired";
            oSubject.notifyObservers();
        }
    }

    public void respond(BidInfo bidInfo) {
        if (!expiryService.checkIsExpired(getBid())) {
            sendOffer(bidInfo);
        } else {
        }
    }

}





