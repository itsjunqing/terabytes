package view.offering;

import entity.BidInfo;
import lombok.Getter;
import model.offering.OpenOffersModel;
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
public class OpenOffersView extends ViewTemplate {

    private OpenOffersModel openOffersModel;
    private JButton refreshButton;
    private JButton respondButton;
    private JButton buyOutButton;

    public OpenOffersView(OpenOffersModel offeringModel) {
        this.openOffersModel = offeringModel;
        makeMainPanel();
        updateContent();
        makeFrame("All Open Offers for this Bid", JFrame.DISPOSE_ON_CLOSE);
    }

    public void dispose() {
        this.frame.dispose();
    }

    private List<BidInfo> getBidInfos() {
        List<BidInfo> otherBidInfo = new ArrayList<>(openOffersModel.getOpenOffers());
        Collections.reverse(otherBidInfo);
        return otherBidInfo;
    }

    private BidInfo getMyBidInfo() {
        return openOffersModel.getMyOffer();
    }

    protected void updateContent() {
        // making the frames
        updateView();
        createButtons();
        SwingUtilities.updateComponentTreeUI(frame);
    }

    protected void refreshContent(){
        // making the frames
        updateView();
        refreshButtons();
        SwingUtilities.updateComponentTreeUI(frame);
    }

    protected void refreshButtons(){
        errorLabel.setText(openOffersModel.getErrorText());
    }

    protected void updateView() {
        // to be used upon refresh to update both openBidPanel and buttonPanel
        if (contentPanel != null) {
            contentPanel.removeAll();
        } else {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            mainPanel.add(contentPanel);
        }
        Bid bid = openOffersModel.getBid();
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
        contentPanel.add(jScrollPane);

        // initialize gridBagConstraits
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 2;
        gbc1.weightx = 1;
        gbc1.fill = GridBagConstraints.HORIZONTAL;


        List<BidInfo> otherBidInfo = getBidInfos();
        BidInfo myBidInfo = getMyBidInfo();

        /**
         * First add all the "other bids", if empty, return empty panel
         */
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
        /**
         * Display my offer, may be empty
         */
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

        /**
         * Display student request, will always exist
         */
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


}
