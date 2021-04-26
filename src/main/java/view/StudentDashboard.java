package view;

import model.DashboardModel;
import stream.Contract;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentDashboard extends DashboardView {

    public StudentDashboard(DashboardModel dashboardModel) {
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
                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new GridLayout(1,2));

                List<Contract> contractList = getDashboardModel().getContractsList();
                ContractPanel contractPanel = new ContractPanel(contractList);
                ButtonPanel buttonPanel = new ButtonPanel();

                mainPanel.add(contractPanel);
                mainPanel.add(buttonPanel);


                JFrame frame = new JFrame("Student Dashboard");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(mainPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
