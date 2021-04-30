package controller.offering;

import entity.BidInfo;
import model.offering.OpenOffersModel;
import view.form.OfferBid;
import view.offering.OpenOffersView;

import java.awt.event.ActionEvent;

public class OpenOffersController {

    private OpenOffersModel openOffersModel;
    private OpenOffersView openOffersView;
    private String bidId;
    private String userId;
    private OfferBid offerBid;

    public OpenOffersController(String bidId, String userId) {
        this.bidId = bidId;
        this.userId = userId;
       this.openOffersModel = new OpenOffersModel(bidId, userId);
       this.openOffersModel.refresh();
       this.openOffersView = new OpenOffersView(this.openOffersModel);
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
        offerBid = new OfferBid();
        offerBid.getOfferBidButton().addActionListener(this::handleCreateBid);
    }

    private void handleBuyOut(ActionEvent e) {
    }

    private void handleCreateBid(ActionEvent e){
        try {
            BidInfo bidInfo = extractBidOfferInfo(offerBid);
            System.out.println("Extracted: " + bidInfo);
            initiateOpenOffer(bidInfo);
            offerBid.dispose();
        } catch (NullPointerException exception) {
        }
        // TODO : add error message for incomplete forms
    }




    public BidInfo extractBidOfferInfo(OfferBid offerBidForm) {
        // offer button is selected -> create offer view -> extract info from view -> create BidInfo -> patch to Bid API
        // close view after offer
        int bidIndexOnDisplay = -1;
        String tutorId = openOffersModel.getUserId();
        String time = offerBidForm.getTime();
        String day = offerBidForm.getDay();
        int duration = offerBidForm.getDuration();
        int rate = offerBidForm.getRate();
        int numberOfSessions = offerBidForm.getNumSessions();
        boolean freeLesson = offerBidForm.getFreeLesson();

        BidInfo bidInfo = new BidInfo(tutorId, time, day, duration, rate, numberOfSessions, freeLesson);
        return bidInfo;
    }

    public void initiateOpenOffer(BidInfo bidInfo){
        openOffersModel.sendOffer(bidInfo);
    };


}
