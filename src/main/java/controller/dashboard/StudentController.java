package controller.dashboard;

import controller.bidding.CloseBidController;
import controller.bidding.OpenBidController;
import entity.BidInfo;
import entity.BidPreference;
import entity.DashboardStatus;
import entity.QualificationTitle;
import view.dashboard.StudentView;
import view.form.BidInitiation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;

public class StudentController extends DashboardController {

    public StudentController(String userId) {
        super(userId);
        SwingUtilities.invokeLater(() -> {
            SwingUtilities.invokeLater(() -> {

                dashboardView = new StudentView(dashboardModel);
                dashboardModel.attach(dashboardView);
                listenViewActions();
            });
        }
        );
    }


    @Override
    public void listenViewActions() {
        dashboardView.getRefreshButton().addActionListener(this::handleRefresh);
        dashboardView.getInitiateButton().addActionListener(this::handleInitiation);
    }

    private void handleRefresh(ActionEvent e) {
        System.out.println("From StudentController: Refresh Button is pressed");
        dashboardModel.refresh();
    }

    private void handleInitiation(ActionEvent e) {
        DashboardStatus status = dashboardModel.getStatus();
        switch (status) {
            case MAX:
                break;

            case OPEN:
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                new OpenBidController(dashboardModel.getUserId());
                break;

            case CLOSE:
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                new CloseBidController(dashboardModel.getUserId());
                break;

            case PASS:
                listenBidInitiationForm();
                break;
        }
    }

    private void listenBidInitiationForm() {
        System.out.println("From StudentController: Initiation Button is pressed");
        BidInitiation form = new BidInitiation();
        form.getOpenBidButton().addActionListener(ef -> initiateOpenBid(ef, form));
        form.getCloseBidButton().addActionListener(ef -> initiateCloseBid(ef, form));
    }

    private void initiateOpenBid(ActionEvent e, BidInitiation form) {
        try {
            BidPreference bp = extractFormInfo(form);
            System.out.println("From StudentController: Extracted: " + bp);
            form.dispose();
            new OpenBidController(dashboardModel.getUserId(), bp);
        } catch (NullPointerException exception) {
            form.getErrorLabel().setText("Incomplete form!");
        }
    }

    private void initiateCloseBid(ActionEvent e, BidInitiation form) {
        try {
            BidPreference bp = extractFormInfo(form);
            System.out.println("From StudentController: Extracted: " + bp);
            form.dispose();
            new CloseBidController(dashboardModel.getUserId(), bp);
        } catch (NullPointerException exception) {
            form.getErrorLabel().setText("Incomplete form!");
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
