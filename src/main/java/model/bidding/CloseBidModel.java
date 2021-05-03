package model.bidding;


import entity.BidInfo;
import entity.BidPreference;
import entity.MessageBidInfo;
import entity.MessagePair;
import lombok.Getter;
import lombok.Setter;
import service.ApiService;
import service.BuilderService;
import stream.Bid;
import stream.Contract;
import stream.Message;
import stream.MessageAdditionalInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class of CloseBidModel that stores the data of a Close Bidding.
 * Contains the list of messages involved within the Close Bid.
 */
@Getter @Setter
public class CloseBidModel extends BiddingModel {

    private List<MessageBidInfo> closeBidOffers;
    private List<MessagePair> closeBidMessages;

    /**
     * Constructor to construct a new CloseBidModel with a new Bid initiated.
     * @param userId a String of user id
     * @param bp a BidPreference object
     */
    public CloseBidModel(String userId, BidPreference bp) {
        Bid bid = BuilderService.buildBid(userId, bp, "Close");
        Bid bidCreated = ApiService.bidApi().add(bid);
        initModel(userId, bidCreated);
    }

    /**
     * Constructor to construct a CloseBidModel based on the existing on-going Bid.
     * @param userId a String of user id
     */
    public CloseBidModel(String userId) {
        Bid existingBid = extractBid(userId, "Close");
        initModel(userId, existingBid);
    }

    /**
     * Initializes the model attributes.
     * @param userId a String of user id
     * @param bid a Bid object (created or extracted)
     */
    private void initModel(String userId, Bid bid) {
        this.bidId = bid.getId();
        this.userId = userId;
        this.closeBidOffers = new ArrayList<>();
        this.closeBidMessages = new ArrayList<>();
        refresh();
    }

    /**
     * Refreshes the model data.
     */
    @Override
    public void refresh() {
        this.errorText = "";
        closeBidOffers.clear();
        closeBidMessages.clear();

        Bid bid = ApiService.bidApi().get(bidId);

        // check if the bid is expired, if the bid is expired, then remove the bid,
        // return an empty list, and update the error text
        if (!expiryService.checkIsExpired(bid)){
            BidInfo bidInfo = bid.getAdditionalInfo().getBidPreference().getPreferences();

            // Get the Messages where the initiator is a tutor
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
        } else {
            closeBidOffers.clear();
            closeBidMessages.clear();
            errorText = "This Bid has expired, please make a new one";
        }
        oSubject.notifyObservers();
    }


    /**
     * Constructs a Message to send to tutor.
     * If student has not sent a message before, a new message is posted.
     * If student has sent a message before, the message is edited (edited).
     * The display and the corresponding MessagePair is then refreshed (and updated) accordingly.
     *
     * @param messagePair a MessagePair object
     * @param stringMsg a String message
     */
    public void sendMessage(MessagePair messagePair, String stringMsg) {
        String tutorId = messagePair.getTutorMsg().getInitiatorId();
        MessageAdditionalInfo info = new MessageAdditionalInfo(tutorId);
        String studentMsgId = messagePair.getStudentMsgId(); // get the student's message id (if it has send a message before)
        if (studentMsgId == null) {
            Message message = new Message(bidId, userId, new Date(), stringMsg, info);
            ApiService.messageApi().add(message);
        } else {
            Message message = new Message(stringMsg, info);
            ApiService.messageApi().patch(studentMsgId, message);
        }
        refresh();
    }

    /**
     * Returns the MessagePair of the corresponding message received by the tutor
     * @param selection a selection index from the view
     * @return a MessagePair object
     */
    public MessagePair viewMessage(int selection) {
        if (!expiryService.checkIsExpired(getBid())){
            return closeBidMessages.get(selection-1);
        } else{
            errorText = "Bid had been closed down, please close the window";
            oSubject.notifyObservers();
            return null;
        }
    }

//    /**
//     * Selects and return an offer based on the selection option
//     * @param selection an integer selection
//     * @return a BidInfo object
//     */
//    @Override
//    public BidInfo selectOffer(int selection) {
//        BidInfo bidInfo = closeBidOffers.get(selection-1);
//        markBidClose();
//        return bidInfo;
//    }

    public Contract formContract(int selection) {
        Bid currentBid = getBid();
        BidInfo bidInfo = closeBidOffers.get(selection-1);
        markBidClose();
        if (!expiryService.checkIsExpired(currentBid)){
            return BuilderService.buildContract(currentBid, bidInfo);
        }
        errorText = "This Bid has expired or closed down, please close and refresh main page";
        oSubject.notifyObservers();
        return null;
    }

    /**
     * Converts Message to MessageBidInfo object.
     * @param message a Message object
     * @return a MessageBidInfo object
     */
    private MessageBidInfo convertObject(Message message) {
        String initiatorId = message.getPoster().getId();
        String content = message.getContent();
        MessageAdditionalInfo info = message.getAdditionalInfo();
        return new MessageBidInfo(initiatorId, info.getDay(), info.getTime(), info.getDuration(), info.getRate(),
                info.getNumberOfSessions(), info.getFreeLesson(), content);
    }

}
