package model.offering;

import api.BidApi;
import entity.BidInfo;
import entity.MessageBidInfo;
import entity.MessagePair;
import lombok.Data;
import stream.Bid;
import stream.Message;
import stream.MessageAdditionalInfo;

import java.util.List;

@Data
public class CloseOffersModel {
    private BidApi bidApi;
	private String bidId;
    private String userId;
    private Bid bid;
    private MessagePair messagePair;

    public CloseOffersModel(String bidId, String userId){
    	this.bidId = bidId;
    	this.userId = userId;
    	this.bidApi = new BidApi();
    }


public void refresh() {
	this.bid = null;

	// Getting the correct bid based on id
	List<Bid> bids = bidApi.getAllBids();
	for (Bid b: bids) {
		if (b.getId().equals(this.getBidId())){
		    this.bid = b;
	    }
	}
	messagePair = getCloseOffers(this.bid);
}

private MessagePair getCloseOffers(Bid bidSelected) {
        String studentMessageId;
        String tutorMessageId;
        // Look for student's message to me (tutor)
        Message studentMessage = null;
        List<Message> messages = bidSelected.getMessages();
        for (Message m: messages) {
            if (m.getAdditionalInfo().getReceiverId().equals(userId)) {
                studentMessage = m;
                break;
            }
        }
        /*
        Possible scenarios:
        1. Student has sent a message to tutor
        - a MessageBidInfo is created with the message sent

        2. Student has not sent a message to tutor yet
        - a MessageBidInfo is created with no message and student's bid preferences
         */
        MessageBidInfo studentBidMessage;
        BidInfo spInfo = bidSelected.getAdditionalInfo().getBidPreference().getPreferences();
        if (studentMessage != null) {
            studentBidMessage = new MessageBidInfo(studentMessage.getPoster().getId(), spInfo.getDay(),
                    spInfo.getTime(), spInfo.getDuration(), spInfo.getRate(), spInfo.getNumberOfSessions(),
                    studentMessage.getContent());
            studentMessageId = studentMessage.getId();
        } else {
            studentBidMessage = new MessageBidInfo(spInfo.getInitiatorId(), spInfo.getDay(),
                    spInfo.getTime(), spInfo.getDuration(), spInfo.getRate(), spInfo.getNumberOfSessions(),
                    "");
            studentMessageId = null;
        }

        // Look for outgoing message from me to the student
        Message tutorMessage = null;
        for (Message m: messages) {
            if (m.getPoster().getId().equals(userId)) {
                tutorMessage = m;

                break;
            }
        }

        MessageBidInfo tutorBidMessage = null;
        if (tutorMessage != null) {
            MessageAdditionalInfo info = tutorMessage.getAdditionalInfo();
            tutorBidMessage = new MessageBidInfo(tutorMessage.getPoster().getId(), info.getDay(), info.getTime(),
                    info.getDuration(), info.getRate(), info.getNumberOfSessions(), info.getFreeLesson(),
                    tutorMessage.getContent());
            tutorMessageId = tutorMessage.getId();
        } else{
            tutorMessageId = null;
        }
    return new MessagePair(tutorMessageId, tutorBidMessage, studentMessageId, studentBidMessage);
    }
}