package view;

import lombok.Getter;
import model.DashboardModel;

@Getter
public abstract class DashboardView {

    private DashboardModel dashboardModel;
//    protected JPanel mainPanel;
//    protected ContractPanel contractPanel;
//    protected DashboardButtonPanel buttonPanel;

    public DashboardView(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

//    public abstract void updateContracts();
}
