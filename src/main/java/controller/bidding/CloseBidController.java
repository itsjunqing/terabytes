package controller.bidding;

import entity.BidPreference;
import entity.MessageBidInfo;
import entity.MessagePair;
import model.bidding.CloseBidModel;
import stream.Bid;
import view.bidding.CloseBidView;
import view.bidding.CloseMessageView;
import view.form.ReplyMessage;

import java.awt.event.ActionEvent;


public class CloseBidController extends BiddingController {

    private CloseBidModel closeBidModel;
    private CloseBidView closeBidView;
    /**
     * Constructor to create a CloseBid
     * @param userId
     * @param bp
     */
    public CloseBidController(String userId, BidPreference bp) {
        this.closeBidModel = new CloseBidModel(userId, bp);
        this.closeBidView= new CloseBidView(closeBidModel);
        this.closeBidModel.attach(closeBidView);
        listenViewActions();
    }

    /**
     * Constructor for CloseBid in progress
     * @param userId
     */
    public CloseBidController(String userId) {
        this.closeBidModel = new CloseBidModel(userId);
        this.closeBidView = new CloseBidView(closeBidModel);
        this.closeBidModel.attach(closeBidView);
        listenViewActions();
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
        closeBidView.getRefreshButton().addActionListener(this::handleRefresh);
        closeBidView.getViewMessageButton().addActionListener(this::handleViewMessage);
        closeBidView.getSelectOfferButton().addActionListener(this::handleOfferSelection);
    }

    private void handleViewMessage(ActionEvent e) {
        System.out.println("From CloseBidController: View Message is clicked");
        int selection = closeBidView.getOfferSelection();
        MessagePair messagePair = closeBidModel.getCloseBidMessages().get(selection-1);

        CloseMessageView closeMessageView = new CloseMessageView(messagePair);
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

    private void handleOfferSelection(ActionEvent e) {
        System.out.println("From CloseBidController: Offer selection is clicked");
        int selection = closeBidView.getOfferSelection();
        Bid currentBid = closeBidModel.getBid();
        MessageBidInfo messageBidInfo = closeBidModel.getCloseBidOffers().get(selection-1);
        System.out.println("From CloseBidController: Selected offer = " + messageBidInfo.toString());
        closeBidModel.markBidClose(); // mark bid as close
        createContract(currentBid, messageBidInfo); // create contract
        closeBidView.dispose(); // remove view after select offer
    }


}
