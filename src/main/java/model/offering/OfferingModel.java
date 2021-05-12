package model.offering;

import entity.Preference;
import lombok.Getter;
import model.BasicModel;
import service.ExpiryService;
import service.ApiService;
import stream.Bid;
import stream.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A Class of OfferingModel that stores the data of an Offering by Tutor.
 */
@Getter
public class OfferingModel extends BasicModel {

    private List<Bid> bidsOnGoing;
    protected String errorText;

    /**
     * Constructs an OfferingModel
     * @param userId a String of user id
     */
    public OfferingModel(String userId) {
        this.userId = userId;
        this.bidsOnGoing = new ArrayList<>();
        refresh();
    }

    /**
     * Refreshes the model
     */
    @Override
    public void refresh() {
        this.errorText = "";
        bidsOnGoing.clear(); // for memory cleaning
        List<Bid> bids = ApiService.bidApi().getAll();
        bidsOnGoing = OpenBidsService.processBids(bids, userId);
//        User currentUser = ApiService.userApi().get(userId);
//
//        ExpiryService expiryService = new ExpiryService();
//        for (Bid b: bids) {
//            if (!expiryService.checkIsExpired(b)) {
//                Preference bp = b.getAdditionalInfo().getPreference();
//                boolean hasQualification = currentUser.getQualifications().stream()
//                        .anyMatch(q -> q.getTitle().equals(bp.getQualification().toString()));
//
//                // Bonus mark on checking 2 levels higher for competency requirement
//                boolean hasCompetency = currentUser.getCompetencies().stream()
//                        .anyMatch(c -> c.getLevel() - 2 >= bp.getCompetency()
//                                && c.getSubject().getName().equals(bp.getSubject()));
//                if (hasQualification && hasCompetency) {
//                    bidsOnGoing.add(b);
//                }
//            }
//        }
        oSubject.notifyObservers();
    }

    /**
     * View the offers of a Bid based on the selection option.
     * @param selection an integer selection value
     * @return a Bid object
     */
    public Bid viewOffers(int selection){
        Bid bid = bidsOnGoing.get(selection-1);
        if (!expiryService.checkIsExpired(bid)){
            return bid;
        }
        return null;
    }

}
