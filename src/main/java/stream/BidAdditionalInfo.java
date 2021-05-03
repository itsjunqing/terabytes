package stream;

import lombok.Data;
import entity.BidInfo;
import entity.BidPreference;

import java.util.ArrayList;
import java.util.List;

/**
 * A BidAdditionalInfo data class, storing the extra information of a Bid,
 * which includes the preferences and a list of current tutors offers (for OpenBid only)
 */
@Data
public class BidAdditionalInfo {
    private BidPreference bidPreference; // student's preference of Bid
    private List<BidInfo> bidOffers; // tutor's offer (for open bid only)

    public BidAdditionalInfo(BidPreference bidPreference) {
        this.bidPreference = bidPreference;
        this.bidOffers = new ArrayList<>();
    }
}
