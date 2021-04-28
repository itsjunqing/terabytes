package view.panel.button;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class TutorDashboardButtonPanel extends DashboardButtonPanel {
    private JComboBox contractChoice;

    public TutorDashboardButtonPanel(int contractSize) {
        super(contractSize);
        addButtons(contractSize);
    }
    @Override
    protected void addButtons(int contractSize) {
        panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
//        gbc2.fill = GridBagConstraints.HORIZONTAL;
        panel.setLayout(layout);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridwidth = GridBagConstraints.REMAINDER;
        gbc2.gridheight = 3;
        gbc2.weightx = 1;
        button1 = new JButton("Refresh");
//        refresh.setPreferredSize(new Dimension(50, 20));
        panel.add(button1, gbc2);
        contractChoice = new JComboBox<>();
        for (int i = 1; i < contractSize + 1; i++) {
            contractChoice.addItem(i);
        }
        panel.add(contractChoice, gbc2);
        button2 = new JButton("Initiate Offer");
//        initiate.setPreferredSize(new Dimension(50, 20));
        panel.add(button2, gbc2);
        panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridheight = 100;
        gbc1.weightx = 100;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(panel, gbc1, 0);
        add(mainList, BorderLayout.CENTER);

    }



}


