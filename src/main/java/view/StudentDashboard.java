package view;

import stream.Contract;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentDashboard {

    public StudentDashboard(List<Contract> contractList) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }
                JPanel mainPanel = new JPanel();

                mainPanel.setLayout(new GridLayout(1,2));
                ContractPanel contractPanel = new ContractPanel(contractList);
                ButtonPanel buttonPanel = new ButtonPanel();

                mainPanel.add(contractPanel);
                mainPanel.add(buttonPanel);


                JFrame frame = new JFrame("Contracts");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(mainPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

    }



}
