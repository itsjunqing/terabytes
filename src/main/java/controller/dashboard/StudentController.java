package controller.dashboard;

import controller.bidding.CloseBidController;
import controller.bidding.OpenBidController;
import entity.BidInfo;
import entity.BidPreference;
import entity.DashboardStatus;
import entity.QualificationTitle;
import stream.User;
import view.dashboard.StudentView;
import view.form.BidInitiation;

import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;

/**
 * Remaining parts:
 * 1) Integration of OpenBid
 * 2) Integration of CloseBid
 */

public class StudentController extends DashboardController {

    public StudentController(User user) {
        super(user);
        this.dashboardView = new StudentView(dashboardModel);
        dashboardModel.oSubject.attach(dashboardView);
        listenViewActions();
    }

    @Override
    public void listenViewActions() {
        dashboardView.getRefreshButton().addActionListener(this::handleRefresh);
        dashboardView.getInitiateButton().addActionListener(this::handleInitiation);
    }

    private void handleRefresh(ActionEvent e) {
        System.out.println("From DashboardController: Refresh Button is pressed");
        dashboardModel.refresh();
        dashboardView.getRefreshButton().addActionListener(this::handleRefresh);
        dashboardView.getInitiateButton().addActionListener(this::handleInitiation);
    }

    private void handleInitiation(ActionEvent e) {
        DashboardStatus status = dashboardModel.getStatus();
        switch (status) {
            case MAX:
                dashboardView.getErrorLabel().setText("Error, you already have 5 Contracts!");
                break;

            case OPEN:
                dashboardView.getErrorLabel().setText("You already have a open bid in progress, displaying active bid");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                OpenBidController openBidController = new OpenBidController(dashboardModel.getUserId());
                break;

            case CLOSE:
                dashboardView.getErrorLabel().setText("You already have a close bid in progress, displaying active bid");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                CloseBidController closeBidController = new CloseBidController(dashboardModel.getUserId());
                break;

            case PASS:
                listenBidInitiationForm();
                break;
        }
    }

    private void listenBidInitiationForm() {
        BidInitiation form = new BidInitiation();
        form.getOpenBidButton().addActionListener(ef -> initiateOpenBid(ef, form));
        form.getCloseBidButton().addActionListener(ef -> initiateCloseBid(ef, form));
    }

    private void initiateOpenBid(ActionEvent e, BidInitiation form) {
        try {
            BidPreference bp = extractFormInfo(form);
            System.out.println("Extracted: " + bp);
            form.dispose();
            OpenBidController openBidController = new OpenBidController(dashboardModel.getUserId(), bp);

        } catch (NullPointerException exception) {
            // TODO: Add error message in UI on incomplete forms, similar to login
        }
    }

    private void initiateCloseBid(ActionEvent e, BidInitiation form) {
        try {
            BidPreference bp = extractFormInfo(form);
            System.out.println("Extracted: " + bp);
            form.dispose();
            CloseBidController closeBidController = new CloseBidController(dashboardModel.getUserId(), bp);

        } catch (NullPointerException exception) {
            // TODO: Add error message in UI on incomplete forms, similar to login
        }
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



}
