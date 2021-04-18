package stream;

import lombok.Data;
import model.BidInfo;
import model.BidPreference;

import java.util.List;

@Data
public class BidAdditionalInfo {
    private BidPreference bidPreference; // student's preference of Bid
    private List<BidInfo> bidOffers; // tutor's offer

    public BidAdditionalInfo(BidPreference bidPreference, List<BidInfo> bidOffers) {
        this.bidPreference = bidPreference;
        this.bidOffers = bidOffers;
    }
}
