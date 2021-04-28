package view.panel.button;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class StudentDashboardButtonPanel extends DashboardButtonPanel {

    private JComboBox contractChoice;
    private JButton refreshButton;
    private JButton initiateButton;

    public StudentDashboardButtonPanel(int contractSize) {
        super(contractSize);
        addButtons(contractSize);
    }

    @Override
    protected void addButtons(int contractSize) {
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
//        gbc2.fill = GridBagConstraints.HORIZONTAL;
        panel.setLayout(layout);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridwidth = GridBagConstraints.REMAINDER;
        gbc2.gridheight = 3;
        gbc2.weightx = 1;


        refreshButton = new JButton("Refresh");
//        refresh.setPreferredSize(new Dimension(50, 20));
        panel.add(refreshButton, gbc2);

        // contract choice not needed
//        contractChoice = new JComboBox<>();
//        for (int i = 1; i < contractSize + 1; i++) {
//            contractChoice.addItem(i);
//        }
//        panel.add(contractChoice, gbc2);

        initiateButton = new JButton("Initiate Bid");
//        initiate.setPreferredSize(new Dimension(50, 20));
        panel.add(initiateButton, gbc2);
        panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 100;
        gbc1.weightx = 100;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        this.mainList.add(panel, gbc1, 0);
        add(this.mainList, BorderLayout.CENTER);

    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JButton getInitiateButton() {
        return initiateButton;
    }
}


