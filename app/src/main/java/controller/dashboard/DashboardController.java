package controller.dashboard;

import controller.EventListener;
import model.dashboard.DashboardModel;
import view.dashboard.DashboardView;

/**
 * A Class to control movements on Dashboard
 */
public abstract class DashboardController implements EventListener {

    protected DashboardModel dashboardModel;
    protected DashboardView dashboardView;

    /**
     * Constructor to construct a DashboardController
     * @param userId a String of user id
     */
    public DashboardController(String userId) {
        this.dashboardModel = new DashboardModel(userId);
    }

}