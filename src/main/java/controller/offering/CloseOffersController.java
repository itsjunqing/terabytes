package controller.offering;

import entity.MessageBidInfo;
import model.offering.CloseOffersModel;
import view.form.CloseReply;
import view.offering.CloseOfferView;

import java.awt.event.ActionEvent;

public class CloseOffersController {

    private CloseOffersModel closeOffersModel;
    private CloseOfferView closeOfferView;

    public CloseOffersController(String userId, String bidId) {
        this.closeOffersModel = new CloseOffersModel(userId, bidId);
        this.closeOfferView = new CloseOfferView(closeOffersModel);
//        closeOffersModel.oSubject.attach(closeOfferView)
        this.closeOffersModel.attach(closeOfferView);
        listenViewActions();
    }

    private void listenViewActions() {
        closeOfferView.getRefreshButton().addActionListener(this::handleRefresh);
        closeOfferView.getRespondMessageButton().addActionListener(this::handleRespondMessage);
    }

    private void handleRefresh(ActionEvent e) {
        closeOffersModel.refresh();
        closeOfferView.getRefreshButton().addActionListener(this::handleRefresh);
        closeOfferView.getRespondMessageButton().addActionListener(this::handleRespondMessage);
    }

    private void handleRespondMessage(ActionEvent e) {
        CloseReply closeReply = new CloseReply();
        closeReply.getSendReplyButton().addActionListener(e1 -> handleBidInfo(e1, closeReply));
    }

    private void handleBidInfo(ActionEvent e, CloseReply closeReply) {
        try {
            MessageBidInfo messageBidInfo = extractCloseReplyInfo(closeReply);
            System.out.println("Extracted: " + messageBidInfo);
            closeReply.dispose();
            closeOffersModel.sendMessage(messageBidInfo);

        } catch (NullPointerException exception) {
            // TODO : add error message for incomplete forms
        }
    }

    private MessageBidInfo extractCloseReplyInfo(CloseReply closeReplyForm) {
        String tutorId = closeOffersModel.getUserId();
        String time = closeReplyForm.getTime();
        String day = closeReplyForm.getDayBox();
        int duration = closeReplyForm.getDurationBox();
        int rate = closeReplyForm.getRateField();
        int numberOfSessions = closeReplyForm.getNumOfSessionBox();
        boolean freeLesson = closeReplyForm.getFreeLessonBox();
        String message = closeReplyForm.getMessageText();
        return new MessageBidInfo(tutorId, day, time, duration, rate, numberOfSessions, freeLesson, message);
    }

}
