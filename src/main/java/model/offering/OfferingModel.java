package model.offering;

import api.BidApi;
import api.UserApi;
import entity.BidInfo;
import entity.BidPreference;
import entity.MessageBidInfo;
import entity.MessagePair;
import lombok.Getter;
import lombok.Setter;
import observer.OSubject;
import stream.Bid;
import stream.Message;
import stream.MessageAdditionalInfo;
import stream.User;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OfferingModel extends OSubject {

    private String userId;
    private UserApi userApi;
    private BidApi bidApi;
    private List<Bid> bidsOnGoing;

    public OfferingModel(String userId) {
        this.userId = userId; // only Tutor can initialize this, so User is a tutor
        this.userApi = new UserApi();
        this.bidApi = new BidApi();
        this.bidsOnGoing = new ArrayList<>();
    }

    public void refresh() {
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
//        notifyObservers();
    }







}
