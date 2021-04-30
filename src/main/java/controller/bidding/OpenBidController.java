package controller.bidding;

import entity.BidInfo;
import entity.BidPreference;
import model.bidding.OpenBidModel;
import stream.Bid;
import view.bidding.OpenBidView;

import java.awt.event.ActionEvent;


public class OpenBidController extends BiddingController {

    private OpenBidModel openBidModel;
    private OpenBidView openBidView;

    /**
     * Constructor to create an OpenBid
     * @param userId
     * @param bp
     */
    public OpenBidController(String userId, BidPreference bp) {
        this.openBidModel = new OpenBidModel(userId, bp);
        this.openBidView = new OpenBidView(openBidModel);
    }

    /**
     * Constructor for OpenBid in progress
     * @param userId
     */
    public OpenBidController(String userId) {
        this.openBidModel = new OpenBidModel(userId);
        this.openBidView = new OpenBidView(openBidModel);
    }

    @Override
    public void listenViewActions() {
        openBidView.getRefreshButton().addActionListener(this::handleRefresh);
        openBidView.getSelectOfferButton().addActionListener(this::handleOfferSelection);
    }

    private void handleRefresh(ActionEvent e) {
        openBidModel.refresh();
    }

    private void handleOfferSelection(ActionEvent e) {
        int selection = openBidView.getOfferSelection();
        Bid currentBid = openBidModel.getBid();
        BidInfo bidInfo = openBidModel.getOpenBidOffers().get(selection-1);
        System.out.println("Contract " + selection);
        openBidModel.markBidClose();
        createContract(currentBid, bidInfo);
    }
}
