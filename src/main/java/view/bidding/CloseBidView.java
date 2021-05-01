package view.bidding;

import entity.MessageBidInfo;
import lombok.Getter;
import model.bidding.CloseBidModel;
import observer.Observer;
import stream.Bid;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
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

    // Note: once refresh is called, openBidPanel and buttonPanel will be cleared off, so the buttons will be removed
    // from the BiddingController POV, refreshButton and selectOfferButton need to re-listen after each refresh

    public CloseBidView(CloseBidModel closeBidModel) {
        this.closeBidModel = closeBidModel;
        initView();
    }

    private void initView() {
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

    public void updateContent() {
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
            JTable table = getOpenBidTable(b, bidIndex, bid);
            bidIndex -= 1;
            resizeColumns(table);
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

    private JTable getOpenBidTable(MessageBidInfo messageBidInfo, int bidNo, Bid bid) {
        String freeLesson = new String();
        if (messageBidInfo.isFreeLesson() == true) {
            freeLesson = "Yes";
        } else {
            freeLesson = "No";
        }

        String[][] rec = {
                {"Offer Number: ", Integer.toString(bidNo)},
                {"Tutor Name:", this.closeBidModel.getUserName(messageBidInfo.getInitiatorId())},
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

    private void resizeColumns(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        int colCount = table.getColumnCount();
        int rowCount = table.getRowCount();
        for (int c = 0; c < colCount; c++) {
            int width = 20;
            for (int r = 0; r < rowCount; r++) {
                TableCellRenderer defaultRenderer = table.getCellRenderer(r, c);
                int defaultSize = table.prepareRenderer(defaultRenderer, r, c).getPreferredSize().width + 1;
                if (width < defaultSize){
                    width = defaultSize;
                }
            }
            if(width > 300)
                width=300;
            if(width < 200)
                width=200;
            columnModel.getColumn(c).setPreferredWidth(width);
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
        gbc2.gridheight = 3;
        gbc2.weightx = 1;

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
        if (closeBidModel.isExpired()) {
            errorLabel.setText("This Bid has expired, please make a new one");
        } else {
            errorLabel.setText("");
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

    public int getOfferSelection() throws NullPointerException {
        return Integer.parseInt(offerSelection.getSelectedItem().toString());
    }

    @Override
    public void update() {
        refreshContent();
    }
}
