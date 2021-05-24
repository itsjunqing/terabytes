package view.offering;

import entity.BidInfo;
import lombok.Getter;
import model.offering.MonitoringModel;
import stream.Bid;
import view.ViewUtility;
import view.ViewTemplate;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class MonitoringView extends ViewTemplate {

    private MonitoringModel monitoringModel;
    private JComboBox bidSelection;
    private JButton viewOffersButton;

    /**
     * Constructor that creates the main frame and calls
     * functions to populate it
     * @param monitoringModel;
     */
    public MonitoringView(MonitoringModel monitoringModel) {
        this.monitoringModel = monitoringModel;
        makeMainPanel();
        makeFrame("Monitoring Dashboard", JFrame.DISPOSE_ON_CLOSE);
        // Updating the panels in the frame
        updateContent();
    }

    public void dispose() {
        this.frame.dispose();
    }

    private List<Bid> getBids() {
        List<Bid> monitoringBids = new ArrayList<>(monitoringModel.getMonitoringBids());
        Collections.reverse(monitoringBids);
        return monitoringBids;
    }

    /**
     * Function to create the panel for the first time
     */
    protected void updateContent() {
        updateView();
        createButtons();
        SwingUtilities.updateComponentTreeUI(frame);
    }

    /**
     * Function to refresh the content of the panels without
     * unnecessarily creating new panels
     */
    protected void refreshContent(){
        updateView();
        refreshButtons();
        SwingUtilities.updateComponentTreeUI(frame);
    }


    protected void refreshButtons(){
        bidSelection.removeAllItems();
        int bidListSize = getBids().size();
        for (int i = 1; i < bidListSize + 1; i++) {
            bidSelection.addItem(i);
        }
        errorLabel.setText(monitoringModel.getErrorText());
    }

    /**
     * function to create the information panel
     */
    protected void updateView() {
        if (contentPanel != null) {
            contentPanel.removeAll();
        } else {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            mainPanel.add(contentPanel);
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
        contentPanel.add(jScrollPane);

        // initialize gridBagConstraints
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 2;
        gbc1.weightx = 1;
        gbc1.fill = GridBagConstraints.HORIZONTAL;

        List<Bid> monitoringBids = getBids();

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

    protected void createButtons() {
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

        int bidListSize = getBids().size();

        // add choose bid combobox
        bidSelection = new JComboBox();
        for (int i = 1; i < bidListSize + 1; i ++){
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


}
