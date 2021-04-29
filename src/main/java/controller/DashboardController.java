package controller;

import entity.BidInfo;
import entity.BidPreference;
import entity.QualificationTitle;
import model.*;
import view.DashboardView;
import view.builder.OpenBidView;
import view.form.BidInitiation;

import java.util.Observable;

//import view.BiddingView;
//import view.OfferingView;

public class DashboardController extends Observable {

    private DashboardModel dashboardModel;
    private DashboardView dashboardView;

    public DashboardController(DashboardModel dashboardModel, DashboardView dashboardView) {
        this.dashboardModel = dashboardModel;
        this.dashboardView = dashboardView;
        if (dashboardModel.getUser().getIsStudent()) {
            listenStudentView();
        } else {
            listenTutorView();
        }
    }

    private void listenStudentView() {
        dashboardView.getInitiateButton().addActionListener(e -> {
            System.out.println("From DashboardController: Initiate Button is pressed");

            BidInitiation form = new BidInitiation();
            form.getOpenBidButton().addActionListener(ef -> {
                try {
                    BidPreference bp = extractFormInfo(form);
                    System.out.println("Extracted: " + bp);
                     initiateOpenBid(bp);
                    form.dispose();
                } catch (NullPointerException exception) {
                    // TODO: Add error message in UI on incomplete forms, similar to login
                }
            });
            form.getCloseBidButton().addActionListener(ef -> {
                try {
                    BidPreference bp = extractFormInfo(form);
                    System.out.println("Extracted: " + bp);
                    // initiateCloseBid(bp);
                    form.dispose();
                } catch (NullPointerException exception) {
                    // TODO: Add error message in UI on incomplete forms, similar to login
                }
            });

        });
        dashboardView.getRefreshButton().addActionListener(e -> {
            System.out.println("From DashboardController: Refresh Button is pressed");
        });

    }

    private void listenTutorView() {
        dashboardView.getInitiateButton().addActionListener(e -> {
            System.out.println("From DashboardController: Initiate Button is pressed");
        });
        dashboardView.getRefreshButton().addActionListener(e -> {
            System.out.println("From DashboardController: Refresh Button is pressed");
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
        String initiatorId = dashboardModel.getUserId();
        BidInfo bidInfo = new BidInfo(initiatorId, time, day, duration, rate, numOfSession);
        return new BidPreference(q, c, s, bidInfo);
    }

    private void initiateOpenBid(BidPreference bp) {
        OpenBidModel openBidModel = new OpenBidModel();
        openBidModel.createBid(dashboardModel.getUserId(), bp);
        OpenBidView openBidView = new OpenBidView(openBidModel);

//        BiddingController biddingController = new BiddingController(openBidModel, biddingView);
    }

    private void initiateCloseBid(BidPreference bp) {
        BiddingModel biddingModel = new CloseBidModel();
        biddingModel.createBid(dashboardModel.getUserId(), bp);
//        BiddingView biddingView = new CloseBidView(biddingModel);

//         BiddingController biddingController = new BiddingController(biddingModel, biddingView);
    }

}



//    private void listenOffering() {
//        // bid offering for tutors
//        OfferingModel offeringModel = new OfferingModel(dashboardModel.getUser());
////        OfferingView offeringView = new OfferingView();
////        OfferingController offeringController = new OfferingController(offeringModel, offeringView);
//        // keep dashboard view, no need destroy and let offeringcontroller to handle subsequent actions
//    }
