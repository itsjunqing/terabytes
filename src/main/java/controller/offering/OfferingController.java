package controller.offering;

import model.offering.OfferingModel;
import stream.Bid;
import view.offering.OfferingView;

import java.awt.event.ActionEvent;

public class OfferingController {

    private OfferingModel offeringModel;
    private OfferingView offeringView;

    public OfferingController(String userId) {
        this.offeringModel = new OfferingModel(userId);
        this.offeringView = new OfferingView(offeringModel);
        offeringModel.oSubject.attach(offeringView);
        listenViewActions();
    }

    private void listenViewActions() {
        offeringView.getRefreshButton().addActionListener(this::handleRefresh);
        offeringView.getViewOffersButton().addActionListener(this::handleViewOffers);
    }

    private void handleRefresh(ActionEvent e) {
        System.out.println("From OfferingController: Refresh Button is pressed");
        offeringModel.refresh();
        offeringView.getRefreshButton().addActionListener(this::handleRefresh);
        offeringView.getViewOffersButton().addActionListener(this::handleViewOffers);
    }

    private void handleViewOffers(ActionEvent e) {
        System.out.println("From OfferingController: ViewOffers Button is pressed");
        int selection = offeringView.getBidNumber();
        Bid bid = offeringModel.getBidsOnGoing().get(selection-1);
        if (bid.getType().equals("Open")) {
            OpenOffersController openOffersController = new OpenOffersController(offeringModel.getUserId(), bid.getId());
        } else {
           CloseOffersController closeOffersController = new CloseOffersController(offeringModel.getUserId(), bid.getId());
        }
    }

}
