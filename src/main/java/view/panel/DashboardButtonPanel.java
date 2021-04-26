package view.panel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class DashboardButtonPanel extends JPanel {

    private JPanel mainList;
    private JTable table1;
    private JPanel panel1;

    public DashboardButtonPanel(int contractSize) {
        setLayout(new BorderLayout());

        mainList = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        gbc.weightx = 100;
//        gbc.weighty = 100;
//        mainList.add(new JPanel(), gbc);

        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridwidth = GridBagConstraints.REMAINDER;
        gbc2.gridheight = 3;
        gbc2.weightx = 1;
//        gbc2.fill = GridBagConstraints.HORIZONTAL;
        panel.setLayout(layout);
        JButton refresh = new JButton("Refresh");
//        refresh.setPreferredSize(new Dimension(50, 20));
        panel.add(refresh, gbc2);
        JComboBox contractChoice = new JComboBox<>();
        contractChoice.addItem("Contract 1");
        panel.add(contractChoice, gbc2);
        JButton initiate = new JButton("Initiate Bid");
//        initiate.setPreferredSize(new Dimension(50, 20));
        panel.add(initiate, gbc2);
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


