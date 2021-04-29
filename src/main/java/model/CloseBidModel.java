package model;


import entity.BidInfo;
import entity.BidPreference;
import entity.MessageBidInfo;
import entity.MessagePair;
import lombok.Getter;
import lombok.Setter;
import stream.Bid;
import stream.BidAdditionalInfo;
import stream.Message;
import stream.MessageAdditionalInfo;

import java.util.*;

@Getter @Setter
public class CloseBidModel extends BiddingModel {

    /*
    How this works:
    1. Filter out to get the list of offers for close bid, provided by tutors
    2. Take count = number of close bid offers
    3. Based on this count, we try to map each close bid message (by tutor) to a message (by student)
       Case 3a: If there is a message by student, then construct the pair:
        - with the student's message
        - with tutor's message

        Case 3b: If there is no message by student, then construct the pair:
        - without the student's message
        - with tutor's message

       Note: all messages will be patched rather than add into the list, ensuring no duplication of messages

    What is inside:
    closeBidOffers: contains the bidInfos (of Message) to be displayed in CloseBidView
    closeBidMessages: contains the bidInfo (of Message) to be displayed in ViewOffer (for both student and tutor)

    Note that, size(closeBidOffers) = size(closeBidMessages)
    - closeBidOffers.get(0) = closeBidMessages.get(0).getTutorMsg
    - closeBidOffers.get(1) = closeBidMessages.get(1).getTutorMsg
    - ..
    This allows us to map which view offer is selected in the view, to obtain the corresponding message pair
    Eg: Student press "View Offer 1", then it gets the closeBidMessages.get(0).getTutorMsg
     */

    private List<MessageBidInfo> closeBidOffers;
    private List<MessagePair> closeBidMessages; // map of tutor to student's message

    public CloseBidModel() {
        closeBidOffers = new ArrayList<>();
        closeBidMessages = new ArrayList<>();
    }

    @Override
    public void createBid(String userId, BidPreference bp) {
        BidAdditionalInfo bidAdditionalInfo = new BidAdditionalInfo(bp);
        Date dateCreated = new Date();
        String subjectId = getSubjectApi().getAllSubjects().stream()
                .filter(s -> s.getName().equals(bp.getSubject()))
                .findFirst()
                .orElse(null) // null guarantee to not occur as view selected is from a list of available subjects
                .getId();
        Bid bid = new Bid("Close", userId, dateCreated, subjectId, bidAdditionalInfo);
        Bid bidCreated = getBidApi().addBid(bid); // post BID
        setBidId(bidCreated.getId()); // set ID for future references
        setUserId(userId);
    }

    @Override
    public void refresh() {
        Bid bid = getBidApi().getBid(getBidId());
        List<Message> messages = bid.getMessages();
        List<MessageBidInfo> messageBidInfos = new ArrayList<>();
        for (Message m: messages) {
            if (!m.getPoster().getId().equals(getUserId())) {
                messageBidInfos.add(convertObject(m));
            }
        }
        closeBidOffers.addAll(messageBidInfos);

        BidPreference sp = bid.getAdditionalInfo().getBidPreference();
        BidInfo spInfo = sp.getPreferences();
        for (MessageBidInfo tutorBidMessage: messageBidInfos) {
            String tutorId = tutorBidMessage.getInitiatorId();
            Message studentMessage = null;
            for (Message m: messages) {
                // Filter out Message that sent to tutor (whose target is tutor)
                if (m.getAdditionalInfo().getReceiverId().equals(tutorId)) {
                    studentMessage = m;
                    break;
                }
            }
            // Case 3a: construct pair with existing message, use studentMessage to construct
            MessageBidInfo studentBidMessage;
            if (studentMessage != null) {
                studentBidMessage = new MessageBidInfo(studentMessage.getPoster().getId(), spInfo.getDay(),
                        spInfo.getTime(), spInfo.getDuration(), spInfo.getRate(), spInfo.getNumberOfSessions(),
                        studentMessage.getContent());
            // Case 3b: construct pair with no message, use bid preference to construct
            } else {
                studentBidMessage = new MessageBidInfo(spInfo.getInitiatorId(), spInfo.getDay(),
                        spInfo.getTime(), spInfo.getDuration(), spInfo.getRate(), spInfo.getNumberOfSessions(),
                        "");
            }
            closeBidMessages.add(new MessagePair(tutorBidMessage, studentBidMessage));
        }
//        notifyObservers();
    }

    private MessageBidInfo convertObject(Message message) {
        String initiatorId = message.getPoster().getId();
        String content = message.getContent();
        MessageAdditionalInfo info = message.getAdditionalInfo();
        return new MessageBidInfo(initiatorId, info.getDay(), info.getTime(), info.getDuration(), info.getRate(),
                info.getNumberOfSessions(), info.getFreeLesson(), content);
    }





}
