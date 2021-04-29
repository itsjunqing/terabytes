package view.builder;

import lombok.Getter;
import model.BidInfo;
import model.OpenBidModel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

@Getter
public class OpenBidView {
    private OpenBidModel openBidModel;
    private JPanel mainPanel;
    private JPanel openBidPanel;
    private JPanel buttonPanel;
    private JComboBox offerSelection;
    private JButton refreshButton;
    private JButton selectOfferButton;
    // Note: once refresh is called, openBidPanel and buttonPanel will be cleared off, so the buttons will be removed
    // from the BiddingController POV, refreshButton and selectOfferButton need to re-listen after each refresh

    public OpenBidView(OpenBidModel openBidModel) {
        this.openBidModel = openBidModel;
        initView();
    }

    private void initView() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));

        JFrame frame = new JFrame("Open Offers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        updateContent();
    }

    public void updateContent() {
        // query of bid offers need to be done outside to ensure consistent update to both openBidPanel and buttonPanel
        List<BidInfo> bidInfoList = openBidModel.getOpenBidOffers();
        int bidIndex = bidInfoList.size();
        updateView(bidInfoList);
        updateButtons(bidIndex);
    }

    private void updateView(List<BidInfo> bidInfoList) {
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
        openBidPanel.add(new JScrollPane(mainList));

        int bidIndex = bidInfoList.size();
        for (BidInfo b : bidInfoList) {
            // Code to add open bid panel
            JPanel panel = new JPanel();
            JTable table = getOpenBidTable(b, bidIndex);
            bidIndex -= 1;
            resizeColumnWidth(table);
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

    private JTable getOpenBidTable(BidInfo bidInfo, int bidNo) {
        String[][] rec = {
                {"Proposed Contract End Date", ""},
                {"Tutor Name:", ""},
                {"Subject:", ""},
                {"Number of Sessions:", ""},
                {"Day & Time:", ""},
                {"Duration (hours):", ""},
                {"Rate (per hour):", ""},
                {"Free Lesson?", ""},

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
            System.out.println(width);
            if(width > 300)
                width=300;
            if(width < 200)
                width=200;
            columnModel.getColumn(column).setPreferredWidth(width);
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

        // add select offer button
        selectOfferButton = new JButton("Select Offer");
        panel.add(selectOfferButton, gbc2);

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
