package controller.dashboard;

import model.dashboard.DashboardModel;
import stream.User;
import view.dashboard.DashboardView;

import java.util.Observable;

public abstract class DashboardController extends Observable {

    protected DashboardModel dashboardModel;
    protected DashboardView dashboardView;

    public DashboardController(User user) {
        this.dashboardModel = new DashboardModel(user);
    }

    public abstract void listenViewActions();

}