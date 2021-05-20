package controller.bidding;

import entity.Preference;
import entity.MessagePair;
import model.bidding.CloseBidModel;
import stream.Contract;
import view.bidding.CloseBidView;
import view.bidding.CloseMessageView;
import view.form.ReplyMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * A Class of CloseBidController that controls the action of a close bidding dashboard
 */
public class CloseBidController extends BiddingController {

    private CloseBidModel closeBidModel;
    private CloseBidView closeBidView;

    /**
     * Constructor to create a new CloseBidController
     * @param userId a String of user id
     * @param bp the preference of the Bid
     */
    public CloseBidController(String userId, Preference bp) {
        this.closeBidModel = new CloseBidModel(userId, bp);
        SwingUtilities.invokeLater(() -> {
            this.closeBidView = new CloseBidView(closeBidModel);
            this.closeBidModel.attach(closeBidView);
            listenViewActions();
        });
    }

    /**
     * Constructor to create a CloseBidController for an on-going Close Bid
     * @param userId a String of user id
     */
    public CloseBidController(String userId) {
        this.closeBidModel = new CloseBidModel(userId);
        SwingUtilities.invokeLater(() -> {
            this.closeBidView = new CloseBidView(closeBidModel);
            this.closeBidModel.attach(closeBidView);
            listenViewActions();
        });
    }

    /**
     * Listen to dashboard actions
     */
    @Override
    public void listenViewActions() {
        closeBidView.getRefreshButton().addActionListener(this::handleRefresh);
        closeBidView.getViewMessageButton().addActionListener(this::handleViewMessage);
        closeBidView.getSelectOfferButton().addActionListener(this::handleOfferSelection);
    }

    /**
     * Handles dashboard refreshing
     */
    private void handleRefresh(ActionEvent e) {
        System.out.println("From CloseBidController: Refresh is clicked");
        closeBidModel.refresh();
    }

    /**
     * Handles the viewing of message of the Bid with the corresponding Tutor
     */
    private void handleViewMessage(ActionEvent e) {
        System.out.println("From CloseBidController: View Message is clicked");
        try {
            int selection = closeBidView.getOfferSelection();
            System.out.printf(Integer.toString(selection));
            MessagePair messagePair = closeBidModel.viewMessage(selection);
            if (messagePair != null) {
                CloseMessageView closeMessageView = new CloseMessageView(messagePair, closeBidModel.getBid());
                closeMessageView.getRespondMessageButton().addActionListener(e1 -> {
                    ReplyMessage replyMessage = new ReplyMessage();

                    // When student has pressed "Reply":
                    // Send the Message --> Close ReplyMessage window --> Close CloseMessageView window
                    replyMessage.getReplyButton().addActionListener(e2 -> {
                        String studentMsg = replyMessage.getMessageText().getText();
                        closeBidModel.sendMessage(messagePair, studentMsg);
                        closeBidModel.refresh();
                        replyMessage.dispose();
                        closeMessageView.dispose();
                    });
                });
            }
        } catch (NullPointerException ef) {
            closeBidView.getErrorLabel().setText("No offers selected!");
        }
        closeBidModel.refresh();
    }

    /**
     * Handles the selection of offer by Tutor and constructs a Contract to be established and confirmed
     */
    private void handleOfferSelection(ActionEvent e) {
        try {
            int selection = closeBidView.getOfferSelection();
            Contract contract = closeBidModel.formContract(selection);
            if (contract != null) {
                handleContract(contract);
                closeBidView.dispose();
            }
        } catch (NullPointerException ef) {
            closeBidView.getErrorLabel().setText("No offers selected!");
        }
    }
}

