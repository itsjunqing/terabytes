package controller;

import model.*;
import stream.User;
import view.DashboardView;
import view.form.BidInitiation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

//import view.BiddingView;
//import view.OfferingView;

public class DashboardController extends Observable {

    private DashboardModel dashboardModel;
    private DashboardView dashboardView;

    public DashboardController(DashboardModel dashboardModel, DashboardView dashboardView) {
        this.dashboardModel = dashboardModel;
        this.dashboardView = dashboardView;
        listenDashboard();
    }

    public void listenDashboard(){
//        dashboardView.getButtonPanel().getButton1().addActionListener(e -> {
//            System.out.println("The Refresh Button is pressed");
//        });
//        dashboardView.getButtonPanel().getButton2().addActionListener(e -> {
//            System.out.println("The second button is pressed");
//        });
    };

    public void listenRefresh() {
        dashboardModel.refresh();
    }


    public void listenAction() {
        User user = dashboardModel.getUser();
        if (user.getIsStudent()) {
            listenInitiation(); // listen to bid initiation
        } else {
            listenOffering(); // listen to bid offering
        }
    }

    private void listenInitiation() {
        // BID INITIATION
        BidInitiation form = new BidInitiation();
        form.getOpenBidButton().addActionListener(e -> {
            try {
                BidPreference bp = extractFormInfo(form);
                initiateOpenBid(bp);
            } catch (NullPointerException exception) {
                // TODO: Add error message in UI on incomplete forms, similar to login
            }

        });
        form.getCloseBidButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BidPreference bp = extractFormInfo(form);
                    initiateCloseBid(bp);
                } catch (NullPointerException exception) {
                    // TODO: Add error message in UI on incomplete forms, similar to login
                }
            }
        });
    }

    private BidPreference extractFormInfo(BidInitiation form) throws NullPointerException {
        QualificationTitle q = form.getQualification();
        int c = form.getCompetency();
        String s = form.getSubject();
        int numOfSession = form.getNumOfSessions();
        String day = form.getDay();
        String time = form.getTime();
        int duration = form.getDuration();
        int rate = form.getRate();
        String messageNote = form.getMessageNote();
        int contractDuration = form.getContractDuration();
        String initiatorId = dashboardModel.getUserId();
//        BidInfo bidInfo = new BidInfo(initiatorId, time, day, duration, rate, numOfSession, contractDuration, messageNote);
        BidInfo bidInfo = null;
        return new BidPreference(q, c, s, bidInfo);
    }

    private void initiateOpenBid(BidPreference bp) {
        // TODO: Initialize OpenBidView here

        BiddingModel biddingModel = new OpenBiddingModel();
        biddingModel.createBid(dashboardModel.getUserId(), bp);

        // TODO: Create BiddingController after BiddingView is created, uncomment below
        // BiddingController biddingController = new BiddingController(biddingModel, biddingView);
    }

    private void initiateCloseBid(BidPreference bp) {
        // TODO: Initialize OpenBidView here

        BiddingModel biddingModel = new CloseBiddingModel();
        biddingModel.createBid(dashboardModel.getUserId(), bp);

        // TODO: Create BiddingController after BiddingView is created, uncomment below
        // BiddingController biddingController = new BiddingController(biddingModel, biddingView);
    }


    private void listenOffering() {
        // bid offering for tutors
        OfferingModel offeringModel = new OfferingModel(dashboardModel.getUser());
//        OfferingView offeringView = new OfferingView();
//        OfferingController offeringController = new OfferingController(offeringModel, offeringView);
        // keep dashboard view, no need destroy and let offeringcontroller to handle subsequent actions
    }
}

