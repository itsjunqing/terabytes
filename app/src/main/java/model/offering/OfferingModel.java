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
import java.util.stream.Collectors;

/**
 * A Class of OfferingModel that stores the data of an Offering by Tutor.
 */
@Getter
public class OfferingModel extends BasicModel {

    private List<Bid> bidsOnGoing;

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
        User currentUser = ApiService.userApi().get(userId);

        ExpiryService expiryService = new ExpiryService();
        for (Bid b: bids) {
            if (!expiryService.checkIsExpired(b)) {
                Preference bp = b.getAdditionalInfo().getPreference();
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

    /**
     * Filters a list of bids for open bids.
     * @return a list of Open Bids
     */
    public List<Bid> getOpenBidsOnGoing(){
        return bidsOnGoing.stream().filter(b -> b.getType().equals("Open")).collect(Collectors.toList());
    }

}
