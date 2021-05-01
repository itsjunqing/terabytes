package model.offering;

import api.BidApi;
import api.MessageApi;
import entity.BidInfo;
import entity.MessageBidInfo;
import entity.MessagePair;
import lombok.Getter;
import service.ExpiryService;
import observer.OSubject;
import stream.Bid;
import stream.Message;
import stream.MessageAdditionalInfo;

import java.util.Date;

@Getter
public class CloseOffersModel extends OSubject {

    private String userId;
    private String bidId;
    private BidApi bidApi;
    private MessageApi messageApi;
    private MessagePair messagePair;
    private boolean expired;

    public CloseOffersModel(String userId, String bidId) {
        this.userId = userId;
        this.bidId = bidId;
    	this.bidApi = new BidApi();
    	this.messageApi = new MessageApi();
    	this.expired = true;
        refresh();
    }

    public void refresh() {
        Bid bid = bidApi.getBid(bidId);
        BidInfo bidInfo = bid.getAdditionalInfo().getBidPreference().getPreferences();
        ExpiryService expiryService = new ExpiryService();
        if (!expiryService.checkIsExpired(bid)){
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
            expired = true;
        }
        notifyObservers();

    }

    private MessageAdditionalInfo convertObject(MessageBidInfo messageBidInfo) {
        return new MessageAdditionalInfo(messageBidInfo.getInitiatorId(), messageBidInfo.getDay(), messageBidInfo.getTime(),
                messageBidInfo.getDuration(), messageBidInfo.getRate(), messageBidInfo.getNumberOfSessions(),
                messageBidInfo.isFreeLesson());
    }

    public void sendMessage(MessageBidInfo messageBidInfo) {
        String tutorMsgId = messagePair.getStudentMsgId();
        if (tutorMsgId == null) {
            Message message = new Message(bidId, userId, new Date(), messageBidInfo.getContent(), convertObject(messageBidInfo));
            messageApi.addMessage(message);
        } else {
            Message message = new Message(messageBidInfo.getContent(), convertObject(messageBidInfo));
            messageApi.patchMessage(tutorMsgId, message);
        }
    }

}