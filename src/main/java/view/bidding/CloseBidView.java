package view.bidding;

import entity.MessageBidInfo;
import lombok.Getter;
import model.bidding.CloseBidModel;
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
public class CloseBidView implements Observer {
    private CloseBidModel closeBidModel;
    private JPanel mainPanel;
    private JPanel openBidPanel;
    private JPanel buttonPanel;
    private JComboBox offerSelection;
    private JButton refreshButton;
    private JButton viewMessageButton;
    private JButton selectOfferButton;
    private JFrame frame;
    private JLabel errorLabel;
    private JLabel timeLeft;

    public CloseBidView(CloseBidModel closeBidModel) {
        this.closeBidModel = closeBidModel;

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        frame = new JFrame("Closed Offers");

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

    public void dispose() {
        this.frame.dispose();
    }

    private void updateContent() {
        // query of bid offers need to be done outside to ensure consistent update to both openBidPanel and buttonPanel
        Bid bid = closeBidModel.getBid();
        List<MessageBidInfo> messageBidInfoList = new ArrayList<>(closeBidModel.getCloseBidOffers());
        Collections.reverse(messageBidInfoList);
        int bidIndex = messageBidInfoList.size();
        updateView(messageBidInfoList, bid);
        updateButtons(bidIndex);
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
    }

    private void refreshContent(){
        // query of bid offers need to be done outside to ensure consistent update to both openBidPanel and buttonPanel
        Bid bid = closeBidModel.getBid();
        List<MessageBidInfo> messageBidInfoList = new ArrayList<>(closeBidModel.getCloseBidOffers());
        Collections.reverse(messageBidInfoList);
        int bidIndex = messageBidInfoList.size();
        updateView(messageBidInfoList, bid);
        refreshButtons(bidIndex);
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
    }

    private void refreshButtons(int bidIndex){
        // refreshing jcombobox
        offerSelection.removeAllItems();
        for (int i = 1; i < bidIndex + 1; i++) {
            offerSelection.addItem(i);
        }
        // refreshing jlabel
        errorLabel.setText(closeBidModel.getErrorText());

        String time = ViewUtility.getCloseBidTimeLeft(closeBidModel.getBidDate());

        timeLeft = new JLabel();
        timeLeft.setHorizontalAlignment(0);
        timeLeft.setHorizontalTextPosition(0);
        timeLeft.setText(time);
    }

    private void updateView(List<MessageBidInfo> messageBidInfoList, Bid bid) {
        // to be used upon refresh to update both openBidPanel and buttonPanel
        if (openBidPanel != null) {
            openBidPanel.removeAll();
        } else {
            openBidPanel = new JPanel();
            openBidPanel.setLayout(new BorderLayout());
            mainPanel.add(openBidPanel);
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

        int bidIndex = messageBidInfoList.size();
        for (MessageBidInfo b : messageBidInfoList) {
            // Code to add open bid panel
            JPanel panel = new JPanel();
            JTable table = ViewUtility.BidAndOfferTable.buildStudentTable(b, bidIndex, bid);
            bidIndex -= 1;
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

    private void updateButtons(int count) {
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
        gbc2.gridheight = 4;
        gbc2.weightx = 1;

        String time = ViewUtility.getCloseBidTimeLeft(closeBidModel.getBidDate());

        timeLeft = new JLabel();
        timeLeft.setHorizontalAlignment(0);
        timeLeft.setHorizontalTextPosition(0);
        timeLeft.setText(time);
        panel.add(timeLeft, gbc2);

        // add refresh button
        refreshButton = new JButton("Refresh");
        panel.add(refreshButton, gbc2);

        // add offer selection menu
        offerSelection = new JComboBox<>();
        for (int i = 1; i < count + 1; i++) {
            offerSelection.addItem(i);
        }
        panel.add(offerSelection, gbc2);
        viewMessageButton = new JButton("Respond");
        panel.add(viewMessageButton, gbc2);
        // add select offer button
        selectOfferButton = new JButton("Select Offer");
        panel.add(selectOfferButton, gbc2);

        errorLabel = new JLabel();
        errorLabel.setForeground(new Color(-4521974));
        errorLabel.setHorizontalAlignment(0);
        errorLabel.setHorizontalTextPosition(0);
        errorLabel.setText(closeBidModel.getErrorText());
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

    public int getOfferSelection() throws NullPointerException {
        return Integer.parseInt(offerSelection.getSelectedItem().toString());
    }

    @Override
    public void update() {
        refreshContent();
    }
}

/*
 private JTable getOpenBidTable(MessageBidInfo messageBidInfo, int bidNo, Bid bid) {
        String freeLesson = messageBidInfo.isFreeLesson()? "Yes": "No";

        String[][] rec = {
                {"Offer Number: ", Integer.toString(bidNo)},
                {"Tutor Name:", Utility.getFullName(messageBidInfo.getInitiatorId())},
                {"Subject:", bid.getSubject().getName()},
                {"Number of Sessions:", Integer.toString(messageBidInfo.getNumberOfSessions())},
                {"Day & Time:", messageBidInfo.getDay() + " " + messageBidInfo.getTime()},
                {"Duration (hours):", Integer.toString(messageBidInfo.getDuration())},
                {"Rate (per hour):", Integer.toString(messageBidInfo.getRate())},
                {"Free Lesson?", freeLesson},

        };
        String[] col = {"", ""};
        JTable contractTable = new JTable(rec, col);
        return contractTable;
    }
 */
