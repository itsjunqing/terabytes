package view.dashboard;

import lombok.Getter;
import model.dashboard.DashboardModel;
import observer.Observer;

import javax.swing.*;

@Getter
public abstract class DashboardView implements Observer {

    protected DashboardModel dashboardModel;
    protected JPanel mainPanel; // mainPanel holds both contractPanel and buttons
    protected JPanel contractPanel; // used to clear and update the content, only this need to be updated
    protected JButton refreshButton;
    protected JButton initiateButton;
    protected JLabel errorLabel;
    protected JFrame frame;
    JPanel buttonPanel;


    // Note: once buttons are created, when refresh is called, only contractPanel is updated, buttons are not destroyed
    // so the same buttons listened in the controller will continue to work

    public DashboardView(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    public abstract void updateContracts();
}
