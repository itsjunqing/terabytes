package view.builder;

import entity.MessageBidInfo;
import entity.MessagePair;
import lombok.Getter;
import model.CloseBidModel;
import model.OfferingModel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

@Getter
public class CloseOfferView {
    private CloseBidModel closeBidModel;
    private JPanel mainPanel;
    private JPanel openBidPanel;
    private JPanel buttonPanel;
    private JButton refreshButton;
    private JButton respondMessageButton;

    // maybe remove this
    private MessagePair messagePair;
    private OfferingModel offeringModel;
    private int bidIndex;

    // Note: once refresh is called, openBidPanel and buttonPanel will be cleared off, so the buttons will be removed
    // from the BiddingController POV, refreshButton and selectOfferButton need to re-listen after each refresh

    public CloseOfferView(OfferingModel offeringModel, int bidIndex) {
        this.offeringModel = offeringModel;
        this.bidIndex = bidIndex;
        initView();
    }

    private void initView() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));

        updateContent();

        JFrame frame = new JFrame("Close Message View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.pack();
        frame.setMinimumSize(new Dimension(830, 400));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    public void updateContent() {
        // query of bid offers need to be done outside to ensure consistent update to both openBidPanel and buttonPanel
        this.messagePair= offeringModel.getCloseOffers(bidIndex-1);
        updateView(messagePair);
        updateButtons();
    }

    private void updateView(MessagePair messagePair) {
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

        // Code to add open bid panel

        // code to crete gridBagConstraints
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 2;
        gbc1.weightx = 1;
        gbc1.fill = GridBagConstraints.HORIZONTAL;


        if (messagePair.getTutorMsg() != null) {
            // code to add message panel 2
            JPanel panel1 = new JPanel();
            JTable table2 = getTutorMessageTable(messagePair.getTutorMsg());
            resizeColumnWidth(table2);
            table2.setBounds(10, 10, 500, 100);
            panel1.add(table2);
            TitledBorder title2;
            title2 = BorderFactory.createTitledBorder("Our Bid and Message");
            panel1.setBorder(title2);
            mainList.add(panel1, gbc1, 0);
        }else {            // code to add message panel 2
            JPanel panel1 = new JPanel();
            String[][] noOffer = { {"No Offer", " Please Input Offer"}};
            String[] col = {"", ""};
            JTable noOfferTable = new JTable(noOffer, col);

            noOfferTable.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());

            resizeColumnWidth(noOfferTable);
            noOfferTable.setBounds(10, 10, 500, 100);
            panel1.add(noOfferTable);

            TitledBorder title2;
            title2 = BorderFactory.createTitledBorder("Our Bid and Message");
            panel1.setBorder(title2);
            mainList.add(panel1, gbc1, 0);
        };
            // code to add message panel 1
            JPanel panel = new JPanel();
            JTable table = getStudentMessageTable(messagePair.getStudentMsg());
            resizeColumnWidth(table);
            table.setBounds(10, 10, 500, 100);
            panel.add(table);
            TitledBorder title;
            title = BorderFactory.createTitledBorder("Student Request and Message");
            panel.setBorder(title);
            mainList.add(panel, gbc1, 0);






    }

    private JTable getStudentMessageTable(MessageBidInfo messageBidInfo) {
        String freeLesson = new String();
        if (messageBidInfo.isFreeLesson() == true) {
            freeLesson = "Yes";
        } else {
            freeLesson = "No";
        }

        String[][] rec = {
                {"Subject:", ""},
                {"Number of Sessions:", Integer.toString(messageBidInfo.getNumberOfSessions())},
                {"Day & Time:", messageBidInfo.getDay() + " " + messageBidInfo.getTime()},
                {"Duration (hours):", Integer.toString(messageBidInfo.getDuration())},
                {"Rate (per hour):", Integer.toString(messageBidInfo.getRate())},
                {"Free Lesson?:", freeLesson},
                {"Message to Tutor:", messageBidInfo.getContent() + "This subject is really hard, are you really smart enough to teach me"}

        };
        String[] col = {"", ""};
        JTable contractTable = new JTable(rec, col);

        contractTable.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());

        return contractTable;
    }

    private JTable getTutorMessageTable(MessageBidInfo messageBidInfo) {
        String freeLesson = new String();
        if (messageBidInfo.isFreeLesson() == true) {
            freeLesson = "Yes";
        } else {
            freeLesson = "No";
        }

        String[][] rec = {
                {"Tutor Name:", ""},
                {"Subject:", ""},
                {"Number of Sessions:", Integer.toString(messageBidInfo.getNumberOfSessions())},
                {"Day & Time:", messageBidInfo.getDay() + " " + messageBidInfo.getTime()},
                {"Duration (hours):", Integer.toString(messageBidInfo.getDuration())},
                {"Rate (per hour):", Integer.toString(messageBidInfo.getRate())},
                {"Free Lesson?", freeLesson},
                {"Message from tutor:", messageBidInfo.getContent() + "Yes, I am, I got the top in my class in MIT, followed by a fellowship in harvard and I also am a world champion in"}

        };
        String[] col = {"", ""};
        JTable contractTable = new JTable(rec, col);

        contractTable.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());

        return contractTable;
    }


    private class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
        WordWrapCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
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


        respondMessageButton = new JButton("Respond");
        panel.add(respondMessageButton, gbc2);


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
