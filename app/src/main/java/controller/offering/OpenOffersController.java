package controller.offering;

import controller.EventListener;
import controller.contract.ContractConfirmController;
import entity.BidInfo;
import model.offering.OpenOffersModel;
import stream.Contract;
import view.ViewUtility;
import view.form.OpenReply;
import view.offering.OpenOffersView;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * A Class of OpenOffersController to control actions on the Open Offers dashboard
 */
public class OpenOffersController implements EventListener {

    private OpenOffersModel openOffersModel;
    private OpenOffersView openOffersView;

    /**
     * Constructs a OpenOffersControrller
     * @param userId a String of user id
     * @param bidId a String of bid id
     */
    public OpenOffersController(String userId, String bidId) {
        this.openOffersModel = new OpenOffersModel(userId, bidId);
        SwingUtilities.invokeLater(() -> {
            this.openOffersView = new OpenOffersView(openOffersModel);
            this.openOffersModel.attach(openOffersView);
            listenViewActions();
        });
    }

    /**
     * Listens to actions on the dashboard
     */
    public void listenViewActions() {
        openOffersView.getRefreshButton().addActionListener(this::handleRefresh);
        openOffersView.getRespondButton().addActionListener(this::handleRespond);
        openOffersView.getBuyOutButton().addActionListener(this::handleBuyOut);
    }

    /**
     * Handles dashboard refreshing
     */
    private void handleRefresh(ActionEvent e) {
        openOffersModel.refresh();
    }

    /**
     * Handles the request to respond a Bid by providing an offer.
     */
    private void handleRespond(ActionEvent e) {
        OpenReply openReply = new OpenReply();
        openReply.getSendOpenReplyButton().addActionListener(e1 -> handleBidInfo(e1, openReply));
    }

    /**
     * Handles the sending of offer for open offers
     */
    private void handleBidInfo(ActionEvent e, OpenReply openReplyForm) {
        try {
            BidInfo bidInfo = extractOpenReplyInfo(openReplyForm);
            System.out.println("Extracted: " + bidInfo);
            openOffersModel.respond(bidInfo);
            openReplyForm.dispose();

        } catch (NullPointerException exception) {
            openReplyForm.getErrorLabel().setText("Form is incomplete!");
        }
    }

    /**
     * Handles the request of buying out a bid
     */
    private void handleBuyOut(ActionEvent e) {
        // Get preferences -> Form contract to be posted to API
        Contract contract = openOffersModel.buyOut();
        // Get contract duration -> post -> sign -> dispose
        new ContractConfirmController(contract, ViewUtility.TUTOR_CODE, true);
        openOffersView.dispose();
    }

    /**
     * Returns the BidInfo extracted from OpenReply form
     * representing the information sent from tutor to the student
     * @param openReplyForm a OpenReply form
     * @return a BidInfo object
     */
    private BidInfo extractOpenReplyInfo(OpenReply openReplyForm) throws NullPointerException {
        String tutorId = openOffersModel.getUserId();
        String time = openReplyForm.getTimeBox();
        String day = openReplyForm.getDayBox();
        int duration = openReplyForm.getDurationBox();
        int rate = openReplyForm.getRateField();
        int numberOfSessions = openReplyForm.getNumOfSessionBox();
        boolean freeLesson = openReplyForm.getFreeLessonBox();
        return new BidInfo(tutorId, time, day, duration, rate, numberOfSessions, freeLesson);
    }

}
