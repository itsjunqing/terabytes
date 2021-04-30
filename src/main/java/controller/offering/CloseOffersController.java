package controller.offering;

import entity.MessageBidInfo;
import model.offering.CloseOffersModel;
import view.form.ReplyBid;
import view.offering.CloseOfferView;

import java.awt.event.ActionEvent;

public class CloseOffersController {

    private CloseOffersModel closeOffersModel;
    private CloseOfferView closeOfferView;
    private String bidId;
    private String userId;
    private ReplyBid replyBid;

    public CloseOffersController(String bidId, String userId) {
       this.closeOffersModel = new CloseOffersModel(bidId, userId);
       closeOffersModel.refresh();
       this.closeOfferView = new CloseOfferView(closeOffersModel);
       listenViewActions();
    }

    private void listenViewActions() {
        closeOfferView.getRefreshButton().addActionListener(this::handleRefresh);
        closeOfferView.getRespondMessageButton().addActionListener(this::handleRespondMessage);
    }

    private void handleRefresh(ActionEvent e) {

    }

    private void handleRespondMessage(ActionEvent e) {
        replyBid = new ReplyBid();
        replyBid.getSendReplyButton().addActionListener(this::handleBidForm);
    }


    private void handleBidForm(ActionEvent e){
        ReplyBid replyBidForm = new ReplyBid();
        replyBidForm.getSendReplyButton().addActionListener(this::handleCreateMessage);
    }

    private void handleCreateMessage(ActionEvent e){
        try {
            MessageBidInfo messageBidInfo = extractMessageInfo(replyBid);
            System.out.println("Extracted: " + messageBidInfo);
            initiateCloseOffer(messageBidInfo);
            replyBid.dispose();
        } catch (NullPointerException exception) {
            // TODO: Add error message in UI on incomplete forms, similar to login
        }
    }

    public MessageBidInfo extractMessageInfo(ReplyBid replyBidForm) {
        String tutorId = closeOffersModel.getUserId();
        String time = replyBidForm.getTime();
        String day = replyBidForm.getDay();
        int duration = replyBidForm.getDuration();
        int rate = replyBidForm.getRate();
        int numberOfSessions = replyBidForm.getNumSessions();
        boolean freeLesson = replyBidForm.getFreeLesson();
        String message = replyBidForm.getReplyMessage();

        return new MessageBidInfo(tutorId, day, time, duration, rate, numberOfSessions, freeLesson, message);
    }
    public void initiateCloseOffer(MessageBidInfo messageBidInfo){
//        offeringModel.sendMessage(selectedBid, messageInfo);
    };
}
