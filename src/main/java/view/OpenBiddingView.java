package view;

import stream.Contract;
import view.panel.ContractPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OpenBiddingView {

    public OpenBiddingView(List<Contract> contractList) {
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
