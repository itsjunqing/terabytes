package view;

import model.BiddingModel;
import stream.Contract;
import view.panel.ContractPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OpenBiddingView extends BiddingView {

    public OpenBiddingView(BiddingModel biddingModel) {
        super(biddingModel);
        updateDisplay();
    }

    @Override
    public void updateDisplay() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }

                // TODO: replace contractList with bidMessageList below
                // List<BidInfo> bidMessagesList = getBiddingModel().getBidOffers();

                List<Contract> contractList = new ArrayList<>();
                ContractPanel contractPanel = new ContractPanel(contractList);
                JFrame frame = new JFrame("Open Bidding View");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(contractPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
