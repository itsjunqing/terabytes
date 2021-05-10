package controller.offering;

import controller.EventListener;
import model.offering.OfferingModel;
import stream.Bid;
import view.offering.OfferingView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLOutput;

public class OfferingController implements EventListener {

    private OfferingModel offeringModel;
    private OfferingView offeringView;
    private String userId;

    public OfferingController(String userId) {
        this.userId = userId;
        this.offeringModel = new OfferingModel(userId);
        SwingUtilities.invokeLater(() -> {
            this.offeringView = new OfferingView(offeringModel);
            this.offeringModel.attach(offeringView);
            listenViewActions();
        });

    }

    public void listenViewActions() {
        offeringView.getRefreshButton().addActionListener(this::handleRefresh);
        offeringView.getViewOffersButton().addActionListener(this::handleViewOffers);
        offeringView.getSubscribeOfferButton().addActionListener(this::handleSubscribeOffer);
    }

    private void handleRefresh(ActionEvent e) {
        System.out.println("From OfferingController: Refresh Button is pressed");
        offeringModel.refresh();
    }

    private void handleViewOffers(ActionEvent e) {
        System.out.println("From OfferingController: ViewOffers Button is pressed");
        int selection = offeringView.getBidNumber();
        offeringModel.getBidsOnGoing().forEach(b -> System.out.println(b.toString()));
        Bid bid = offeringModel.viewOffers(selection);
        if (bid != null) {
            if (bid.getType().equals("Open")) {
                new OpenOffersController(offeringModel.getUserId(), bid.getId());
            } else {
                System.out.println("this is some text \n\n\n lets make it count");
                new CloseOffersController(offeringModel.getUserId(), bid.getId());
            }
        }
    }

    private void handleSubscribeOffer(ActionEvent e){
        System.out.println("From Offering Controller: subscribe offer Button is pressed");
        new MonitoringController(userId);
    }

}
