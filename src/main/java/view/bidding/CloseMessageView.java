package view.bidding;

import entity.MessagePair;
import lombok.Getter;
import observer.Observer;
import stream.Bid;
import view.ViewUtility;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
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
    private Bid bid;
    private JFrame frame;
    private JLabel errorLabel;

    public CloseMessageView(MessagePair messagePair, Bid bid) {
        this.messagePair = messagePair;
        this.bid = bid;

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        frame = new JFrame("Closed Messages");
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
        updateView();
        updateButtons();
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();

    }

    private void refreshContent(){
        updateView();
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
    }

    private void refreshButtons(){
        ;
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
        JScrollPane jScrollPane = new JScrollPane(mainList);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        openBidPanel.add(jScrollPane);

        // Code to add open bid panel

        // code to crete gridBagConstraints
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 2;
        gbc1.weightx = 1;
        gbc1.fill = GridBagConstraints.HORIZONTAL;


        // code to add message panel 2
        JPanel panel1 = new JPanel();
        JTable table2 = ViewUtility.MessageTable.buildTutorTable(messagePair.getTutorMsg(), bid);
        ViewUtility.resizeColumns(table2);
        table2.setBounds(10, 10, 500, 100);
        panel1.add(table2);
        TitledBorder title2;
        title2 = BorderFactory.createTitledBorder("Tutor Bid and Message");
        panel1.setBorder(title2);
        mainList.add(panel1, gbc1, 0);

        // code to add message panel 1
        JPanel panel = new JPanel();
        JTable table = ViewUtility.MessageTable.buildStudentTable(messagePair.getStudentMsg(), bid);
        ViewUtility.resizeColumns(table);
        table.setBounds(10, 10, 500, 100);
        panel.add(table);
        TitledBorder title;
        title = BorderFactory.createTitledBorder("Initial Request and Message");
        panel.setBorder(title);
        mainList.add(panel, gbc1, 0);
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

    @Override
    public void update() {
        updateContent();
    }



}
