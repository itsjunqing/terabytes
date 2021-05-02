package view.offering;

import lombok.Getter;
import model.offering.OfferingModel;
import observer.Observer;
import stream.Bid;
import view.ViewUtility;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class OfferingView implements Observer {

    private OfferingModel offeringModel;

    private JPanel mainPanel;
    private JPanel offeringPanel;
    private JPanel buttonPanel;
    private JComboBox bidSelection;
    private JButton refreshButton;
    private JButton viewOffersButton;
    private JLabel errorLabel;

    private JFrame frame;

    public OfferingView(OfferingModel offeringModel) {
        this.offeringModel = offeringModel;

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        frame = new JFrame("Tutor Offering View");
        updateContent();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(mainPanel);
        frame.pack();
        frame.setMinimumSize(new Dimension(860, 400));
        frame.setMaximumSize(new Dimension(860, 1000));
        frame.setPreferredSize(new Dimension(860, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateContent() {
        // query of bid offers need to be done outside to ensure consistent update to both openBidPanel and buttonPanel
        List<Bid> bidList = new ArrayList<>(offeringModel.getBidsOnGoing());
        offeringModel.getBidsOnGoing().forEach(b -> System.out.println(b.toString()));
        System.out.println(bidList.size());
        System.out.println("size, look at me \n \n ");
        bidList.stream()
                .forEach(c -> System.out.println(c.toString()));

        System.out.println(bidList.size());
        System.out.println("hi");
        int bidSize = bidList.size();
        Collections.reverse(bidList);
        updateView(bidList);
        updateButtons(bidSize);
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
    }

    private void refreshContent() {
        // query of bid offers need to be done outside to ensure consistent update to both openBidPanel and buttonPanel
        List<Bid> bidList = new ArrayList<>(offeringModel.getBidsOnGoing());
        offeringModel.getBidsOnGoing().forEach(b -> System.out.println(b.toString()));
        System.out.println(bidList.size());
        System.out.println("size, look at me \n \n ");
        bidList.stream()
                .forEach(c -> System.out.println(c.toString()));
        System.out.println(bidList.size());
        System.out.println("hi");
        int bidSize = bidList.size();
        Collections.reverse(bidList);
        updateView(bidList);
        refreshButtons(bidSize);
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
    }

    private void refreshButtons(int bidSize){
        // refreshing jcombobox
        bidSelection.removeAllItems();
        for (int i = 1; i < bidSize + 1; i++) {
            bidSelection.addItem(i);
        }
        // refreshing jlabel
        errorLabel.setText(offeringModel.getErrorText());
    }

    private void updateButtons(int bidSize) {
        if (buttonPanel != null) {
            buttonPanel.removeAll();
        } else {
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());
            mainPanel.add(buttonPanel);
        }

        JPanel mainList = new JPanel(new GridBagLayout());
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridwidth = GridBagConstraints.REMAINDER;
        gbc2.gridheight = 3;
        gbc2.weightx = 1;

        // add refresh button
        refreshButton = new JButton("Refresh");
        panel.add(refreshButton, gbc2);

        // add bid selection menu
        bidSelection = new JComboBox<>();
        for (int i = 1; i < bidSize + 1; i++) {
            bidSelection.addItem(i);
        }
        panel.add(bidSelection, gbc2);

        // add view offers button
        viewOffersButton = new JButton("View Offers");
        panel.add(viewOffersButton, gbc2);

        errorLabel = new JLabel();
        errorLabel.setForeground(new Color(-4521974));
        errorLabel.setHorizontalAlignment(0);
        errorLabel.setHorizontalTextPosition(0);
        errorLabel.setText(offeringModel.getErrorText());
        panel.add(errorLabel);

        panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 100;
        gbc1.weightx = 100;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(panel, gbc1, 0);
        buttonPanel.add(mainList, BorderLayout.CENTER);
    }

    private void updateView(List<Bid> bidList) {
        if (offeringPanel != null) {
            offeringPanel.removeAll();
        } else {
            offeringPanel = new JPanel();
            offeringPanel.setLayout(new BorderLayout());
            mainPanel.add(offeringPanel);
        }

        JPanel mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 100;
        gbc.weighty = 100;
        mainList.add(new JPanel(), gbc);
        JScrollPane jScrollPane = new JScrollPane(mainList);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        offeringPanel.add(jScrollPane);

        int bidSize = bidList.size();
        for (Bid b: bidList) {
            // Code to generate an open contract panel
                JPanel panel = new JPanel();
                JTable table = ViewUtility.BidAndOfferTable.buildTutorTable(b, bidSize);
                bidSize -= 1;
                ViewUtility.resizeColumns(table);
                table.setBounds(10, 10, 500, 100);
                panel.add(table);

                panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
                GridBagConstraints gbc1 = new GridBagConstraints();
                gbc1.gridwidth = GridBagConstraints.REMAINDER;
                gbc1.gridheight = 2;
                gbc1.weightx = 1;
                gbc1.fill = GridBagConstraints.HORIZONTAL;
                mainList.add(panel, gbc1, 0);
        }
    }

    public int getBidNumber() throws NullPointerException {
        return Integer.parseInt(bidSelection.getSelectedItem().toString());
    }

    @Override
    public void update() {
        refreshContent();
    }
}
