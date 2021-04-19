package model;

import lombok.Getter;
import lombok.Setter;
import stream.Bid;
import stream.BidAdditionalInfo;
import stream.Message;
import stream.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class CloseBiddingModel extends BiddingModel {

    private List<BidInfo> bidMessages;

    public CloseBiddingModel() {
        super();
        this.bidMessages = new ArrayList<>();
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

        Bid bid = new Bid("Close", initiatorId, dateCreated, subjectId, bidAdditionalInfo);
        Bid bidCreated = getBidApi().addBid(bid); // post BID
        setBidId(bidCreated.getId()); // set the id
    }

    @Override
    public void refresh() {
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
