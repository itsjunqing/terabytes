package controller.offering;

import model.offering.CloseOffersModel;
import view.offering.CloseOfferView;

import java.awt.event.ActionEvent;

public class CloseOffersController {

    private CloseOffersModel closeOffersModel;
    private CloseOfferView closeOfferView;

    public CloseOffersController(String bidId) {
//        this.closeOffersModel = new CloseOffersModel(bidId);
//        this.closeOfferView = new CloseOfferView(closeOffersModel);
        listenViewActions();
    }

    private void listenViewActions() {
        closeOfferView.getRefreshButton().addActionListener(this::handleRefresh);
        closeOfferView.getRespondMessageButton().addActionListener(this::handleRespondMessage);
    }

    private void handleRefresh(ActionEvent e) {

    }

    private void handleRespondMessage(ActionEvent e) {

    }
}
