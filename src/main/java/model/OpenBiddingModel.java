package model;

import lombok.Getter;
import lombok.Setter;
import stream.Bid;
import stream.BidAdditionalInfo;

import java.util.ArrayList;
import java.util.Date;

@Getter @Setter
public class OpenBiddingModel extends BiddingModel {

    @Override
    public void createBid(String userId, BidPreference bp) {
        BidAdditionalInfo bidAdditionalInfo = new BidAdditionalInfo(bp, new ArrayList<>());
        Date dateCreated = new Date();
        String subjectId = getSubjectApi().getAllSubjects().stream()
                .filter(s -> s.getName().equals(bp.getSubject()))
                .findFirst()
                .orElse(null) // null guarantee to not occur as view selected is from a list of available subjects
                .getId();
        Bid bid = new Bid("Open", userId, dateCreated, subjectId, bidAdditionalInfo);
        Bid bidCreated = getBidApi().addBid(bid); // post BID
        setBidId(bidCreated.getId()); // set ID for future references
    }

    @Override
    public void refresh() {
        Bid bid = getBidApi().getBid(getBidId());
        setBidOffers(bid.getAdditionalInfo().getBidOffers());
        notifyObservers();
    }
}
