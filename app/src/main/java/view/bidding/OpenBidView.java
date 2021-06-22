package view.bidding;

import entity.BidInfo;
import lombok.Getter;
import model.bidding.OpenBidModel;
import stream.Bid;
import view.ViewTemplate;
import view.ViewUtility;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class OpenBidView extends ViewTemplate {

    private OpenBidModel openBidModel;
    private JComboBox<Integer> offerSelection;
    private JButton refreshButton;
    private JButton selectOfferButton;
    private JLabel timeLeft;

    public OpenBidView(OpenBidModel openBidModel) {
        this.openBidModel = openBidModel;
        initViewTemplate("Open Offers", JFrame.DISPOSE_ON_CLOSE);
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

        List<BidInfo> bidInfoList = getBidInfoList();
        Bid bid = openBidModel.getBid();

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

        int bidSize = getBidInfoList().size();

        // add offer selection menu
        offerSelection = new JComboBox<Integer>();
        for (int i = 1; i < bidSize + 1; i++) {
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

    protected void refreshButtons() {
        // refreshing jcombobox
        offerSelection.removeAllItems();
        int bidSize = getBidInfoList().size();
        for (int i = 1; i < bidSize + 1; i++) {
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

    private List<BidInfo> getBidInfoList() {
        List<BidInfo> bidInfoList = new ArrayList<>(openBidModel.getOpenBidOffers());
        Collections.reverse(bidInfoList);
        return bidInfoList;
    }

    public int getOfferSelection() throws NullPointerException {
        return Integer.parseInt(offerSelection.getSelectedItem().toString());
    }

}


