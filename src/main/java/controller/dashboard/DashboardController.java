package controller.dashboard;

import model.dashboard.DashboardModel;
import view.dashboard.DashboardView;

public abstract class DashboardController {

    protected DashboardModel dashboardModel;
    protected DashboardView dashboardView;

    public DashboardController(String userId) {
        this.dashboardModel = new DashboardModel(userId);
    }

    public abstract void listenViewActions();

}