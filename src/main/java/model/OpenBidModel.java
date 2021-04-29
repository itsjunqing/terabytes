package model;

import entity.BidInfo;
import entity.BidPreference;
import lombok.Getter;
import lombok.Setter;
import stream.Bid;
import stream.BidAdditionalInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class OpenBidModel extends BiddingModel {

    private List<BidInfo> openBidOffers;
    private Bid bid;

    @Override
    public void createBid(String userId, BidPreference bp) {
        BidAdditionalInfo bidAdditionalInfo = new BidAdditionalInfo(bp);
        Date dateCreated = new Date();
        String subjectId = getSubjectApi().getAllSubjects().stream()
                .filter(s -> s.getName().equals(bp.getSubject()))
                .findFirst()
                .orElse(null) // null guarantee to not occur as view selected is from a list of available subjects
                .getId();
        Bid bid = new Bid("Open", userId, dateCreated, subjectId, bidAdditionalInfo);
        Bid bidCreated = getBidApi().addBid(bid); // post BID
        setBidId(bidCreated.getId()); // set ID for future references
        refresh();
    }

    @Override
    public void refresh() {
        bid = getBidApi().getBid(getBidId());
        openBidOffers = bid.getAdditionalInfo().getBidOffers();

//        notifyObservers();
    }

    @Override
    public void lookForBid(String userId) {
        this.setUserId(userId);
        List<Bid> bidList = getBidApi().getAllBids().stream()
                .filter(b -> b.getDateClosedDown() == null)
                .filter(b -> b.getType().equalsIgnoreCase("Open"))
                .filter(b -> b.getInitiator().getId().equals(getUserId()))
                .collect(Collectors.toList());
        this.setBidId(bidList.get(0).getId());
    }
}
