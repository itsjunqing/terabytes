package stream;

import lombok.Data;
import model.BidInfo;
import model.BidPreference;

import java.util.ArrayList;
import java.util.List;

@Data
public class BidAdditionalInfo {// only for open bid
    private BidPreference bidPreference; // student's preference of Bid
    private List<BidInfo> bidOffers; // tutor's offer (for open bid only)

    public BidAdditionalInfo(BidPreference bidPreference) {
        this.bidPreference = bidPreference;
        this.bidOffers = new ArrayList<>();
    }
}
