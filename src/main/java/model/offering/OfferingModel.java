package model.offering;

import api.BidApi;
import api.UserApi;
import entity.BidPreference;
import lombok.Getter;
import lombok.Setter;
import observer.OSubject;
import stream.Bid;
import stream.User;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OfferingModel {

    private String userId;
    private UserApi userApi;
    private BidApi bidApi;
    private List<Bid> bidsOnGoing;
    public OSubject oSubject;
    protected String errorText;


    public OfferingModel(String userId) {
        this.userId = userId;
        this.userApi = new UserApi();
        this.bidApi = new BidApi();
        this.bidsOnGoing = new ArrayList<>();
        this.errorText = "";

        oSubject = new OSubject();

    }

    public void refresh() {
        // TODO: Nick, pls verify the logic to see if there is any missing requirement
        bidsOnGoing.clear(); // for memory cleaning
        User currentUser = userApi.getUser(userId);
        List<Bid> bids = bidApi.getAllBids();
        for (Bid b: bids) {
            boolean bidIsClosed = b.getDateClosedDown() != null;
            if (!bidIsClosed) {
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
    public void setErrorText(String errorText){
        this.errorText = errorText;
    }

}
