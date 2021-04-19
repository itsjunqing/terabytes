package view;

import model.DashboardModel;

public class StudentDashboard extends DashboardView {

    public StudentDashboard(DashboardModel dashboardModel) {
        super(dashboardModel);
    }

    public void displayContracts() {
        System.out.println("Print Contracts Here");
    }

}