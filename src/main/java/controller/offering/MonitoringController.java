package controller.offering;

import controller.EventListener;
import model.offering.MonitoringModel;
import scheduler.Scheduler;
import stream.Bid;
import view.offering.MonitoringView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * A Class of MonitoringController to control the actions on the monitoring dashboard.
 * This uses a Scheduler to construct the automatic refresh and updates of the dashboard.
 */
public class MonitoringController implements EventListener {

    private MonitoringView monitoringView;
    private MonitoringModel monitoringModel;
    private Scheduler scheduler;

    /**
     * Constructs a MonitoringController
     * @param userId a String of user id
     * @param monitoringBids a list of Bid to be monitored
     */
    public MonitoringController(String userId, List<Bid> monitoringBids) {
        this.monitoringModel = new MonitoringModel(userId, monitoringBids);
        SwingUtilities.invokeLater(() -> {
            // Construct the view and the scheduler to update automatically
            this.monitoringView = new MonitoringView(monitoringModel);
            this.scheduler = new Scheduler();
            initObserving();
            listenViewActions();
        });
    }

    /**
     * Initializes the aspects of:
     * Scheduler: periodically updates and notifies the observer (model)
     * Model: observe the scheduler
     * View: observe the model
     */
    private void initObserving() {
        // Let the View to observe the Model
        this.monitoringModel.attach(monitoringView);
        // Let the Model to observe the Scheduler
        this.scheduler.attach(monitoringModel);
        // End the Scheduler when frame is closed
        this.monitoringView.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Monitoring Frame is Closing");
                scheduler.endScheduler();
                e.getWindow().dispose();
            }
        });
    }

    /**
     * Listen to actions on the dashboard
     */
    @Override
    public void listenViewActions() {
        monitoringView.getViewOffersButton().addActionListener(this::handleViewOffers);
    }

    /**
     * Handles the request to provide offers for a selected Bid
     */
    private void handleViewOffers(ActionEvent e) {
        System.out.println("From MonitoringController: ViewOffers Button is pressed");
        try {
            int selection = monitoringView.getBidNumber();
            Bid bid = monitoringModel.viewOffers(selection);
            if (bid != null)  {
                // Monitored bids are guaranteed to be Open
                new OpenOffersController(monitoringModel.getUserId(), bid.getId());
            } else {
                monitoringView.getErrorLabel().setText("Bid is closed down!");
            }
        } catch (NullPointerException ex) {
            monitoringView.getErrorLabel().setText("No bid is selected!");
        }
    }
}



//    private void handleSelection(ActionEvent e){
//        List <Bid> selectedBids = subscriptionSelectionView.getBidJList().getSelectedValuesList();
//        subscriptionSelectionView.dispose();
//        monitoringModel.setSelectedBids(selectedBids);
//        SwingUtilities.invokeLater(() -> {
//            monitoringView = new MonitoringView(monitoringModel);
//            monitoringView.getRespondButton().addActionListener(this::handleRespond);
//            monitoringView.getBuyOutButton().addActionListener(this::handleBuyOut);
//            // if the window is closed, end the scheduller
//            monitoringView.getFrame().addWindowListener(new WindowAdapter() {
//                @Override
//                public void windowClosing(WindowEvent e) {
//                    System.out.println("Monitoring Frame is Closing");
//                    dispose();
//                    e.getWindow().dispose();
//                }
//            });
//            this.monitoringModel.attach(monitoringView);
//            scheduler = Scheduler.getInstance();
//            scheduler.oSubject.attach(monitoringModel); // scheduler (subject) will notify observer (monitoring) every 5 seconds
//        });
//    }
