package model.offering;

import entity.BidPreference;
import lombok.Getter;
import observer.OSubject;
import service.ApiService;
import service.ExpiryService;
import stream.Bid;
import stream.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OfferingModel extends OSubject {

    private String userId;
    private ApiService apiService;
    private List<Bid> bidsOnGoing;
    public OSubject oSubject;

    private ExpiryService expiryService;
//    private boolean expired;

//    protected String errorText;


    public OfferingModel(String userId) {
        this.userId = userId;
        this.apiService = new ApiService();
        this.expiryService = new ExpiryService();
        this.bidsOnGoing = new ArrayList<>();
        this.oSubject = new OSubject();
//        this.expired = false;

    }

    public void refresh() {
        // TODO: Nick, pls verify the logic to see if there is any missing requirement

        bidsOnGoing.clear(); // for memory cleaning
        User currentUser = apiService.getUserApi().get(userId);
        List<Bid> bids = apiService.getBidApi().getAll();
        ExpiryService expiryService = new ExpiryService();
        for (Bid b: bids) {
            boolean bidIsClosed = b.getDateClosedDown() != null;
            if (!bidIsClosed) {
                //   will check if bid is expired  either remove them
                //   (or create a contract if its a open bid and is expired)
                //   returns false if bid is not expired
                if (!expiryService.checkIsExpired(b)){
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
        }
        notifyObservers();
    }

    public Bid viewOffers(int selection){
        Bid bid = bidsOnGoing.get(selection-1);
        if (!expiryService.checkIsExpired(bid)){
            return bid;
        }
        else {
            return null;
        }
    }

}
