package controller.dashboard;

import controller.bidding.CloseBidController;
import controller.bidding.OpenBidController;
import controller.contract.ContractRenewalController;
import entity.BidInfo;
import entity.Preference;
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
            dashboardView = new StudentView(dashboardModel);
            dashboardModel.attach(dashboardView);
            listenViewActions();
        });
    }

    @Override
    public void listenViewActions() {
        dashboardView.getRefreshButton().addActionListener(this::handleRefresh);
        dashboardView.getInitiateButton().addActionListener(this::handleInitiation);
        dashboardView.getRenewContractsButton().addActionListener((this::handleRenewContract));
    }

    private void handleRenewContract(ActionEvent e){
        System.out.println("From StudentController: Renew Contract Button is pressed");
        ContractRenewalController contractRenewalController = new ContractRenewalController(dashboardModel.getUserId());
    }

    private void handleRefresh(ActionEvent e) {
        System.out.println("From StudentController: Refresh Button is pressed");
        dashboardModel.refresh();
    }

    private void handleInitiation(ActionEvent e) {
        DashboardStatus status = dashboardModel.getBidStatus();
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
            Preference bp = extractFormInfo(form);
            System.out.println("From StudentController: Extracted: " + bp);
            form.dispose();
            new OpenBidController(dashboardModel.getUserId(), bp);
        } catch (NullPointerException exception) {
            System.out.println("Incomplete form found");
            form.getErrorLabel().setText("Incomplete form!");
        }
    }

    private void initiateCloseBid(ActionEvent e, BidInitiation form) {
        try {
            Preference bp = extractFormInfo(form);
            System.out.println("From StudentController: Extracted: " + bp);
            form.dispose();
            new CloseBidController(dashboardModel.getUserId(), bp);
        } catch (NullPointerException exception) {
            System.out.println("Incomplete form found");
            form.getErrorLabel().setText("Incomplete form!");
        }
    }

    private Preference extractFormInfo(BidInitiation form) throws NullPointerException {
        System.out.println("From StudentController: Extracting bid information..");
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
        return new Preference(q, c, s, bidInfo);
    }



}
