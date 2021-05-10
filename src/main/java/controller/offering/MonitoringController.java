package controller.offering;

import controller.EventListener;
import entity.BidInfo;
import model.offering.MonitoringModel;
import scheduler.Scheduler;
import stream.Bid;
import view.form.OpenReply;
import view.offering.MonitoringView;
import view.offering.SubscriptionSelectionView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MonitoringController implements EventListener {

    private MonitoringView monitoringView;
    private MonitoringModel monitoringModel;
    private SubscriptionSelectionView subscriptionSelectionView;
    private Scheduler scheduler;
    private List<Bid> openBids = new ArrayList<Bid>();
    private List<Bid> choosenBids = new ArrayList<Bid>();

    public MonitoringController(String userId) {
        SwingUtilities.invokeLater(() -> {
            this.monitoringModel = new MonitoringModel(userId);
            this.subscriptionSelectionView = new SubscriptionSelectionView(monitoringModel);
            listenViewActions();
        });
    }

    public void listenViewActions() {
        subscriptionSelectionView.getConfirmSelection().addActionListener(this::handleSelection);
    }

    private void handleSelection(ActionEvent e){
        List <Bid> selectedBids = subscriptionSelectionView.getBidJList().getSelectedValuesList();
        subscriptionSelectionView.dispose();
        monitoringModel.setSelectedBids(selectedBids);
        SwingUtilities.invokeLater(() -> {
            monitoringView = new MonitoringView(monitoringModel);
            monitoringView.getRespondButton().addActionListener(this::handleRespond);
            monitoringView.getBuyOutButton().addActionListener(this::handleBuyOut);
            // if the window is closed, end the scheduller
            monitoringView.getFrame().addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    System.out.println("Monitoring Frame is Closing");
                    dispose();
                    e.getWindow().dispose();
                }
            });
            this.monitoringModel.attach(monitoringView);
            scheduler = Scheduler.getInstance();
            scheduler.oSubject.attach(monitoringModel);
        });
    }

    private void handleRespond(ActionEvent e) {
        try {
            String bidId = monitoringView.getSelectionId();
            OpenReply openReply = new OpenReply();
            openReply.getSendOpenReplyButton().addActionListener(e1 -> handleBidInfo(e1, openReply, bidId));
        }catch(NullPointerException n){
            monitoringView.getErrorLabel().setText("There are no bids to select");
        }
    }

    private void handleBidInfo(ActionEvent e, OpenReply openReplyForm, String bidId) {
        SwingUtilities.invokeLater(() -> {
        try {
            BidInfo bidInfo = extractOpenReplyInfo(openReplyForm);
            System.out.println("Extracted: " + bidInfo);
                monitoringModel.respond(bidInfo, bidId);
            openReplyForm.dispose();

        } catch (NullPointerException exception) {
            openReplyForm.getErrorLabel().setText("Form is incomplete!");
        }
        });
    }

    private void handleBuyOut(ActionEvent e) {
        // Get preferences -> Add BidInfo -> create contract -> sign -> dispose
        SwingUtilities.invokeLater(() -> {

            try {
            String bidId = monitoringView.getSelectionId();
            monitoringModel.buyOut(bidId);
            monitoringView.dispose();
        } catch (NullPointerException exception) {
            monitoringView.getErrorLabel().setText("There are no bids to buy out!");
        }
        });
    }

    private BidInfo extractOpenReplyInfo(OpenReply openReplyForm) throws NullPointerException {
        String tutorId = monitoringModel.getUserId();
        String time = openReplyForm.getTimeBox();
        String day = openReplyForm.getDayBox();
        int duration = openReplyForm.getDurationBox();
        int rate = openReplyForm.getRateField();
        int numberOfSessions = openReplyForm.getNumOfSessionBox();
        boolean freeLesson = openReplyForm.getFreeLessonBox();
        return new BidInfo(tutorId, time, day, duration, rate, numberOfSessions, freeLesson);
    }

    private void dispose(){
        scheduler.endScheduler();
    }

}
