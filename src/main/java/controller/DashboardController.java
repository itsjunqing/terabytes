package controller;

import entity.BidInfo;
import entity.BidPreference;
import entity.QualificationTitle;
import model.*;
import okhttp3.internal.ws.RealWebSocket;
import stream.User;
import view.*;
import view.form.BidInitiation;

import java.util.Observable;
import java.util.concurrent.TimeUnit;

//import view.BiddingView;
//import view.OfferingView;

public class DashboardController extends Observable {

    private DashboardModel dashboardModel;
    private DashboardView dashboardView;

    public DashboardController(User user) {
        this.dashboardModel = new DashboardModel(user);
        if (dashboardModel.getUser().getIsStudent()) {
            this.dashboardView = new StudentView(dashboardModel);
        } else {
            this.dashboardView = new TutorView(dashboardModel);
        }

        if (dashboardModel.getUser().getIsStudent()) {
            listenStudentView();
        } else {
            listenTutorView();
        }
    }

    private void listenStudentView() {
        dashboardView.getInitiateButton().addActionListener(e -> {
            System.out.println("From DashboardController: Initiate Button is pressed");
            // check If 5 contracts
            String bidStatus = dashboardModel.checkBids();
            switch (bidStatus) {
                case "max":
                    dashboardView.getErrorLabel().setText("Error, you already have 5 Contracts!");
                    break;

                // check if theres an offering in progress
                case "Open": {
                    dashboardView.getErrorLabel().setText("You already have a open bid in progress, displaying active bid");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    OpenBidModel openBidModel = new OpenBidModel();
                    openBidModel.lookForBid(dashboardModel.getUserId());
                    openBidModel.refresh();
                    OpenBidView openBidView = new OpenBidView(openBidModel);
                    BiddingController biddingController = new BiddingController(openBidModel, openBidView);
                    break;
                }
                case "Close": {
                    dashboardView.getErrorLabel().setText("You already have a close bid in progress, displaying active bid");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    CloseBidModel closebidModel = new CloseBidModel();
                    closebidModel.lookForBid(dashboardModel.getUserId());
                    closebidModel.refresh();
                    CloseBidView closeBidView = new CloseBidView(closebidModel);
                    BiddingController biddingController = new BiddingController(closebidModel, closeBidView);
                    break;

                    //else bid initiation
                }
                case "pass":
                    listenForm();
                    break;

            }
        }
        );
        dashboardView.getRefreshButton().addActionListener(e -> {
            System.out.println("From DashboardController: Refresh Button is pressed");
        });
    }






    private void listenTutorView() {
        dashboardView.getInitiateButton().addActionListener(e -> {
            OfferingController offeringController = new OfferingController(dashboardModel.getUserId());
        });
        dashboardView.getRefreshButton().addActionListener(e -> {
            System.out.println("From DashboardController: Refresh Button is pressed");
        });
    }

    private void listenForm(){
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
                initiateCloseBid(bp);
                form.dispose();
            } catch (NullPointerException exception) {
                // TODO: Add error message in UI on incomplete forms, similar to login
            }
        });}

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
        openBidModel.refresh();
        openBidModel.createBid(dashboardModel.getUserId(), bp);
        OpenBidView openBidView = new OpenBidView(openBidModel);
        BiddingController biddingController = new BiddingController(openBidModel, openBidView);


//        BiddingController biddingController = new BiddingController(openBidModel, biddingView);
    }

    private void initiateCloseBid(BidPreference bp) {
        CloseBidModel closebidModel = new CloseBidModel();
        closebidModel.refresh();
        closebidModel.createBid(dashboardModel.getUserId(), bp);
        CloseBidView closeBidView = new CloseBidView(closebidModel);
        BiddingController biddingController = new BiddingController(closebidModel, closeBidView);

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
