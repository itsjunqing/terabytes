package view.panel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class CloseBiddingButtonPanel extends DashboardButtonPanel {
    private JComboBox contractChoice;
    public CloseBiddingButtonPanel(int contractSize) {
        super(contractSize);
        addButtons(contractSize);
    }

    @Override
    protected void addButtons(int numItems) {
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
//        gbc2.fill = GridBagConstraints.HORIZONTAL;
        panel.setLayout(layout);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridwidth = GridBagConstraints.REMAINDER;
        gbc2.gridheight = 3;
        gbc2.weightx = 1;
        JButton refresh = new JButton("Refresh");
//        refresh.setPreferredSize(new Dimension(50, 20));
        panel.add(refresh, gbc2);

        contractChoice = new JComboBox<>();
        for (int i = 1; i < numItems + 1; i++) {
            contractChoice.addItem(i);
        }
        JButton selectOffer = new JButton("Select Offer");
//        initiate.setPreferredSize(new Dimension(50, 20));
        panel.add(selectOffer, gbc2);


        JButton replyOffer = new JButton("Reply Offer");
//        initiate.setPreferredSize(new Dimension(50, 20));
        panel.add(replyOffer, gbc2);
        panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 100;
        gbc1.weightx = 100;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        this.mainList.add(panel, gbc1, 0);
        add(this.mainList, BorderLayout.CENTER);

    }



}


