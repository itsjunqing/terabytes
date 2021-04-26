package view;

import model.DashboardModel;
import stream.Contract;
import view.panel.ContractPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TutorDashboard extends DashboardView {

    public TutorDashboard(DashboardModel dashboardModel) {
        super(dashboardModel);
        displayContracts();
    }

    @Override
    public void displayContracts() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }
                List<Contract> contractList = getDashboardModel().getContractsList();
                ContractPanel contractPanel = new ContractPanel(contractList);
                JFrame frame = new JFrame("Tutor Dashboard");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(contractPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

}
