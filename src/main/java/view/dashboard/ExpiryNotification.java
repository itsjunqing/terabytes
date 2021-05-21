package view.dashboard;

import lombok.Getter;
import stream.Contract;
import view.ViewUtility;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.List;

@Getter
public class ExpiryNotification {

    private List<Contract> expiringContracts;
    private int type;

    private JPanel mainPanel; // mainPanel holds both contractPanel and buttons
    private JPanel contractPanel;
    private JButton notedButton;
    private JFrame frame;
    private JPanel buttonPanel;

    public ExpiryNotification(List<Contract> expiringContracts, int type) {
        this.expiringContracts = expiringContracts;
        this.type = type;

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        frame = new JFrame("Contract Expiry Notification");
        addContracts();
        addButtons();
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

    private void addContracts() {
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

        // get the list of contracts that are expiring and update accordingly
        int contractIndex = expiringContracts.size();
        for (Contract c: expiringContracts) {
            JPanel panel = new JPanel();
            JTable table = ViewUtility.ContractTable.buildTable(c, contractIndex, type);
//            JTable table = ViewUtility.ContractTable.buildStudentTable(c, contractIndex); // to be removed
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

    private void addButtons() {
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

        notedButton = new JButton("Noted");
        panel.add(notedButton, gbc2);

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
