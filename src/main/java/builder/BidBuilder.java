package builder;

import entity.Preference;
import service.ApiService;
import stream.Bid;
import stream.BidAdditionalInfo;

import java.util.Date;

/**
 * Class that builds a Bid
 */
public class BidBuilder {

    /**
     * Builds a Bid object based on the preference given
     * @param userId a String of user id
     * @param bp a bid Preference object
     * @param type the type ("Open" or "Close")
     * @return a Bid object
     */
    public Bid buildBid(String userId, Preference bp, String type) {
        BidAdditionalInfo bidAdditionalInfo = new BidAdditionalInfo(bp);
        Date dateCreated = new Date();
        String subjectId = ApiService.subjectApi().getAll().stream()
                .filter(s -> s.getName().equals(bp.getSubject()))
                .findFirst()
                .orElse(null) // null guarantee to not occur as view selected is from a list of available subjects
                .getId();
        return new Bid(type, userId, dateCreated, subjectId, bidAdditionalInfo);
    }
}
