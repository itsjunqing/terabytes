package controller.bidding;

import entity.Preference;
import model.bidding.OpenBidModel;
import stream.Contract;
import view.bidding.OpenBidView;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class OpenBidController extends BiddingController {

    private OpenBidModel openBidModel;
    private OpenBidView openBidView;

    /**
     * Constructor to create an OpenBid
     * @param userId
     * @param bp
     */
    public OpenBidController(String userId, Preference bp) {
        this.openBidModel = new OpenBidModel(userId, bp);
        SwingUtilities.invokeLater(() -> {
            this.openBidView = new OpenBidView(openBidModel);
            this.openBidModel.attach(openBidView);
            listenViewActions();
        });
    }

    /**
     * Constructor for OpenBid in progress
     * @param userId
     */
    public OpenBidController(String userId) {
        this.openBidModel = new OpenBidModel(userId);
        SwingUtilities.invokeLater(() -> {
            this.openBidView = new OpenBidView(openBidModel);
            this.openBidModel.attach(openBidView);
            listenViewActions();
        });
    }

    @Override
    public void listenViewActions() {
        openBidView.getRefreshButton().addActionListener(this::handleRefresh);
        openBidView.getSelectOfferButton().addActionListener(this::handleOfferSelection);
    }

    private void handleRefresh(ActionEvent e) {
        System.out.println("From OpenBidController: Refresh is clicked");
        openBidModel.refresh();
    }

    private void handleOfferSelection(ActionEvent e) {
        try {
            int selection = openBidView.getOfferSelection();
            Contract contract = openBidModel.formContract(selection);
            if (contract != null){
                boolean confirmed = handleContract(contract);
                if (confirmed) {
                    openBidModel.markBidClose();
                    openBidView.dispose();
                }
            }
        } catch (NullPointerException ef) {
            openBidView.getErrorLabel().setText("No offers selected!");
        }
    }
}
