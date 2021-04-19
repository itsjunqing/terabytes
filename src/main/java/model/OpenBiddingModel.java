package model;

import lombok.Getter;
import lombok.Setter;
import stream.Bid;
import stream.BidAdditionalInfo;
import stream.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
public class OpenBiddingModel extends BiddingModel {

    private List<BidInfo> bidOffers;

    public OpenBiddingModel() {
        super();
        this.bidOffers = new ArrayList<>();
    }

    @Override
    public void createBid(User user, BidPreference bidPreference) {
        BidAdditionalInfo bidAdditionalInfo = new BidAdditionalInfo(bidPreference, new ArrayList<>());

        // extract info
        String initiatorId = user.getId();
        Date dateCreated = new Date();
        String subjectId = getSubjectApi().getAllSubjects().stream()
                .filter(s -> s.getName().equals(bidPreference.getSubject()))
                .findFirst()
                .orElse(null) // null guarantee to not occur as view selected is from a list of available subjects
                .getId();

        Bid bid = new Bid("Open", initiatorId, dateCreated, subjectId, bidAdditionalInfo);
        Bid bidCreated = getBidApi().addBid(bid);
        setBidId(bidCreated.getId()); // set the id
    }

    @Override
    public void refresh() {
        Bid bid = getBidApi().getBid(String.valueOf(getBidId()));
        bidOffers.clear(); // for memory cleaning
        bidOffers = bid.getAdditionalInfo().getBidOffers(); // reset all bid offers to be displayed
        notifyObservers();
    }

    @Override
    public List<BidInfo> getBidInfos() {
        return bidOffers;
    }
}
