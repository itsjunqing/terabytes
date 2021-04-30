package controller.offering;

import entity.BidInfo;
import entity.MessageBidInfo;
import model.offering.OfferingModel;
import stream.Bid;
import view.form.OfferBid;
import view.form.ReplyBid;
import view.offering.CloseOfferView;
import view.offering.OfferingView;
import view.offering.OpenOffersView;

import java.awt.event.ActionEvent;
import java.util.List;
//import view.offering.OfferingView;

public class OfferingController {

    private OfferingModel offeringModel;
    private OfferingView offeringView;
    private OpenOffersView openOffersView;
    private CloseOfferView closeOfferView;
    private String userId;

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
            OpenOffersController openOffersController = new OpenOffersController(bid.getId(), userId);
        } else {
           CloseOffersController closeOffersController = new CloseOffersController(bid.getId(), userId);
        }

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
