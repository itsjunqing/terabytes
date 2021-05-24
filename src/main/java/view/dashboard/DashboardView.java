package view.dashboard;

import lombok.Getter;
import model.dashboard.DashboardModel;
import observer.Observer;
import view.template.viewTemplate;

import javax.swing.*;

@Getter
public abstract class DashboardView extends viewTemplate {

    protected DashboardModel dashboardModel;
    protected JButton refreshButton;
    protected JButton initiateButton;
    protected JButton renewContractsButton = new JButton();


    public DashboardView(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    protected void refreshContent(){
        updateView();
        refreshButtons();
        SwingUtilities.updateComponentTreeUI(frame);
    }
}
