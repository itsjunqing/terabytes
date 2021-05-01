package view.offering;

import entity.BidInfo;
import lombok.Getter;
import model.offering.OpenOffersModel;
import observer.Observer;
import stream.Bid;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
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

    // Note: once refresh is called, openBidPanel and buttonPanel will be cleared off, so the buttons will be removed
    // from the BiddingController POV, refreshButton and selectOfferButton need to re-listen after each refresh

    public OpenOffersView(OpenOffersModel offeringModel) {
        this.openOffersModel = offeringModel;
        initView();
    }

    private void initView() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        frame = new JFrame("All Open Offers for this Bid");
        // Updating the panels in the frame
        updateContent();

        // Setting the frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(mainPanel);
        frame.pack();
        frame.setMinimumSize(new Dimension(830, 400));
        frame.setMaximumSize(new Dimension(1000, 800));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void dispose() {
        this.frame.dispose();
    }

    public void updateContent() {

        // getting the constants from the model
        List<BidInfo> otherBidInfo = new ArrayList<>(openOffersModel.getOpenOffers());
        BidInfo myBidInfo = openOffersModel.getMyOffer();
        Bid bid = openOffersModel.getBid();
        // making the frames 
        updateView(otherBidInfo, myBidInfo, bid);
        updateButtons();
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
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
        openBidPanel.add(new JScrollPane(mainList));

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
            JTable table = getOpenBidTable(b, bid);
            resizeColumnWidth(table);
            table.setBounds(10, 10, 500, 100);
            panel.add(table);
            panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
            mainList.add(panel, gbc1, 0);
        }

        // display current bid (if it exist)
        if (myBidInfo != null) {
            // Code to add open bid panel
            JPanel myBidPanel = new JPanel();
            JTable myBidTable = getMyTable(myBidInfo, bid);
            resizeColumnWidth(myBidTable);
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

            resizeColumnWidth(noOfferTable);
            noOfferTable.setBounds(10, 10, 500, 100);
            myBidPanel.add(noOfferTable);
            TitledBorder myOfferTitle;
            myOfferTitle = BorderFactory.createTitledBorder("My Offer");
            myBidPanel.setBorder(myOfferTitle);
            mainList.add(myBidPanel, gbc1, 0);
        }


        // display request first

        JPanel requestPanel = new JPanel();
        JTable requestTable = getRequest(bid);
        resizeColumnWidth(requestTable);
        requestTable.setBounds(10, 10, 500, 100);
        requestPanel.add(requestTable);
        TitledBorder titledBorder;
        titledBorder = BorderFactory.createTitledBorder("Student Request");
        requestPanel.setBorder(titledBorder);
        mainList.add(requestPanel, gbc1, 0);
    }



    private JTable getOpenBidTable(BidInfo bidInfo, Bid bid) {
        String freeLesson;
        if (bidInfo.isFreeLesson()) {
            freeLesson = "Yes";
        } else {
            freeLesson = "No";
        }

        String[][] rec = {
                {"Tutor Name:", this.openOffersModel.getUserName(bidInfo.getInitiatorId())},
                {"Subject:", bid.getSubject().getName()},
                {"Number of Sessions:", Integer.toString(bidInfo.getNumberOfSessions())},
                {"Day & Time:", bidInfo.getDay() + " " + bidInfo.getTime()},
                {"Duration (hours):", Integer.toString(bidInfo.getDuration())},
                {"Rate (per hour):", Integer.toString(bidInfo.getRate())},
                {"Free Lesson?", freeLesson},

        };
        String[] col = {"", ""};
        return new JTable(rec, col);
    }

    private JTable getMyTable(BidInfo bidInfo, Bid bid) {
        String freeLesson;
        if (bidInfo.isFreeLesson()) {
            freeLesson = "Yes";
        } else {
            freeLesson = "No";
        }

        String[][] rec = {
                {"Subject:", bid.getSubject().getName()},
                {"Number of Sessions:", Integer.toString(bidInfo.getNumberOfSessions())},
                {"Day & Time:", bidInfo.getDay() + " " + bidInfo.getTime()},
                {"Duration (hours):", Integer.toString(bidInfo.getDuration())},
                {"Rate (per hour):", Integer.toString(bidInfo.getRate())},
                {"Free Lesson?", freeLesson},

        };
        String[] col = {"", ""};
        return new JTable(rec, col);
    }

    private JTable getRequest(Bid bidObject) {
        String[][] rec = {
                {"Bid Type", "Open"},
                {"Student Name:", bidObject.getInitiator().getGivenName() + " " + bidObject.getInitiator().getFamilyName()},
                {"Subject:", bidObject.getSubject().getName()},
                {"Number of Sessions:", Integer.toString(bidObject.getAdditionalInfo().getBidPreference().getPreferences().getNumberOfSessions())},
                {"Day & Time:", bidObject.getAdditionalInfo().getBidPreference().getPreferences().getDay() + " " + bidObject.getAdditionalInfo().getBidPreference().getPreferences().getTime()},
                {"Duration (hours):", Integer.toString(bidObject.getAdditionalInfo().getBidPreference().getPreferences().getDuration())},
                {"Rate (per hour):", Integer.toString(bidObject.getAdditionalInfo().getBidPreference().getPreferences().getRate())},
        };
        String[] col = {"", ""};
        JTable contractTable = new JTable(rec, col);
        return contractTable;
    }

    // TODO: this is from https://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths, rewrite
    private void resizeColumnWidth(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
//            System.out.println(width);
            if(width > 300)
                width=300;
            if(width < 200)
                width=200;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
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
        if (openOffersModel.isExpired()) {
            errorLabel.setText("Bid has expired, please pick another one");
        } else {
            errorLabel.setText(""); // TODO: do we need this? or can remove it?
        }
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
        updateContent();
    }
}
