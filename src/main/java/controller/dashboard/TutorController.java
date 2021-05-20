package controller.dashboard;

import controller.offering.OfferingController;
import stream.Contract;
import view.dashboard.TutorView;
import view.form.RenewalNotification;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * A Class to control Tutor's dashboard movements
 */
public class TutorController extends DashboardController {

    /**
     * Constructor to construct TutorController
     * @param userId a String of user id
     */
    public TutorController(String userId) {
        super(userId);
        SwingUtilities.invokeLater(() -> {
            this.dashboardView = new TutorView(dashboardModel);
            this.dashboardModel.attach(dashboardView);
            listenViewActions();
        });
        checkContractRenewals();
    }

    /**
     * Listens to actions on the dashboard
     */
    @Override
    public void listenViewActions() {
        dashboardView.getRefreshButton().addActionListener(this::handleRefresh);
        dashboardView.getInitiateButton().addActionListener(this::handleInitiation);
    }

    /**
     * Handles dashboard refreshing
     */
    private void handleRefresh(ActionEvent e) {
        System.out.println("From TutorController: Refresh Button is pressed");
        dashboardModel.refresh();
        checkContractRenewals();
    }

    /**
     * Handles offering initiation
     */
    private void handleInitiation(ActionEvent e) {
        System.out.println("From TutorController: Initiation Button is pressed");
        new OfferingController(dashboardModel.getUserId());
    }

    /**
     * Checks if there is contract to be confirmed (as renewed by student) and
     * create corresponding RenewalNotification view to confirm the contract
     */
    private void checkContractRenewals() {
        List<Contract> renewingContracts = dashboardModel.getRenewingContracts();
        System.out.println("From TutorController: Renewing contracts are: ");
        for (Contract c: renewingContracts) {
            System.out.println(c);
            RenewalNotification rn = new RenewalNotification(c);
            rn.getConfirmSignButton().addActionListener(e1 -> {
                dashboardModel.executeRenewalResponse(c, true);
                rn.dispose();
            });
            rn.getRejectButton().addActionListener(e1 -> {
                dashboardModel.executeRenewalResponse(c, false);
                rn.dispose();
            });
        }
    }
}
