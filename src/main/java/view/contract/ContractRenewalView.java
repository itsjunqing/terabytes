package view.contract;

import lombok.Getter;
import model.dashboard.DashboardModel;
import stream.Contract;
import view.ViewUtility;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class ContractRenewalView {
    private DashboardModel dashboardModel;
    private JPanel mainPanel; // mainPanel holds both contractPanel and buttons
    private JPanel contractPanel; // used to clear and update the content, only this need to be updated
    private JButton refreshButton;
    private JComboBox contractSelection;
    private JButton renewNewTerms;
    private JButton renewOldTerms;
    private JLabel errorLabel;
    private JFrame frame;
    private JPanel buttonPanel;
    public ContractRenewalView(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        String name = dashboardModel.getName();
        frame = new JFrame("Contract Renewal View");
        updateContracts();
        addButtons(dashboardModel.getContractsList().size());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        // resizing if its smaller than the default size
        frame.setMinimumSize(new Dimension(860, 400));
        frame.setMaximumSize(new Dimension(860, 1000));
        frame.setPreferredSize(new Dimension(860, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    protected void refreshContent(){
        updateContracts();
        refreshButtons(dashboardModel.getContractsList().size());
        SwingUtilities.updateComponentTreeUI(frame);
//        frame.pack();
    }

    public void updateContracts() {
        // if contractPanel already constructed, just remove the contents (only one item inside - mainList)
        if (contractPanel != null) {
            contractPanel.removeAll();
        } else {
            contractPanel = new JPanel();
            contractPanel.setLayout(new BorderLayout());
            mainPanel.add(contractPanel);
        }

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
        List<Contract> contractList = new ArrayList<>(getDashboardModel().getContractsList());
        Collections.reverse(contractList);
        int contractIndex = contractList.size();
        for (Contract c: contractList) {
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

    private void refreshButtons(int contractListSize){
        for (int i = 1; i < contractListSize+1; i++){
            contractSelection.addItem(i);
            errorLabel.setText(dashboardModel.getErrorText());
        }
    }

    private void addButtons(int contractListSize) {
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

        refreshButton = new JButton("Refresh");
        panel.add(refreshButton, gbc2);


        for (int i = 1; i < contractListSize+1; i++){
            contractSelection.addItem(i);
        }
        panel.add(contractSelection);

        renewNewTerms = new JButton("Renew with New Terms");
        panel.add(renewNewTerms, gbc2);

        renewOldTerms = new JButton("Renew with Old (existing) Terms");
        panel.add(renewOldTerms, gbc2);

        errorLabel = new JLabel();
        errorLabel.setForeground(new Color(-4521974));
        errorLabel.setHorizontalAlignment(0);
        errorLabel.setHorizontalTextPosition(0);
        errorLabel.setText(dashboardModel.getErrorText());
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

    public void update() {
        refreshContent();
    }
}
