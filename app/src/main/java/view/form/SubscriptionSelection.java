package view.form;

import lombok.Getter;
import stream.Bid;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

@Getter
public class SubscriptionSelection extends JFrame {

    private List<Bid> bidsOnGoing;
    private JFrame mainFrame;
    private JPanel contentPane;
    private JLabel errorLabel;
    private JButton confirmButton;
    private JList<Bid> bidJList;

    public SubscriptionSelection(List<Bid> bidsOnGoing) {
        this.bidsOnGoing = bidsOnGoing;
        // setting up the frame
        mainFrame = new JFrame("Subscription Selection");

        // adding to the main JPanel
        contentPane = new JPanel();
        mainFrame.add(contentPane);
        contentPane.setLayout(new GridBagLayout());

        // making the constraints of the button panel
        setDetails();
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setMinimumSize(new Dimension(860, 400));
        mainFrame.setMaximumSize(new Dimension(1000, 1000));
        mainFrame.setPreferredSize(new Dimension(860, 500));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void dispose(){
        mainFrame.dispose();
    }

    private void setDetails(){
        // adding the bids into the list model
        DefaultListModel<Bid> listModel = new DefaultListModel<>();
        for (Bid b: bidsOnGoing) {
            listModel.addElement(b);
        }
        updateContent(listModel);
        updateButtons();
    }

    private void updateContent(DefaultListModel listModel){
        bidJList = new JList<Bid>(listModel);

        // Construct the constraints of JPanel containing the JList
        JPanel panel = new JPanel();
        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.fill = GridBagConstraints.BOTH;
        panelConstraints.weightx = 0.3;
        panelConstraints.weighty = 1;
        panelConstraints.gridx = 0;
        panelConstraints.gridy = 0;
        panel.setLayout(new BorderLayout(0, 0));

        // Construct loweredBevel
        Border loweredBevel = BorderFactory.createLoweredBevelBorder();
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                loweredBevel, "Please Select Bids to Subscribe to");
        titledBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
        panel.setBorder(titledBorder);

        // Construct scrollPane
        JScrollPane bidScrollPane = new JScrollPane();
        panel.add(bidScrollPane, BorderLayout.CENTER);
        bidScrollPane.setViewportView(bidJList);
        contentPane.add(panel, panelConstraints);
    }

    // making the button panel
    private void updateButtons() {
        JPanel mainList = new JPanel(new GridBagLayout()); // TODO, JQ: What's the purpose of this? Remove?
        JPanel buttonPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        buttonPanel.setLayout(layout);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridwidth = GridBagConstraints.REMAINDER;
        gbc2.gridheight = 3;
        gbc2.weightx = 1;

        // add select offer button
        confirmButton = new JButton("Confirm Selection");
        buttonPanel.add(confirmButton, gbc2);
        errorLabel = new JLabel();
        errorLabel.setForeground(new Color(-4521974));
        errorLabel.setHorizontalAlignment(0);
        errorLabel.setHorizontalTextPosition(0);
        // TODO: get error text from the model instead
        errorLabel.setText("");
        buttonPanel.add(errorLabel);

        System.out.println("Adding Buttons");
        buttonPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.fill = GridBagConstraints.BOTH;
        buttonConstraints.weightx = 0.7;
        buttonConstraints.weighty = 1;
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 0;
        contentPane.add(buttonPanel, buttonConstraints);
    }

    public List<Bid> getSelectedBids() {
        return bidJList.getSelectedValuesList();
    }

}
