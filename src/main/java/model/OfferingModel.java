package model;

import api.BidApi;
import api.UserApi;
import entity.BidInfo;
import entity.BidPreference;
import entity.MessageBidInfo;
import entity.MessagePair;
import lombok.Getter;
import lombok.Setter;
import observer.OSubject;
import stream.Bid;
import stream.Message;
import stream.MessageAdditionalInfo;
import stream.User;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OfferingModel extends OSubject {

    private String userId;
    private UserApi userApi;
    private BidApi bidApi;
    private List<Bid> bidsOnGoing;

    public OfferingModel(String userId) {
        this.userId = userId; // only Tutor can initialize this, so User is a tutor
        this.userApi = new UserApi();
        this.bidApi = new BidApi();
        this.bidsOnGoing = new ArrayList<>();
    }

    public void refresh() {
        bidsOnGoing.clear(); // for memory cleaning
        User currentUser = userApi.getUser(userId);
        List<Bid> bids = bidApi.getAllBids();
        for (Bid b: bids) {
            boolean bidIsClosed = b.getDateClosedDown() != null;
            if (!bidIsClosed) {
                BidPreference bp = b.getAdditionalInfo().getBidPreference();
                boolean hasQualification = currentUser.getQualifications().stream()
                        .anyMatch(q -> q.getTitle().equals(bp.getQualification().toString()));

                // Bonus mark on checking 2 levels higher for competency requirement
                boolean hasCompetency = currentUser.getCompetencies().stream()
                        .anyMatch(c -> c.getLevel() - 2 >= bp.getCompetency()
                                && c.getSubject().getName().equals(bp.getSubject()));
                if (hasQualification && hasCompetency) {
                    bidsOnGoing.add(b);
                }
            }
        }
//        notifyObservers();
    }

    public List<BidInfo> getOpenOffers(int selectionId) {
        return bidsOnGoing.get(selectionId).getAdditionalInfo().getBidOffers();
    }

    public MessagePair getCloseOffers(int selectionId) {
        Bid bidSelected = bidsOnGoing.get(selectionId);
        List<Message> messages = bidSelected.getMessages();

        // Look for student's message to me (tutor)
        Message studentMessage = null;
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
        } else {
            studentBidMessage = new MessageBidInfo(spInfo.getInitiatorId(), spInfo.getDay(),
                    spInfo.getTime(), spInfo.getDuration(), spInfo.getRate(), spInfo.getNumberOfSessions(),
                    "");
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
        }
        return new MessagePair(tutorBidMessage, studentBidMessage);
    }

    public String getUserName(String Id){
        UserApi userApi = new UserApi();
        User user = userApi.getUser(Id);
        String givenName = user.getGivenName();
        String familyName = user.getFamilyName();
        return givenName + " " + familyName;
    }




    public void sendOffer(int bidIndex, BidInfo bidInfo) {
//        Bid bidChosen = getOpenBids().get(bidIndex);
//        BidAdditionalInfo bidAdditionalInfo = bidChosen.getAdditionalInfo();
//        bidAdditionalInfo.getBidOffers().add(bidInfo); // add offer
//
//        // can only patch bid because api doesn't allow adding of information of the additionalInfo attribute
//        bidApi.patchBid(bidChosen.getId(), new Bid(bidAdditionalInfo));
    }

    public void sendMessage(int bidIndex, BidInfo bidInfo) {
//        Bid bidChosen = getCloseBids().get(bidIndex);
//        String posterId = bidInfo.getInitiatorId();
//        Date datePosted = new Date();
//
//        // content will not be used, will use BidMessageInfo entirely
//        Message message = new Message(bidChosen.getId(), posterId, datePosted, "", bidInfo);
//        messageApi.addMessage(message);
    }






}
