package view;

import model.DashboardModel;

public class TutorDashboard extends DashboardView {

    public TutorDashboard(DashboardModel dashboardModel) {
        super(dashboardModel);
    }

    public void displayContracts() {
        System.out.println("Print Contracts Here");
    }

}