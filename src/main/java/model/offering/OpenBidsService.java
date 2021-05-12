package model.offering;

import entity.Preference;
import service.ApiService;
import service.ExpiryService;
import stream.Bid;
import stream.User;

import java.util.ArrayList;
import java.util.List;

public class OpenBidsService {
    private static final ExpiryService expiryService = new ExpiryService();;

    public static List<Bid> processBids(List<Bid> bids, String userId) {
        List<Bid> outputBidList = new ArrayList<Bid>();
        User currentUser = ApiService.userApi().get(userId);
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
//                    Bid filteredBid = filterBidOffers(b);
                    outputBidList.add(b);
                }
            }
        }
        return outputBidList;
    }
}
