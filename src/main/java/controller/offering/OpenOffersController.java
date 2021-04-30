package controller.offering;

import model.offering.OpenOffersModel;
import view.offering.OpenOffersView;

import java.awt.event.ActionEvent;

public class OpenOffersController {

    private OpenOffersModel openOffersModel;
    private OpenOffersView openOffersView;

    public OpenOffersController(String bidId) {
//        this.openOffersModel = new OpenOffersModel(bidId);
//        this.openOffersView = new OpenOffersView(openOffersModel);
        listenViewActions();
    }

    private void listenViewActions() {
        openOffersView.getRefreshButton().addActionListener(this::handleRefresh);
        openOffersView.getRespondButton().addActionListener(this::handleOpenRespond);
        openOffersView.getBuyOutButton().addActionListener(this::handleBuyOut);
    }

    private void handleRefresh(ActionEvent e) {

    }

    private void handleOpenRespond(ActionEvent e) {

    }

    private void handleBuyOut(ActionEvent e) {

    }

}
