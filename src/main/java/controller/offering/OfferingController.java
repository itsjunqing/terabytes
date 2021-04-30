package controller.offering;

import model.offering.OfferingModel;
import stream.Bid;
import view.offering.CloseOfferView;
import view.offering.OfferingView;
import view.offering.OpenOffersView;

import java.awt.event.ActionEvent;
import java.util.List;
//import view.offering.OfferingView;

public class OfferingController {

    private OfferingModel offeringModel;
    private OfferingView offeringView;

    public OfferingController(String userId) {
        this.offeringModel = new OfferingModel(userId);
        this.offeringView = new OfferingView(offeringModel);
        listenViewActions();
    }

    private void listenViewActions() {
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
        Bid bid = offeringModel.getBidsOnGoing().get(selection-1);
        if (bid.getType().equals("Open")) {
            OpenOffersController openOffersController = new OpenOffersController(bid.getId());
        } else {

        }

    }


    public void listenRefresh() {
        offeringModel.refresh();
    }

    private void listenOfferingView(){
        this.offeringView.getViewOffersButton().addActionListener(e -> {
            int selectedBid = this.offeringView.getBidNumber();
            List<Bid> ongoing = offeringModel.getBidsOnGoing();
            Bid selected = ongoing.get(selectedBid-1);
        if (selected.getType().equals("Open")) {
            OpenOffersView openOffersView = new OpenOffersView(offeringModel, selectedBid);
        }else {
            CloseOfferView closeOfferView = new CloseOfferView(offeringModel, selectedBid);
        }
        });

    }

    public void listenToOffer() {
        // offer button is selected -> create offer view -> extract info from view -> create BidInfo -> patch to Bid API
        // close view after offer
        int bidIndexOnDisplay = -1;

        String tutorId = "-1";
        String time = "-1";
        String day = "Saturday";
        int duration = 1;
        int rate = 10;
        int numberOfSessions = 2;
        boolean freeLesson = true;
        int contractDuration = 4;

//        BidInfo bidInfo = new BidInfo(tutorId, time, day, duration, rate, numberOfSessions, contractDuration, "", freeLesson);

//        offeringModel.sendOffer(bidIndexOnDisplay, bidInfo);
    }

    public void listenBuyOut() {

    }

    public void listenToReply() {
        // create reply view -> extract info from view -> create BidMessageInfo -> post to Message API
        // view must provide info on which bid did tutor selected
        // close reply view after this function ends

        int bidIndexOnDisplay = -1;

        String tutorId = "-1";
        String time = "-1";
        String day = "Saturday";
        int duration = 1;
        int rate = 10;
        int numberOfSessions = 2;
        boolean freeLesson = true;
        int contractDuration = 4;
        String parsedMessage = "I am a pro tutor";
//        BidInfo bidInfo = new BidInfo(tutorId, time, day, duration, rate, numberOfSessions, contractDuration,
//                parsedMessage, freeLesson);
//
//        offeringModel.sendMessage(bidIndexOnDisplay, bidInfo);
    }



}
