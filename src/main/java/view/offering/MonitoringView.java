package view.offering;

import entity.BidInfo;
import lombok.Getter;
import model.offering.MonitoringModel;
import observer.Observer;
import stream.Bid;
import view.ViewUtility;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class MonitoringView implements Observer {

    private MonitoringModel monitoringModel;

    private JPanel mainPanel;
    private JPanel monitoringPanel;
    private JPanel buttonPanel;
    private JComboBox bidSelection;
    private JButton viewOffersButton;

    private JLabel errorLabel;
    private JFrame frame;

    /**
     * Constructor that creates the main frame and calls
     * functions to populate it
     * @param monitoringModel;
     */
    public MonitoringView(MonitoringModel monitoringModel) {
        this.monitoringModel = monitoringModel;

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        frame = new JFrame("Monitoring Dashboard");
        // Updating the panels in the frame
        updateContent();

        // Setting the frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(mainPanel);
//        frame.pack();
        frame.setMinimumSize(new Dimension(860, 400));
        frame.setMaximumSize(new Dimension(1000, 1000));
        frame.setPreferredSize(new Dimension(860, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void dispose() {
        this.frame.dispose();
    }

    /**
     * Function to create the panel for the first time
     */
    private void updateContent() {
        List<Bid> monitoringBids = new ArrayList<>(monitoringModel.getMonitoringBids());
        Collections.reverse(monitoringBids);
        updateView(monitoringBids);
        updateButtons(monitoringBids.size());
        SwingUtilities.updateComponentTreeUI(frame);
    }

    /**
     * Function to refresh the content of the panels without
     * unnecessarily creating new panels
     */
    private void refreshContent(){
        List<Bid> monitoringBids = new ArrayList<>(monitoringModel.getMonitoringBids());
        Collections.reverse(monitoringBids);
        updateView(monitoringBids);
        refreshButtons(monitoringBids.size());
        errorLabel.setText(monitoringModel.getErrorText());
        SwingUtilities.updateComponentTreeUI(frame);
    }


    private void refreshButtons(int bidListSize){
        bidSelection.removeAllItems();
        for (int i = 1; i < bidListSize + 1; i++) {
            bidSelection.addItem(i);
        }
        errorLabel.setText(monitoringModel.getErrorText());
    }

    /**
     * function to create the information panel
     */
    private void updateView(List<Bid> monitoringBids) {
        if (monitoringPanel != null) {
            monitoringPanel.removeAll();
        } else {
            monitoringPanel = new JPanel();
            monitoringPanel.setLayout(new BorderLayout());
            mainPanel.add(monitoringPanel);
        }

        JPanel mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 100;
        gbc.weighty = 100;
        mainList.add(new JPanel(), gbc);

        // add into openBidPanel
        JScrollPane jScrollPane = new JScrollPane(mainList);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        monitoringPanel.add(jScrollPane);

        // initialize gridBagConstraints
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 2;
        gbc1.weightx = 1;
        gbc1.fill = GridBagConstraints.HORIZONTAL;

        // Add latest bids
        for (Bid b: monitoringBids) {
            List <BidInfo> bidOfferList =  b.getAdditionalInfo().getBidOffers();
            int bidOfferSize = bidOfferList.size();
            JPanel panel = new JPanel();
            if ( bidOfferSize > 0) {
                // Code to add open bid panel
                JTable table = ViewUtility.OpenOffersTable.buildTutorOpenOffer(bidOfferList.get(bidOfferSize-1), b);
                ViewUtility.resizeColumns(table);
                table.setBounds(10, 10, 500, 100);
                panel.add(table);
            } else {
                String[][] noOffer = { {"No Offer", "Waiting for offer"}};
                String[] col = {"", ""};
                JTable noOfferTable = new JTable(noOffer, col);
                ViewUtility.resizeColumns(noOfferTable);
                noOfferTable.setBounds(10, 10, 500, 100);
                panel.add(noOfferTable);
            }
            TitledBorder otherTitle = BorderFactory.createTitledBorder("Latest Offer");
            panel.setBorder(otherTitle);
            mainList.add(panel, gbc1, 0);

            // Display student request, will always exist
            JPanel requestPanel = new JPanel();
            JTable requestTable = ViewUtility.OpenOffersTable.buildStudentRequest(b);
            ViewUtility.resizeColumns(requestTable);
            requestTable.setBounds(10, 10, 500, 100);
            requestPanel.add(requestTable);
            TitledBorder titledBorder;
            titledBorder = BorderFactory.createTitledBorder("Student Request");
            requestPanel.setBorder(titledBorder);
            mainList.add(requestPanel, gbc1, 0);
        }
    }

    private void updateButtons(int bidSize) {
        // constructs buttonPanel and add into the mainPanel of the view
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


        // add choose bid combobox
        bidSelection = new JComboBox();
        for (int i = 1; i < bidSize + 1; i ++){
            bidSelection.addItem(i);
        }
        panel.add(bidSelection, gbc2);

        // add View Offers button
        viewOffersButton = new JButton("View Detailed Offers");
        panel.add(viewOffersButton, gbc2);


        errorLabel = new JLabel();
        errorLabel.setForeground(new Color(-4521974));
        errorLabel.setHorizontalAlignment(0);
        errorLabel.setHorizontalTextPosition(0);
        errorLabel.setText("");
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

    public int getBidNumber() throws NullPointerException {
        return Integer.parseInt(bidSelection.getSelectedItem().toString());
    }

    @Override
    public void update() {
        refreshContent();
    }
}
