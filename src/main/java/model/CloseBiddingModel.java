package model;

import lombok.Getter;
import lombok.Setter;
import stream.Bid;
import stream.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class CloseBiddingModel extends BiddingModel {

    private List<BidInfo> bidMessages;

    public CloseBiddingModel(int bidId, BidPreference bidPreference) {
        super(bidId, bidPreference);
        this.bidMessages = new ArrayList<>();
    }

    @Override
    public void refreshBid() {
        Bid bid = getBidApi().getBid(String.valueOf(getBidId()));
        List<Message> messages = bid.getMessages();
        bidMessages.clear(); // for memory cleaning
        bidMessages = messages.stream()
                .map(m -> {
                    BidInfo i = m.getAdditionalInfo();
                    return new BidMessageInfo(i.getInitiatorId(), i.getTime(), i.getDay(), i.getDuration(),
                            i.getRate(), i.getNumberOfSessions(), i.isFreeLesson(),
                            i.getContractDuration(), m.getContent()); // construct new objects
                })
                .collect(Collectors.toList()); // resets all bidMessages
        notifyObservers();
    }

    @Override
    public List<BidInfo> getBidInfos() {
        return bidMessages;
    }
}
