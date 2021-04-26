package view;

import model.DashboardModel;
import stream.Contract;
import view.panel.ContractPanel;
import view.panel.DashboardButtonPanel;

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
                System.out.println("running this");
                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new GridLayout(1,2));

                List<Contract> contractList = getDashboardModel().getContractsList();
                ContractPanel contractPanel = new ContractPanel(contractList);

                // TODO: Update line below to update for tutor's button version, maybe need to create another version, not sure
                DashboardButtonPanel buttonPanel = new DashboardButtonPanel(contractList.size());

                mainPanel.add(contractPanel);
                mainPanel.add(buttonPanel);

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
