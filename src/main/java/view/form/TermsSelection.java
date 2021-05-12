package view.form;

import lombok.Getter;
import stream.Contract;
import view.ViewUtility;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class TermsSelection {

    private List<Contract> contracts;

    private JPanel mainPanel; // mainPanel holds both contractPanel and buttons
    private JPanel contractPanel; // used to clear and update the content, only this need to be updated
    private JComboBox contractSelectionBox;
    private JButton renewNewTermsButton;
    private JButton renewOldTermsButton;
//    private JLabel errorLabel;
    private JFrame frame;
    private JPanel buttonPanel;

    public TermsSelection(List<Contract> contracts) {
        this.contracts = new ArrayList<>(contracts);
        Collections.reverse(this.contracts);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        frame = new JFrame("Contract Terms Selection");
        updateContracts();
        addButtons(contracts.size());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(mainPanel);
        // resizing if its smaller than the default size
        frame.setMinimumSize(new Dimension(860, 400));
        frame.setMaximumSize(new Dimension(860, 1000));
        frame.setPreferredSize(new Dimension(860, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void dispose() {
        frame.dispose();
    }

    // TODO: remove, unused
//    protected void refreshContent(){
//        updateContracts();
//        refreshButtons(contracts.size());
//        SwingUtilities.updateComponentTreeUI(frame);
////        frame.pack();
//    }

    public void updateContracts() {
        contractPanel = new JPanel();
        contractPanel.setLayout(new BorderLayout());
        mainPanel.add(contractPanel);

        JPanel mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 100;
        gbc.weighty = 100;
        mainList.add(new JPanel(), gbc);

        // add component into contractPanel. Note that contractPanel by default is in mainPanel, so just modify contractPanel will do
        JScrollPane jScrollPane = new JScrollPane(mainList);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        contractPanel.add(jScrollPane);

        // get the list of contracts and update accordingly
        int contractIndex = contracts.size();
        for (Contract c: contracts) {
            JPanel panel = new JPanel();
            JTable table = ViewUtility.ContractTable.buildStudentTable(c, contractIndex);
            contractIndex -= 1;
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

    // TODO: unused
//    private void refreshButtons(int contractListSize){
//        for (int i = 1; i < contractListSize+1; i++){
//            contractSelectionBox.addItem(i);
//        }
//    }

    private void addButtons(int contractListSize) {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        mainPanel.add(buttonPanel);

        JPanel mainList = new JPanel(new GridBagLayout());
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridwidth = GridBagConstraints.REMAINDER;
        gbc2.gridheight = 3;
        gbc2.weightx = 1;

        contractSelectionBox = new JComboBox();
        for (int i = 1; i < contractListSize+1; i++){
            contractSelectionBox.addItem(i);
        }
        panel.add(contractSelectionBox, gbc2);

        renewNewTermsButton = new JButton("Renew with New Terms");
        panel.add(renewNewTermsButton, gbc2);

        renewOldTermsButton = new JButton("Renew with Old (existing) Terms");
        panel.add(renewOldTermsButton, gbc2);

        // TODO: removing this
//        errorLabel = new JLabel();
//        errorLabel.setForeground(new Color(-4521974));
//        errorLabel.setHorizontalAlignment(0);
//        errorLabel.setHorizontalTextPosition(0);
////        errorLabel.setText(contractRenewalModel.getErrorText());
//        panel.add(errorLabel);

        panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 100;
        gbc1.weightx = 100;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(panel, gbc1, 0);
        buttonPanel.add(mainList, BorderLayout.CENTER);
    }

    // TODO; remove this, unused
//    public void update() {
//        refreshContent();
//    }

    public int getContractSelection() throws NullPointerException {
        return Integer.parseInt(contractSelectionBox.getSelectedItem().toString());
    }

}
