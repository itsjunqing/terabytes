package view;

import model.OfferingModel;
import stream.Bid;
import view.panel.button.ActiveRequestButtonPanel;
import view.panel.ActiveRequestPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ActiveRequestsView {
private OfferingModel offeringModel;
private List<Bid> bidList;
    public ActiveRequestsView(OfferingModel offeringModel, List<Bid> bidList) {
        this.offeringModel = offeringModel;
        this.bidList = bidList;
        displayContracts();
    }

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

//                List <Bid> bidList = offeringModel.getBidsOnGoing();
                ActiveRequestPanel activeRequestPanel = new ActiveRequestPanel(bidList);
                ActiveRequestButtonPanel buttonPanel = new ActiveRequestButtonPanel(bidList.size());

                mainPanel.add(activeRequestPanel);
                mainPanel.add(buttonPanel);

                JFrame frame = new JFrame("Current Active Requests");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(mainPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
