package view.offering;

import entity.MessagePair;
import lombok.Getter;
import model.offering.CloseOffersModel;
import view.ViewUtility;
import view.ViewTemplate;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

@Getter
public class CloseOfferView extends ViewTemplate {

    private CloseOffersModel closeOffersModel;
    private JButton refreshButton;
    private JButton respondMessageButton;

    public CloseOfferView(CloseOffersModel closeOffersModel) {
        this.closeOffersModel = closeOffersModel;
        makeMainPanel();
        updateView();
        updateButtons();
        makeFrame("Close Message View", JFrame.DISPOSE_ON_CLOSE);
    }

    public void dispose() {
        this.frame.dispose();
    }

    protected void refreshContent(){
        // refreshing jcombobox
        updateView();
        refreshButtons();
        SwingUtilities.updateComponentTreeUI(frame);
    }

    protected void refreshButtons(){
        errorLabel.setText(closeOffersModel.getErrorText());
    }

    protected void updateView() {
        MessagePair messagePair = closeOffersModel.getMessagePair();

        // to be used upon refresh to update both openBidPanel and buttonPanel
        if (contentPanel != null) {
            contentPanel.removeAll();
        } else {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            mainPanel.add(contentPanel);
        }
        if (messagePair == null){
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

        // code to crete gridBagConstraints
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 2;
        gbc1.weightx = 1;
        gbc1.fill = GridBagConstraints.HORIZONTAL;


        if (messagePair.getTutorMsg() != null) {
            // code to add message panel 2
            JPanel panel1 = new JPanel();
            JTable table2 = ViewUtility.MessageTable.buildTutorTable(messagePair.getTutorMsg(), closeOffersModel.getBid());
            ViewUtility.resizeColumns(table2);
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

            noOfferTable.getColumnModel().getColumn(1).setCellRenderer(new ViewUtility.WordWrapCellRenderer());

            ViewUtility.resizeColumns(noOfferTable);
            noOfferTable.setBounds(10, 10, 500, 100);
            panel1.add(noOfferTable);

            TitledBorder title2;
            title2 = BorderFactory.createTitledBorder("Our Bid and Message");
            panel1.setBorder(title2);
            mainList.add(panel1, gbc1, 0);
        }
            // code to add message panel 1
            JPanel panel = new JPanel();
            System.out.println("this is the student message:");
            System.out.println(messagePair.getStudentMsg().toString());
            JTable table = ViewUtility.MessageTable.buildStudentTable(messagePair.getStudentMsg(), closeOffersModel.getBid());
            ViewUtility.resizeColumns(table);
            table.setBounds(10, 10, 500, 100);
            panel.add(table);
            TitledBorder title;
            title = BorderFactory.createTitledBorder("Student Request and Message");
            panel.setBorder(title);
            mainList.add(panel, gbc1, 0);
    }

    protected void updateButtons() {
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
        errorLabel = new JLabel();
        errorLabel.setForeground(new Color(-4521974));
        errorLabel.setHorizontalAlignment(0);
        errorLabel.setHorizontalTextPosition(0);
        errorLabel.setText(closeOffersModel.getErrorText());
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
