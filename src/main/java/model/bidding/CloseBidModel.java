package model.bidding;


import api.MessageApi;
import entity.BidInfo;
import entity.BidPreference;
import entity.MessageBidInfo;
import entity.MessagePair;
import lombok.Getter;
import lombok.Setter;
import stream.Bid;
import stream.Message;
import stream.MessageAdditionalInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<MessagePair> closeBidMessages;
    private MessageApi messageApi;

    /**
     * Constructor to construct a new CloseBid
     * @param userId
     * @param bp
     */
    public CloseBidModel(String userId, BidPreference bp) {
        Bid bidCreated = createBid(userId, bp, "Close");
        this.bidId = bidCreated.getId(); // set ID for future references
        this.userId = userId;
        this.closeBidOffers = new ArrayList<>();
        this.closeBidMessages = new ArrayList<>();
        this.messageApi = new MessageApi();
        refresh();
    }

    /**
     * Constructor to construct existing OpenBid
     * @param userId
     */
    public CloseBidModel(String userId) {
        Bid existingBid = extractBid(userId, "Close");
        this.bidId = existingBid.getId();
        this.userId = userId;
        this.closeBidOffers = new ArrayList<>();
        this.closeBidMessages = new ArrayList<>();
        this.messageApi = new MessageApi();
        refresh();
    }


    @Override
    public void refresh() {
        closeBidOffers.clear();
        closeBidMessages.clear();

        Bid bid = bidApi.getBid(bidId);
        BidInfo bidInfo = bid.getAdditionalInfo().getBidPreference().getPreferences();

        // Get the Messages where the initiator is a tutor
        // TODO: does using receiverId.equals(userId) will work too?
        List<Message> tutorMessages = bid.getMessages().stream()
                .filter(m -> !m.getPoster().getId().equals(userId))
                .collect(Collectors.toList());

        for (Message tutorMsg: tutorMessages) {
            // Tutor's MessageBidInfo
            String tutorMsgId = tutorMsg.getId();
            MessageBidInfo tutorBidMessage = convertObject(tutorMsg);

            // Student's Message (if a Message has been posted or null))
            // Disclaimer: must use receiverId because student can send to many tutors
            String tutorId = tutorMsg.getPoster().getId();
            Message studentMsg = bid.getMessages().stream()
                                        .filter(m -> m.getAdditionalInfo().getReceiverId().equals(tutorId))
                                        .findFirst()
                                        .orElse(null);

            // Convert Student's Message to MessageBidInfo
            String studentMsgId = null;
            MessageBidInfo studentBidMessage;
            if (studentMsg == null) {
                studentBidMessage = new MessageBidInfo(bidInfo.getInitiatorId(), bidInfo.getDay(),
                        bidInfo.getTime(), bidInfo.getDuration(), bidInfo.getRate(), bidInfo.getNumberOfSessions(),
                        "");
            } else {
                studentMsgId = studentMsg.getId();
                studentBidMessage = new MessageBidInfo(studentMsg.getPoster().getId(), bidInfo.getDay(),
                        bidInfo.getTime(), bidInfo.getDuration(), bidInfo.getRate(), bidInfo.getNumberOfSessions(),
                        studentMsg.getContent());
            }

            // tutorMsg exists -> store along with tutorMsgId
            // studentMsg exist -> store along with updated studentMsgId
            // studentMsg no exist -> store along with null
            closeBidMessages.add(new MessagePair(tutorMsgId, tutorBidMessage, studentMsgId, studentBidMessage));

            // update closedBidOffers to be displayed in CloseBidView
            closeBidOffers.add(tutorBidMessage);

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

    public void sendMessage(MessagePair messagePair, String stringMsg) {
        // Construct Message to send to tutor (sender is the corresponding tutor of the MessagePair)
        String tutorId = messagePair.getTutorMsg().getInitiatorId();
        MessageAdditionalInfo info = new MessageAdditionalInfo(tutorId);

        // Get the student's message id that exist (or null)
        String studentMsgId = messagePair.getStudentMsgId();

        // If Student has sent not sent a Message, construct a new Message
        if (studentMsgId == null) {
            Message message = new Message(bidId, userId, new Date(), stringMsg, info);
            messageApi.addMessage(message);
        // If Student has sent a Message before, edit the Message
        } else {
            Message message = new Message(stringMsg, info);
            messageApi.patchMessage(studentMsgId, message);
        }
    }


    /*
    OLD CODE
     */

//    Bid bid = getBidApi().getBid(getBidId());
//    List<Message> messages = bid.getMessages();
//    List<MessageBidInfo> messageBidInfos = new ArrayList<>();
//
//    // get the Messages where the initiator (poster) is a tutor
//        for (Message m: messages) {
//        if (!m.getPoster().getId().equals(getUserId())) {
//            messageBidInfos.add(convertObject(m));
//        }
//    }
//        closeBidOffers.addAll(messageBidInfos);
//
//    // get the Messages where the initiator (poster) is me myself (a student)
//    BidPreference sp = bid.getAdditionalInfo().getBidPreference();
//    BidInfo spInfo = sp.getPreferences();
//        for (MessageBidInfo tutorBidMessage: messageBidInfos) {
//        String tutorId = tutorBidMessage.getInitiatorId();
//        Message studentMessage = null;
//        for (Message m: messages) {
//            // Filter out Message that sent to tutor (whose target is tutor)
//            if (m.getAdditionalInfo().getReceiverId().equals(tutorId)) {
//                studentMessage = m;
//                break;
//            }
//        }
//        // Case 3a: construct pair with existing message, use studentMessage to construct
//        MessageBidInfo studentBidMessage;
//        if (studentMessage != null) {
//            studentBidMessage = new MessageBidInfo(studentMessage.getPoster().getId(), spInfo.getDay(),
//                    spInfo.getTime(), spInfo.getDuration(), spInfo.getRate(), spInfo.getNumberOfSessions(),
//                    studentMessage.getContent());
//            // Case 3b: construct pair with no message, use bid preference to construct
//        } else {
//            studentBidMessage = new MessageBidInfo(spInfo.getInitiatorId(), spInfo.getDay(),
//                    spInfo.getTime(), spInfo.getDuration(), spInfo.getRate(), spInfo.getNumberOfSessions(),
//                    "");
//        }
//        closeBidMessages.add(new MessagePair(tutorBidMessage, studentBidMessage));
//    }
//        notifyObservers();


}
