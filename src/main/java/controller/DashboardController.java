package controller;

import model.DashboardModel;
import view.DashboardView;

public class DashboardController {

    private DashboardModel dashboardModel;
    private DashboardView dashboardView;

    public DashboardController(DashboardModel dashboardModel, DashboardView dashboardView) {
        this.dashboardModel = dashboardModel;
        this.dashboardView = dashboardView;
    }

    public void test() {
        System.out.println(dashboardModel.getContract("4ad8f1ed-4883-4c44-a9ab-a50bdee96ff9"));
        System.out.println(dashboardModel.getContract("blah"));
        assert dashboardModel.getContract("4ad8f1ed-4883-4c44-a9ab-a50bdee96ff9").stream()
                .anyMatch(c -> c.getId().equals("d8890c50-6480-48db-8497-433b5ade22a2"));
        assert dashboardModel.getContract("blah").isEmpty();
    }
}

