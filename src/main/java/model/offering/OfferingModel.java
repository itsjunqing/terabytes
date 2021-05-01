package model.offering;

import entity.BidPreference;
import lombok.Getter;
import model.BasicModel;
import service.ExpiryService;
import service.ApiService;
import stream.Bid;
import stream.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OfferingModel extends BasicModel {

    private List<Bid> bidsOnGoing;
//    protected String errorText;

    public OfferingModel(String userId) {
        this.userId = userId;
        this.bidsOnGoing = new ArrayList<>();
    }

    public void refresh() {
        this.errorText = "";
        bidsOnGoing.clear(); // for memory cleaning
        User currentUser = ApiService.userApi.get(userId);
        List<Bid> bids = ApiService.bidApi.getAll();
        ExpiryService expiryService = new ExpiryService();
        for (Bid b: bids) {
            if (!expiryService.checkIsExpired(b)) {
                BidPreference bp = b.getAdditionalInfo().getBidPreference();
                boolean hasQualification = currentUser.getQualifications().stream()
                        .anyMatch(q -> q.getTitle().equals(bp.getQualification().toString()));

                // Bonus mark on checking 2 levels higher for competency requirement
                boolean hasCompetency = currentUser.getCompetencies().stream()
                        .anyMatch(c -> c.getLevel() - 2 >= bp.getCompetency()
                                && c.getSubject().getName().equals(bp.getSubject()));
                if (hasQualification && hasCompetency) {
                    bidsOnGoing.add(b);
                }
            }
        }
        oSubject.notifyObservers();
    }

    public Bid viewOffers(int selection){
        Bid bid = bidsOnGoing.get(selection-1);
        if (!expiryService.checkIsExpired(bid)){
            return bid;
        }
        return null;
    }

}
