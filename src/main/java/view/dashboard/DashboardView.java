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
    protected JPanel buttonPanel;
    protected JButton renewContractsButton = new JButton();


    public DashboardView(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    protected void refreshContent(){
        updateContracts();
        errorLabel.setText(dashboardModel.getErrorText());
        SwingUtilities.updateComponentTreeUI(frame);
//        frame.pack();
    }

    public abstract void updateContracts();
}
