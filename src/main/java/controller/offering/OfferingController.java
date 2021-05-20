package controller.offering;

import controller.EventListener;
import model.offering.OfferingModel;
import stream.Bid;
import view.offering.OfferingView;
import view.offering.SubscriptionSelectionView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * A Class of OfferingController to control actions on the offering dashboard by tutors
 */
public class OfferingController implements EventListener {

    private OfferingModel offeringModel;
    private OfferingView offeringView;

    /**
     * Constructs an OfferingController
     * @param userId a String of user id
     */
    public OfferingController(String userId) {
        this.offeringModel = new OfferingModel(userId);
        SwingUtilities.invokeLater(() -> {
            this.offeringView = new OfferingView(offeringModel);
            this.offeringModel.attach(offeringView);
            listenViewActions();
        });
    }

    /**
     * Listens to actions on the dashboard
     */
    public void listenViewActions() {
        offeringView.getRefreshButton().addActionListener(this::handleRefresh);
        offeringView.getViewOffersButton().addActionListener(this::handleViewOffers);
        offeringView.getSubscribeOfferButton().addActionListener(this::handleSubscribeBids);
    }

    /**
     * Handles dashboard refreshing
     */
    private void handleRefresh(ActionEvent e) {
        System.out.println("From OfferingController: Refresh Button is pressed");
        offeringModel.refresh();
    }

    /**
     * Handles the request to view the offers of Bid (this can be open or close offers)
     */
    private void handleViewOffers(ActionEvent e) {
        System.out.println("From OfferingController: ViewOffers Button is pressed");
        try {
            int selection = offeringView.getBidNumber();
            offeringModel.getBidsOnGoing().forEach(b -> System.out.println(b.toString()));
            Bid bid = offeringModel.viewOffers(selection);
            if (bid != null) {
                if (bid.getType().equals("Open")) {
                    new OpenOffersController(offeringModel.getUserId(), bid.getId());
                } else {
                    new CloseOffersController(offeringModel.getUserId(), bid.getId());
                }
            }
        } catch (NullPointerException ex) {
            offeringView.getErrorLabel().setText("No bid selected");
        }


    }

    /**
     * Handles the request to subscribe to the existing bids for monitoring purposes
     */
    private void handleSubscribeBids(ActionEvent e){
        System.out.println("From Offering Controller: Subscribe offer Button is pressed");

        if (offeringModel.getBidsOnGoing().size() > 0) {
            SubscriptionSelectionView subscriptionSelectionView =
                    new SubscriptionSelectionView(offeringModel.getOpenBidsOnGoing());
            subscriptionSelectionView.getConfirmButton().addActionListener(e1 -> {
                List<Bid> selectedBids = subscriptionSelectionView.getSelectedBids();
                new MonitoringController(offeringModel.getUserId(), selectedBids);
                subscriptionSelectionView.dispose();
            });
        } else {
            offeringView.getErrorLabel().setText("No ongoing bids, can't subscribe");
        }

    }

}
