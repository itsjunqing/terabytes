package controller.bidding;

import entity.Preference;
import model.bidding.OpenBidModel;
import stream.Contract;
import view.bidding.OpenBidView;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * A Class of OpenBidController that controls the movement on Open Bid dashboard
 */
public class OpenBidController extends BiddingController {

    private OpenBidModel openBidModel;
    private OpenBidView openBidView;

    /**
     * Constructor to create a new OpenBidController
     * @param userId a String of user id
     * @param bp the preference of the Bid
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
     * Constructor to create a OpenBidController for an on-going Open Bid
     * @param userId a String of user id
     */
    public OpenBidController(String userId) {
        this.openBidModel = new OpenBidModel(userId);
        SwingUtilities.invokeLater(() -> {
            this.openBidView = new OpenBidView(openBidModel);
            this.openBidModel.attach(openBidView);
            listenViewActions();
        });
    }

    /**
     * Listen to dashboard actions
     */
    @Override
    public void listenViewActions() {
        openBidView.getRefreshButton().addActionListener(this::handleRefresh);
        openBidView.getSelectOfferButton().addActionListener(this::handleOfferSelection);
    }

    /**
     * Handles dashboard refreshing
     * @param e
     */
    private void handleRefresh(ActionEvent e) {
        System.out.println("From OpenBidController: Refresh is clicked");
        openBidModel.refresh();
    }

    /**
     * Handles the selection of offer by Tutor and constructs a Contract to be established and confirmed
     */
    private void handleOfferSelection(ActionEvent e) {
        try {
            int selection = openBidView.getOfferSelection();
            Contract contract = openBidModel.formContract(selection);
            if (contract != null){
                handleContract(contract);
                openBidView.dispose();
            }
        } catch (NullPointerException ef) {
            openBidView.getErrorLabel().setText("No offers selected!");
        }
    }
}
