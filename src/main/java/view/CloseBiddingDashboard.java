package view;

import model.OfferingModel;
import stream.Bid;
import view.panel.button.CloseBiddingButtonPanel;
import view.panel.CloseBiddingPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CloseBiddingDashboard {
private OfferingModel offeringModel;
private List<Bid> bidList;
    public CloseBiddingDashboard(OfferingModel offeringModel, List<Bid> bidList) {
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
                CloseBiddingPanel closeBiddingPanel = new CloseBiddingPanel(bidList);
                CloseBiddingButtonPanel buttonPanel = new CloseBiddingButtonPanel(bidList.size());

                mainPanel.add(closeBiddingPanel);
                mainPanel.add(buttonPanel);

                JFrame frame = new JFrame("Open Offers");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(mainPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
