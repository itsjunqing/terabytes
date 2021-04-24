package view;

import stream.Contract;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentDashboardFinal {

    public StudentDashboardFinal(List<Contract> contractList) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }
                ContractPanel contractPanel = new ContractPanel(contractList);
                JFrame frame = new JFrame("Contracts");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(contractPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

    }



}
