package controller.dashboard;

import controller.EventListener;
import model.dashboard.DashboardModel;
import view.dashboard.DashboardView;

public abstract class DashboardController implements EventListener {

    protected DashboardModel dashboardModel;
    protected DashboardView dashboardView;

    public DashboardController(String userId) {
        this.dashboardModel = new DashboardModel(userId);
    }

}