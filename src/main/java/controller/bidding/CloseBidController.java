package controller.bidding;

import entity.BidPreference;
import entity.MessagePair;
import model.bidding.CloseBidModel;
import stream.Contract;
import view.bidding.CloseBidView;
import view.bidding.CloseMessageView;
import view.form.ReplyMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class CloseBidController extends BiddingController {

    private CloseBidModel closeBidModel;
    private CloseBidView closeBidView;

    /**
     * Constructor to create a CloseBid
     *
     * @param userId
     * @param bp
     */
    public CloseBidController(String userId, BidPreference bp) {
        SwingUtilities.invokeLater(() -> {
            this.closeBidModel = new CloseBidModel(userId, bp);
            this.closeBidView = new CloseBidView(closeBidModel);
            this.closeBidModel.attach(closeBidView);
            listenViewActions();
        });
    }

    /**
     * Constructor for CloseBid in progress
     *
     * @param userId
     */
    public CloseBidController(String userId) {
        SwingUtilities.invokeLater(() -> {
            this.closeBidModel = new CloseBidModel(userId);
            this.closeBidView = new CloseBidView(closeBidModel);
            this.closeBidModel.attach(closeBidView);
            listenViewActions();
        });

    }

    @Override
    public void listenViewActions() {
        closeBidView.getRefreshButton().addActionListener(this::handleRefresh);
        closeBidView.getViewMessageButton().addActionListener(this::handleViewMessage);
        closeBidView.getSelectOfferButton().addActionListener(this::handleOfferSelection);
    }

    private void handleRefresh(ActionEvent e) {
        System.out.println("From CloseBidController: Refresh is clicked");
        closeBidModel.refresh();
    }

    private void handleViewMessage(ActionEvent e) {
        System.out.println("From CloseBidController: View Message is clicked");
        try {
            int selection = closeBidView.getOfferSelection();
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
                        replyMessage.dispose();
                        closeMessageView.dispose();
                    });
                });
            }

        } catch (NullPointerException ef) {
            closeBidView.getErrorLabel().setText("No offers selected!");
        }
    }

    private void handleOfferSelection(ActionEvent e) {
        System.out.println("From CloseBidController: Offer selection is clicked");
        try {
            int selection = closeBidView.getOfferSelection();
            Contract contract = closeBidModel.offerSelection(selection);
            if (contract != null) {
                handleContract(contract);
                closeBidView.dispose();
            }
        } catch (NullPointerException ef) {
            closeBidView.getErrorLabel().setText("No offers selected!");
        }

    }
}


//        else {;
//        }
//        System.out.println("From CloseBidController: Offer selection is clicked");
//        int selection = closeBidView.getOfferSelection();
//        Bid currentBid = closeBidModel.getBid();
//        MessageBidInfo messageBidInfo = closeBidModel.getCloseBidOffers().get(selection-1);
//        System.out.println("From CloseBidController: Selected offer = " + messageBidInfo.toString());
//        closeBidModel.markBidClose(); // mark bid as close
//        createContract(currentBid, messageBidInfo); // create contract
//        closeBidView.dispose(); // remove view after select offer


