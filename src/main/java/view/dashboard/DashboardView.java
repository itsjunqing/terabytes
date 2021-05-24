package view.dashboard;

import lombok.Getter;
import model.dashboard.DashboardModel;
import view.ViewTemplate;

import javax.swing.*;

@Getter
public abstract class DashboardView extends ViewTemplate {

    protected DashboardModel dashboardModel;
    protected JButton refreshButton;
    protected JButton initiateButton;
    protected JButton renewContractsButton;

    protected DashboardView(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    @Override
    protected void refreshContent(){
        updateView();
        refreshButtons();
        SwingUtilities.updateComponentTreeUI(frame);
    }
}
