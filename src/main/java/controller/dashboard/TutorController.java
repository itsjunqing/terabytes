package controller.dashboard;

import controller.offering.OfferingController;
import stream.Contract;
import view.dashboard.TutorView;
import view.form.RenewalNotification;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class TutorController extends DashboardController {

    public TutorController(String userId) {
        super(userId);
        SwingUtilities.invokeLater(() -> {
            this.dashboardView = new TutorView(dashboardModel);
            this.dashboardModel.attach(dashboardView);
            listenViewActions();
        });
        checkContractRenewals();
    }

    @Override
    public void listenViewActions() {
        dashboardView.getRefreshButton().addActionListener(this::handleRefresh);
        dashboardView.getInitiateButton().addActionListener(this::handleInitiation);
    }

    private void handleRefresh(ActionEvent e) {
        System.out.println("From TutorController: Refresh Button is pressed");
        dashboardModel.refresh();
        checkContractRenewals();
    }

    private void handleInitiation(ActionEvent e) {
        System.out.println("From TutorController: Initiation Button is pressed");
        new OfferingController(dashboardModel.getUserId());
    }

    private void checkContractRenewals() {
        List<Contract> renewingContracts = dashboardModel.getRenewingContracts();
        if (!renewingContracts.isEmpty()) {
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
}
