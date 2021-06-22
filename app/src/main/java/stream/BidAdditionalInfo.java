package stream;

import entity.Preference;
import lombok.Data;
import entity.BidInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * A BidAdditionalInfo data class, storing the extra information of a Bid,
 * which includes the preferences and a list of current tutors offers (for OpenBid only)
 */
@Data
public class BidAdditionalInfo {
    private Preference preference; // student's preference of Bid
    private List<BidInfo> bidOffers; // tutor's offer (for open bid only)

    public BidAdditionalInfo(Preference preference) {
        this.preference = preference;
        this.bidOffers = new ArrayList<>();
    }
}
