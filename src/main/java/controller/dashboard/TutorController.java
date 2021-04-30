package controller.dashboard;

import controller.offering.OfferingController;
import stream.User;
import view.dashboard.TutorView;

import java.awt.event.ActionEvent;

/**
 * Remaining parts:
 * 1) Integration of Offering
 */

public class TutorController extends DashboardController {

    public TutorController(User user) {
        super(user);
        this.dashboardView = new TutorView(dashboardModel);
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
        System.out.println("From DashboardController: Initiation Button is pressed");
        OfferingController offeringController = new OfferingController(dashboardModel.getUserId());
    }
}
