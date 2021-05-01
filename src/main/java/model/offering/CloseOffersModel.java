package model.offering;

import api.BidApi;
import entity.BidInfo;
import entity.MessageBidInfo;
import entity.MessagePair;
import lombok.Data;
import model.CheckExpired;
import observer.OSubject;
import stream.Bid;
import stream.Message;
import stream.MessageAdditionalInfo;

@Data
public class CloseOffersModel {

    private String userId;
    private String bidId;
    private BidApi bidApi;
    private MessagePair messagePair;
    public OSubject oSubject;
    protected String errorText;



    public CloseOffersModel(String userId, String bidId) {
        this.userId = userId;
        this.bidId = bidId;
    	this.bidApi = new BidApi();
        this.errorText = "";
        oSubject = new OSubject();

        refresh();
    }

    public void refresh() {
        Bid bid = bidApi.getBid(bidId);
        BidInfo bidInfo = bid.getAdditionalInfo().getBidPreference().getPreferences();
        CheckExpired checkExpired = new CheckExpired();
        if (!checkExpired.checkIsExpired(bid)){
            // Student's message sent to me
            String studentMsgId = null;
            Message studentMsg = bid.getMessages().stream()
                                    .filter(m -> m.getAdditionalInfo().getReceiverId().equals(userId))
                                    .findFirst()
                                    .orElse(null);
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

            // Tutor's message to student
            String tutorMsgId = null;
            Message tutorMsg = bid.getMessages().stream()
                                    .filter(m -> m.getPoster().getId().equals(userId))
                                    .findFirst()
                                    .orElse(null);
            MessageBidInfo tutorBidMessage = null;
            if (tutorMsg != null) {
                tutorMsgId = tutorMsg.getId();
                MessageAdditionalInfo info = tutorMsg.getAdditionalInfo();
                tutorBidMessage = new MessageBidInfo(tutorMsg.getPoster().getId(), info.getDay(), info.getTime(),
                        info.getDuration(), info.getRate(), info.getNumberOfSessions(), info.getFreeLesson(),
                        tutorMsg.getContent());
            }

            messagePair = new MessagePair(tutorMsgId, tutorBidMessage, studentMsgId, studentBidMessage);
        }
        else {

        }
        oSubject.notifyObservers();
    }

    public void sendMessage(MessageBidInfo messageBidInfo) {
        // TODO: similar to bottom
        String tutorMsgId = messagePair.getStudentMsgId();
        if (tutorMsgId == null) {
//            Message message = new Message(messageBidInfo.getContent(), )
            // convert messageBidInfo to MessageAdditionalInfo
            // create Message object
            // add message to api
        } else {
            // convert messageBidInfo to MessageAdditionalInfo
            // create Message object
            // patch message to api
        }


//
//
//        BidAdditionalInfo info = bidApi.getBid(bidId).getAdditionalInfo();
//        BidInfo currentBidInfo = info.getBidOffers().stream()
//                .filter(i -> i.getInitiatorId().equals(userId))
//                .findFirst()
//                .orElse(null);
//        // if the tutor has provided an offer before, remove the offer
//        if (currentBidInfo != null) {
//            info.getBidOffers().remove(currentBidInfo);
//        }
//        info.getBidOffers().add(bidInfo);
//        bidApi.patchBid(bidId, new Bid(info));

    }
    public void setErrorText(String errorText){
        this.errorText = errorText;
    }

}