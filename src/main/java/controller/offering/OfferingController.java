package controller.offering;

import controller.EventListener;
import model.offering.OfferingModel;
import stream.Bid;
import view.offering.OfferingView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OfferingController implements EventListener {

    private OfferingModel offeringModel;
    private OfferingView offeringView;

    public OfferingController(String userId) {
        this.offeringModel = new OfferingModel(userId);
//        SwingUtilities.invokeLater(() -> {
            this.offeringView = new OfferingView(offeringModel);
            this.offeringModel.attach(offeringView);
            listenViewActions();
//        });

    }

    public void listenViewActions() {
        offeringView.getRefreshButton().addActionListener(this::handleRefresh);
        offeringView.getViewOffersButton().addActionListener(this::handleViewOffers);
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
        System.out.println(bid.toString());
        System.out.println(selection);
        System.out.println("Hi");

//        System.out.println(selection);
//        System.out.println(bid.toString());
//        System.out.println("this is some text \n\n\n lets make it count \n \n");

        if (bid != null) {
            if (bid.getType().equals("Open")) {
                new OpenOffersController(offeringModel.getUserId(), bid.getId());
            } else {
                System.out.println("this is some text \n\n\n lets make it count");
                new CloseOffersController(offeringModel.getUserId(), bid.getId());
            }
        }
    }

}
