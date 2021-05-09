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
    private JPanel mainPanel;
    private JPanel openBidPanel;
    private JPanel buttonPanel;
    private JComboBox bidSelection;
    private JButton respondButton;
    private JButton buyOutButton;
    private MonitoringModel monitoringModel;
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

        // getting the constants from the model
        List<Bid> selectedBids = new ArrayList<>(monitoringModel.getSelectedBids());
        // making the frames
        updateView(selectedBids);
        createButtons(selectedBids.size());
        SwingUtilities.updateComponentTreeUI(frame);
//        frame.pack();
    }

    /**
     * Function to refresh the content of the panels without
     * unecessarily creating new panels
     */
    private void refreshContent(){
        // getting the constants from the model
        List<Bid> selectedBids = new ArrayList<>(monitoringModel.getSelectedBids());

        System.out.println("From OpenOffersView refreshContent function");
        selectedBids.stream().forEach(e -> System.out.println(e.toString()));

        // making the frames
        updateView(selectedBids);
        // refreshing info in buttons
        refreshButtons(selectedBids.size());
        SwingUtilities.updateComponentTreeUI(frame);
//        frame.pack();
    }


    private void refreshButtons(int bidListSize){
        for (int i = 1; i < bidListSize + 1; i++) {
            bidSelection.addItem(i);
        }
        errorLabel.setText(monitoringModel.getErrorText());
    }

    /**
     * function to create the information panel
     */
    private void updateView(List<Bid> choosenBidList) {
        // to be used upon refresh to update both openBidPanel and buttonPanel
        if (openBidPanel != null) {
            openBidPanel.removeAll();
        } else {
            openBidPanel = new JPanel();
            openBidPanel.setLayout(new BorderLayout());
            mainPanel.add(openBidPanel);
        }
        // if bid has expired, return empty panel


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
        openBidPanel.add(jScrollPane);

        // initialize gridBagConstraits
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 2;
        gbc1.weightx = 1;
        gbc1.fill = GridBagConstraints.HORIZONTAL;

        Collections.reverse(choosenBidList);

        for (Bid b:choosenBidList) {
        /**
         * Add the latest other bids
         */
            List <BidInfo> bidOfferList =  b.getAdditionalInfo().getBidOffers();
            int bidOfferSize = bidOfferList.size();
            JPanel panel = new JPanel();
            if ( bidOfferSize > 0){
                // Code to add open bid panel
                JTable table = ViewUtility.OpenOffersTable.buildTutorOpenOffer(bidOfferList.get(bidOfferSize-1), b);
                ViewUtility.resizeColumns(table);
                table.setBounds(10, 10, 500, 100);
                panel.add(table);
            }else{
                String[][] noOffer = { {"No Offer"}};
                String[] col = {"", ""};
                JTable noOfferTable = new JTable(noOffer, col);
                ViewUtility.resizeColumns(noOfferTable);
                noOfferTable.setBounds(10, 10, 500, 100);
                panel.add(noOfferTable);
            }
            TitledBorder otherTitle = BorderFactory.createTitledBorder("Latest Offer");
            panel.setBorder(otherTitle);
            mainList.add(panel, gbc1, 0);

            /**
             * Display student request, will always exist
             */
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

    private void createButtons(int choosenSize) {
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
        for (int i = 1; i < choosenSize + 1; i ++){
            bidSelection.addItem(i);
        }

        // add Provide Offer button
        respondButton = new JButton("Provide Offer");
        panel.add(respondButton, gbc2);



        // add select offer button
        buyOutButton = new JButton("Buy Out");
        panel.add(buyOutButton, gbc2);

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

    @Override
    public void update() {
        refreshContent();
    }
}
