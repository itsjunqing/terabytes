package model;

import lombok.Getter;
import lombok.Setter;
import stream.Bid;
import stream.BidAdditionalInfo;
import stream.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class CloseBiddingModel extends BiddingModel {

    @Override
    public void createBid(String userId, BidPreference bp) {
        BidAdditionalInfo bidAdditionalInfo = new BidAdditionalInfo(bp, new ArrayList<>());
        Date dateCreated = new Date();
        String subjectId = getSubjectApi().getAllSubjects().stream()
                .filter(s -> s.getName().equals(bp.getSubject()))
                .findFirst()
                .orElse(null) // null guarantee to not occur as view selected is from a list of available subjects
                .getId();
        Bid bid = new Bid("Close", userId, dateCreated, subjectId, bidAdditionalInfo);
        Bid bidCreated = getBidApi().addBid(bid); // post BID
        setBidId(bidCreated.getId()); // set ID for future references
    }

    @Override
    public void refresh() {
        Bid bid = getBidApi().getBid(getBidId());
        List<Message> messages = bid.getMessages();
        List<BidInfo> bidMessages = messages.stream()
                                            .map(Message::getAdditionalInfo)
                                            .collect(Collectors.toList());
        setBidOffers(bidMessages);
        notifyObservers();
    }

}
