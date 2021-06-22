package controller.offering;

import controller.EventListener;
import entity.MessageBidInfo;
import model.offering.CloseOffersModel;
import view.form.CloseReply;
import view.offering.CloseOfferView;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * A Class of CloseOffersController to control close offering's dashboard actions
 */
public class CloseOffersController implements EventListener {

    private CloseOffersModel closeOffersModel;
    private CloseOfferView closeOfferView;

    /**
     * Constructs a CloseOffersController
     * @param userId a String of user id
     * @param bidId a String of bid id
     */
    public CloseOffersController(String userId, String bidId) {
        this.closeOffersModel = new CloseOffersModel(userId, bidId);
        SwingUtilities.invokeLater(() -> {
            this.closeOfferView = new CloseOfferView(closeOffersModel);
            this.closeOffersModel.attach(closeOfferView);
            listenViewActions();
        });

    }

    /**
     * Listens to actions on the dashboard
     */
    public void listenViewActions() {
        closeOfferView.getRefreshButton().addActionListener(this::handleRefresh);
        closeOfferView.getRespondMessageButton().addActionListener(this::handleRespondMessage);
    }

    /**
     * Handles dashboard refreshing
     */
    private void handleRefresh(ActionEvent e) {
        closeOffersModel.refresh();
    }

    /**
     * Handles responding message of the corresponding Bid
     */
    private void handleRespondMessage(ActionEvent e) {
        CloseReply closeReply = new CloseReply();
        closeReply.getSendCloseReplyButton().addActionListener(e1 -> handleBidInfo(e1, closeReply));
    }

    /**
     * Handles the sending of message for close offers
     */
    private void handleBidInfo(ActionEvent e, CloseReply closeReply) {
        try {
            MessageBidInfo messageBidInfo = extractCloseReplyInfo(closeReply);
            System.out.println("Extracted: " + messageBidInfo);
            closeOffersModel.sendMessage(messageBidInfo);
            closeReply.dispose();
        } catch (NullPointerException exception) {
            closeReply.getErrorLabel().setText("Incomplete form!");
        }
    }

    /**
     * Returns the MessageBidInfo extracted from CloseReply form
     * representing the information sent from the tutor to the student
     * @param closeReplyForm a CloseReply view
     * @return a MessageBidInfo
     */
    private MessageBidInfo extractCloseReplyInfo(CloseReply closeReplyForm) throws NullPointerException {
        String tutorId = closeOffersModel.getUserId();
        String time = closeReplyForm.getTimeBox();
        String day = closeReplyForm.getDayBox();
        int duration = closeReplyForm.getDurationBox();
        int rate = closeReplyForm.getRateField();
        int numberOfSessions = closeReplyForm.getNumOfSessionBox();
        boolean freeLesson = closeReplyForm.getFreeLessonBox();
        String message = closeReplyForm.getMessageText();
        return new MessageBidInfo(tutorId, day, time, duration, rate, numberOfSessions, freeLesson, message);
    }

}
