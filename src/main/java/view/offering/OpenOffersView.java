package view.offering;

import entity.BidInfo;
import lombok.Getter;
import model.offering.OpenOffersModel;
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
public class OpenOffersView implements Observer {
    private JPanel mainPanel;
    private JPanel openBidPanel;
    private JPanel buttonPanel;
    private JButton refreshButton;
    private JButton respondButton;
    private JButton buyOutButton;
    private OpenOffersModel openOffersModel;
    private JLabel errorLabel;
    private JFrame frame;

    public OpenOffersView(OpenOffersModel offeringModel) {
        this.openOffersModel = offeringModel;

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        frame = new JFrame("All Open Offers for this Bid");
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

    private void updateContent() {

        // getting the constants from the model
        List<BidInfo> otherBidInfo = new ArrayList<>(openOffersModel.getOpenOffers());
        BidInfo myBidInfo = openOffersModel.getMyOffer();
        Bid bid = openOffersModel.getBid();
        // making the frames 
        updateView(otherBidInfo, myBidInfo, bid);
        updateButtons();
        SwingUtilities.updateComponentTreeUI(frame);
//        frame.pack();
    }

    private void refreshContent(){
        // getting the constants from the model
        List<BidInfo> otherBidInfo = new ArrayList<>(openOffersModel.getOpenOffers());

        System.out.println("From OpenOffersView refreshContent function");
        otherBidInfo.stream().forEach(e -> System.out.println(e.toString()));

        BidInfo myBidInfo = openOffersModel.getMyOffer();
        Bid bid = openOffersModel.getBid();
        // making the frames
        updateView(otherBidInfo, myBidInfo, bid);
        refreshButtons();
        SwingUtilities.updateComponentTreeUI(frame);
//        frame.pack();
    }

    private void refreshButtons(){
        errorLabel.setText(openOffersModel.getErrorText());
    }

    private void updateView(List<BidInfo> otherBidInfo, BidInfo myBidInfo, Bid bid) {
        // to be used upon refresh to update both openBidPanel and buttonPanel
        if (openBidPanel != null) {
            openBidPanel.removeAll();
        } else {
            openBidPanel = new JPanel();
            openBidPanel.setLayout(new BorderLayout());
            mainPanel.add(openBidPanel);
        }
        // if bid has expired, return empty panel
        if (bid.getDateClosedDown() != null){
            return;
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
        openBidPanel.add(jScrollPane);

        // initialize gridBagConstraits
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 2;
        gbc1.weightx = 1;
        gbc1.fill = GridBagConstraints.HORIZONTAL;

        Collections.reverse(otherBidInfo);

        for (BidInfo b : otherBidInfo) {
            // Code to add open bid panel
            JPanel panel = new JPanel();
            JTable table = ViewUtility.OpenOffersTable.buildTutorOpenOffer(b, bid);
            ViewUtility.resizeColumns(table);
            table.setBounds(10, 10, 500, 100);
            panel.add(table);
            panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
            mainList.add(panel, gbc1, 0);
        }

        // display current bid (if it exist)
        if (myBidInfo != null) {
            // Code to add open bid panel
            JPanel myBidPanel = new JPanel();
            JTable myBidTable = ViewUtility.OpenOffersTable.buildTutorOpenOffer(myBidInfo, bid);
            ViewUtility.resizeColumns(myBidTable);
            myBidPanel.setBounds(10, 10, 500, 100);
            myBidPanel.add(myBidTable);
            TitledBorder myOfferTitle;
            myOfferTitle = BorderFactory.createTitledBorder("My Offer");
            myBidPanel.setBorder(myOfferTitle);
            mainList.add(myBidPanel, gbc1, 0);

        }else{
            JPanel myBidPanel = new JPanel();
            String[][] noOffer = { {"No Offer", " Please Input Offer"}};
            String[] col = {"", ""};
            JTable noOfferTable = new JTable(noOffer, col);

            ViewUtility.resizeColumns(noOfferTable);
            noOfferTable.setBounds(10, 10, 500, 100);
            myBidPanel.add(noOfferTable);
            TitledBorder myOfferTitle;
            myOfferTitle = BorderFactory.createTitledBorder("My Offer");
            myBidPanel.setBorder(myOfferTitle);
            mainList.add(myBidPanel, gbc1, 0);
        }

        // display request first
        JPanel requestPanel = new JPanel();
        JTable requestTable = ViewUtility.OpenOffersTable.buildStudentRequest(bid);
        ViewUtility.resizeColumns(requestTable);
        requestTable.setBounds(10, 10, 500, 100);
        requestPanel.add(requestTable);
        TitledBorder titledBorder;
        titledBorder = BorderFactory.createTitledBorder("Student Request");
        requestPanel.setBorder(titledBorder);
        mainList.add(requestPanel, gbc1, 0);
    }

    private void updateButtons() {
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

        // add refresh button
        refreshButton = new JButton("Refresh");
        panel.add(refreshButton, gbc2);

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
        errorLabel.setText(openOffersModel.getErrorText());
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
