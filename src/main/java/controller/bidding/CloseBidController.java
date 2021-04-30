package controller.bidding;

import entity.BidPreference;
import entity.MessageBidInfo;
import model.bidding.CloseBidModel;
import stream.Bid;
import view.bidding.CloseBidView;
import view.bidding.CloseMessageView;
import view.form.ReplyMessage;

import java.awt.event.ActionEvent;

/**
 * Remaining:
 * 1) Integration of View Message
 * 2) Integration of Contract formation
 */

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
    }

    /**
     * Constructor for CloseBid in progress
     * @param userId
     */
    public CloseBidController(String userId) {
        this.closeBidModel = new CloseBidModel(userId);
        this.closeBidView = new CloseBidView(closeBidModel);
    }


    @Override
    public void listenViewActions() {
        closeBidView.getRefreshButton().addActionListener(this::handleRefresh);
        closeBidView.getViewMessageButton().addActionListener(this::handleViewMessage);
        closeBidView.getSelectOfferButton().addActionListener(this::handleOfferSelection);
    }

    private void handleRefresh(ActionEvent e) {
        closeBidModel.refresh();
    }

    private void handleViewMessage(ActionEvent e) {
        int selection = closeBidView.getOfferSelection();
        CloseMessageView closeMessageView = new CloseMessageView(closeBidModel, selection);

        // TODO: Handle refresh + message reply (push to API, maybe go through CloseBidModel)
        closeMessageView.getRefreshButton().addActionListener(ef -> {
            System.out.println("Refresh MessagePair");
        });
        closeMessageView.getRespondMessageButton().addActionListener(ef -> {
            ReplyMessage replyMessage = new ReplyMessage();
            System.out.println("Reply Message");
        });
    }

    private void handleOfferSelection(ActionEvent e) {
        int selection = closeBidView.getOfferSelection();
        Bid currentBid = closeBidModel.getBid();
        MessageBidInfo messageBidInfo = closeBidModel.getCloseBidOffers().get(selection);
        System.out.println("Contract " + selection);
        // TODO: handle contract formation + mark bid as closed
        createContract(currentBid, messageBidInfo);
    }


}
