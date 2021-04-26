package view;

import lombok.Getter;
import model.DashboardModel;

@Getter
public abstract class DashboardView {

    private DashboardModel dashboardModel;

    public DashboardView(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    public abstract void displayContracts();
}
