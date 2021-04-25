package controller;

import model.*;
import stream.User;
//import view.BiddingView;
import view.DashboardView;
//import view.OfferingView;
import java.util.Observable;

public class DashboardController extends Observable {

    private DashboardModel dashboardModel;
    private DashboardView dashboardView;

    public DashboardController(DashboardModel dashboardModel, DashboardView dashboardView) {
        this.dashboardModel = dashboardModel;
        this.dashboardView = dashboardView;
    }

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
        // create bid initiation window for students

        // extract info from view -> check if open bid / close bid is selected
        // remember to delete bid initiation view after extracting the info
        // extract info from view:
        QualificationTitle qualification = QualificationTitle.valueOf("Bachelor".toUpperCase()); // map string to enum
        int competency = Integer.parseInt("1"); // assume text view only accepts string
        String subject = "Physics";
        String initiatorId = dashboardModel.getUser().getId();
        String time = "-1";
        String day = "-1";
        int duration = 1;
        int rate = 10;
        int numberOfSessions = 10;
        int contractDuration = 4; // weeks

        BidInfo bidInfo = new BidInfo(initiatorId, time, day, duration, rate, numberOfSessions, contractDuration);
        BidPreference bidPreference = new BidPreference(qualification, competency, subject, bidInfo);

//        BiddingView biddingView = new BiddingView();
        BiddingModel biddingModel;
        String action = "-1";
        if (action.equals("Open")) {
            biddingModel = new OpenBiddingModel();
        } else {
            biddingModel = new CloseBiddingModel();
        }
        biddingModel.createBid(dashboardModel.getUser(), bidPreference); // need further refactoring on passing User

//        BiddingController biddingController = new BiddingController(biddingModel, biddingView);
        // keep dashboard view, no need destroy and let biddingcontroller to handle subsequent actions

    }


    private void listenOffering() {
        // bid offering for tutors
        OfferingModel offeringModel = new OfferingModel(dashboardModel.getUser());
//        OfferingView offeringView = new OfferingView();
//        OfferingController offeringController = new OfferingController(offeringModel, offeringView);
        // keep dashboard view, no need destroy and let offeringcontroller to handle subsequent actions
    }
}

