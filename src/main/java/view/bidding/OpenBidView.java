package view.bidding;

import entity.BidInfo;
import lombok.Getter;
import model.bidding.OpenBidModel;
import observer.Observer;
import stream.Bid;
import view.ViewUtility;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OpenBidView implements Observer {
    private OpenBidModel openBidModel;
    private final JPanel mainPanel;
    private JPanel openBidPanel;
    private JPanel buttonPanel;

    private JComboBox<Integer> offerSelection;
    private JButton refreshButton;
    private JButton selectOfferButton;
    private JFrame frame;
    private JLabel errorLabel;
    private JLabel timeLeft;

    public OpenBidView(OpenBidModel openBidModel) {
        this.openBidModel = openBidModel;
        System.out.println(this.getClass().getName() + " is initiating");
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        frame = new JFrame("Open Offers");
        updateContent();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(mainPanel);
//        frame.pack();
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
        Bid bid = openBidModel.getBid();
        List<BidInfo> bidInfoList = new ArrayList<>(openBidModel.getOpenBidOffers());
//        Collections.reverse(bidInfoList);

        int bidIndex = bidInfoList.size();
        updateView(bidInfoList, bid);
        updateButtons(bidIndex);
        SwingUtilities.updateComponentTreeUI(frame);
//        frame.pack();
    }

    private void refreshContent(){
        // query of bid offers need to be done outside to ensure consistent update to both openBidPanel and buttonPanel
        Bid bid = openBidModel.getBid();
        List<BidInfo> bidInfoList = new ArrayList<>(openBidModel.getOpenBidOffers());
        System.out.println("From OpenBidView refreshContent function: ");
        bidInfoList.stream().forEach(b -> System.out.println(b.toString()));
//        Collections.reverse(bidInfoList);

        int bidIndex = bidInfoList.size();
        updateView(bidInfoList, bid);
        refreshButtons(bidIndex);
        SwingUtilities.updateComponentTreeUI(frame);
//        frame.pack();
    }

    private void refreshButtons(int bidIndex){
        // refreshing jcombobox
        offerSelection.removeAllItems();
        for (int i = 1; i < bidIndex + 1; i++) {
            offerSelection.addItem(i);
        }
        // refreshing jlabel
        errorLabel.setText(openBidModel.getErrorText());

        String time = ViewUtility.getOpenBidTimeLeft(openBidModel.getBidDate());
        System.out.println("Open bid time left");
        System.out.println(time);
        timeLeft.setHorizontalAlignment(0);
        timeLeft.setHorizontalTextPosition(0);
        timeLeft.setText(time);
    }

    private void updateView(List<BidInfo> bidInfoList, Bid bid) {
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


        int bidIndex = bidInfoList.size();
        for (BidInfo b : bidInfoList) {
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

        String time = ViewUtility.getOpenBidTimeLeft(openBidModel.getBidDate());

        System.out.println(time);

        timeLeft = new JLabel();
        timeLeft.setHorizontalAlignment(0);
        timeLeft.setHorizontalTextPosition(0);
        timeLeft.setText(time);
        panel.add(timeLeft, gbc2);

        // add refresh button
        refreshButton = new JButton("Refresh");
        panel.add(refreshButton, gbc2);

        // add offer selection menu
        offerSelection = new JComboBox<Integer>();
        for (int i = 1; i < count + 1; i++) {
            offerSelection.addItem(i);
        }
        panel.add(offerSelection, gbc2);

        // add select offer button
        selectOfferButton = new JButton("Select Offer");
        panel.add(selectOfferButton, gbc2);
        errorLabel = new JLabel();
        errorLabel.setForeground(new Color(-4521974));
        errorLabel.setHorizontalAlignment(0);
        errorLabel.setHorizontalTextPosition(0);
        errorLabel.setText(openBidModel.getErrorText());
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

    private JTable getOpenBidTable(BidInfo bidInfo, int bidNo, Bid bid) {
        String freeLesson = bidInfo.isFreeLesson()? "Yes": "No";
        String[][] rec = {
                {"Offer Number: ", Integer.toString(bidNo)},
                {"Tutor Name:", this.openBidModel.getUserName(bidInfo.getInitiatorId())},
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
 */
