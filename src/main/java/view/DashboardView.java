package view;

import model.DashboardModel;

public abstract class DashboardView {

    private DashboardModel dashboardModel;

    public DashboardView(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    public abstract void displayContracts();
}
