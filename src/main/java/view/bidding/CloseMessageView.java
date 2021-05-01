package view.bidding;

import entity.MessageBidInfo;
import entity.MessagePair;
import lombok.Getter;
import observer.Observer;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;


/**
 * This might need to be updated to cater for the latest Bidding design
 */
@Getter
public class CloseMessageView implements Observer {

    private JPanel mainPanel;
    private JPanel openBidPanel;
    private JPanel buttonPanel;
    private JButton refreshButton; // TODO: remove this
    private JButton respondMessageButton; // only keep a respond button
    private JButton selectBidButton; // TODO: remove this
    private MessagePair messagePair;
    private JFrame frame;
    private JLabel errorLabel;

    public CloseMessageView(MessagePair messagePair) {
        this.messagePair = messagePair;
        initView();
    }

    private void initView() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));

        updateContent();

        frame = new JFrame("Closed Messages");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(mainPanel);
        frame.pack();
        frame.setMinimumSize(new Dimension(830, 400));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void dispose() {
        this.frame.dispose();
    }

    private void updateContent() {
        updateView();
        updateButtons();
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();

    }

    private void updateView() {
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


        // code to add message panel 2
        JPanel panel1 = new JPanel();
        JTable table2 = getTutorMessageTable(messagePair.getTutorMsg());
        resizeColumnWidth(table2);
        table2.setBounds(10, 10, 500, 100);
        panel1.add(table2);
        TitledBorder title2;
        title2 = BorderFactory.createTitledBorder("Tutor Bid and Message");
        panel1.setBorder(title2);
        mainList.add(panel1, gbc1, 0);

        // code to add message panel 1
        JPanel panel = new JPanel();
        JTable table = getStudentMessageTable(messagePair.getStudentMsg());
        resizeColumnWidth(table);
        table.setBounds(10, 10, 500, 100);
        panel.add(table);
        TitledBorder title;
        title = BorderFactory.createTitledBorder("Initial Request and Message");
        panel.setBorder(title);
        mainList.add(panel, gbc1, 0);
    }

    private JTable getStudentMessageTable(MessageBidInfo messageBidInfo) {
        String freeLesson;
        if (messageBidInfo.isFreeLesson()) {
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
                {"Message to Tutor:", messageBidInfo.getContent() }

        };
        String[] col = {"", ""};
        JTable contractTable = new JTable(rec, col);

        contractTable.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());

        return contractTable;
    }

    private JTable getTutorMessageTable(MessageBidInfo messageBidInfo) {
        String freeLesson;
        if (messageBidInfo.isFreeLesson()) {
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
                {"Message from tutor:", messageBidInfo.getContent()}

        };
        String[] col = {"", ""};
        JTable contractTable = new JTable(rec, col);

        contractTable.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());

        return contractTable;
    }

    @Override
    public void update() {
        updateContent();
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

        selectBidButton =new JButton("Select Bid");
        panel.add(selectBidButton, gbc2);

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

}
