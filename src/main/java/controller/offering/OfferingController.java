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
