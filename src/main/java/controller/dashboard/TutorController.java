package controller.dashboard;

import controller.offering.OfferingController;
import view.dashboard.TutorView;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Remaining parts:
 * 1) Integration of Offering
 */

public class TutorController extends DashboardController {

    public TutorController(String userId) {
        super(userId);
        SwingUtilities.invokeLater(() -> {
            this.dashboardView = new TutorView(dashboardModel);
            this.dashboardModel.attach(dashboardView);
            listenViewActions();
        });

    }

    @Override
    public void listenViewActions() {
        dashboardView.getRefreshButton().addActionListener(this::handleRefresh);
        dashboardView.getInitiateButton().addActionListener(this::handleInitiation);
    }

    private void handleRefresh(ActionEvent e) {
        System.out.println("From TutorController: Refresh Button is pressed");
        dashboardModel.refresh();

    }

    private void handleInitiation(ActionEvent e) {
        System.out.println("From TutorController: Initiation Button is pressed");
        new OfferingController(dashboardModel.getUserId());
    }
}
